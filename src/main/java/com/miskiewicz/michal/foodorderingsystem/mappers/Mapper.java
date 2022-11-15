package com.miskiewicz.michal.foodorderingsystem.mappers;

import com.miskiewicz.michal.foodorderingsystem.entities.DessertEntity;
import com.miskiewicz.michal.foodorderingsystem.entities.DrinkEntity;
import com.miskiewicz.michal.foodorderingsystem.entities.MainCourseEntity;
import com.miskiewicz.michal.foodorderingsystem.entities.OrderEntity;
import com.miskiewicz.michal.foodorderingsystem.requests.*;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static com.miskiewicz.michal.foodorderingsystem.requests.Drink.Additions.*;

@Component
public class Mapper {

    private final Converter<Drink, DrinkEntity> convertDrinkToDrinkEntity = new AbstractConverter<>() {
        protected DrinkEntity convert(Drink source) {
            DrinkEntity drink = new DrinkEntity();
            drink.setPrice(source.getPrice());
            drink.setName(source.getName());
            drink.setId(source.getId());
            return drink;
        }
    };

    private final Converter<Lunch, MainCourseEntity> convertLunchToMainCourseEntity = new AbstractConverter<>() {
        protected MainCourseEntity convert(Lunch source) {
            MainCourseEntity mainCourse = new MainCourseEntity();
            mainCourse.setPrice(source.getMainCourse().getPrice());
            mainCourse.setName(source.getMainCourse().getName());
            mainCourse.setId(source.getMainCourse().getId());
            return mainCourse;
        }
    };

    private final Converter<Lunch, DessertEntity> convertLunchToDessertEntity = new AbstractConverter<>() {
        protected DessertEntity convert(Lunch source) {
            DessertEntity dessert = new DessertEntity();
            dessert.setPrice(source.getDessert().getPrice());
            dessert.setName(source.getDessert().getName());
            dessert.setId(source.getDessert().getId());
            return dessert;
        }
    };

    private final Converter<Drink, Boolean> convertDrinkAdditionsToLemonAddition = new AbstractConverter<>() {
        protected Boolean convert(Drink source) {
            return source.getAddition() == ALL || source.getAddition() == LEMON;
        }
    };

    private final Converter<Drink, Boolean> convertDrinkAdditionsToIceCubesAddition = new AbstractConverter<>() {
        protected Boolean convert(Drink source) {
            return source.getAddition() == ALL || source.getAddition() == ICE_CUBES;
        }
    };

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.addMappings(new PropertyMap<DrinkEntity, Drink>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setPrice(source.getPrice());
                map().setId(source.getId());
            }

        });
        modelMapper.addMappings(new PropertyMap<MainCourseEntity, MainCourse>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setPrice(source.getPrice());
                map().setId(source.getId());
            }

        });
        modelMapper.addMappings(new PropertyMap<DessertEntity, Dessert>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setPrice(source.getPrice());
                map().setId(source.getId());
            }

        });
        modelMapper.addMappings(new PropertyMap<OrderingRequest, OrderEntity>() {
            @Override
            protected void configure() {
                map().setCost(source.getCost());
            }
        });
        modelMapper.typeMap(OrderingRequest.class, OrderEntity.class)
                .addMappings(mapper ->
                {
                    mapper.using(convertLunchToMainCourseEntity)
                            .map(OrderingRequest::getLunch, OrderEntity::setMainCourse);
                    mapper.using(convertLunchToDessertEntity)
                            .map(OrderingRequest::getLunch, OrderEntity::setDessert);
                    mapper.using(convertDrinkToDrinkEntity)
                            .map(OrderingRequest::getDrink, OrderEntity::setDrink);
                    mapper.using(convertDrinkAdditionsToIceCubesAddition)
                            .map(OrderingRequest::getDrink, OrderEntity::setIceCubes);
                    mapper.using(convertDrinkAdditionsToLemonAddition)
                            .map(OrderingRequest::getDrink, OrderEntity::setLemon);
                });
        return modelMapper;
    }
}
