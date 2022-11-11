package com.miskiewicz.michal.foodorderingsystem.mappers;

import com.miskiewicz.michal.foodorderingsystem.entities.DrinkEntity;
import com.miskiewicz.michal.foodorderingsystem.entities.OrderEntity;
import com.miskiewicz.michal.foodorderingsystem.requests.Drink;
import com.miskiewicz.michal.foodorderingsystem.requests.OrderingRequest;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<DrinkEntity, Drink>() {
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
                map().setAdditions(source.getDrink().getAddition());
                map().setCost(source.getCost());
            }

        });

        modelMapper.typeMap(OrderingRequest.class, OrderEntity.class)
                .addMappings(mapper -> mapper.using(convertDrinkToDrinkEntity)
                        .map(OrderingRequest::getDrink, OrderEntity::setDrink));

        return modelMapper;
    }

    Converter<Drink, DrinkEntity> convertDrinkToDrinkEntity = new AbstractConverter<>() {
        protected DrinkEntity convert(Drink source) {
            DrinkEntity drink = new DrinkEntity();
            drink.setPrice(source.getPrice());
            drink.setName(source.getName());
            drink.setId(source.getId());
            return drink;
        }
    };
}
