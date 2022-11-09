package com.miskiewicz.michal.foodorderingsystem.mappers;

import com.miskiewicz.michal.foodorderingsystem.entities.DrinkEntity;
import com.miskiewicz.michal.foodorderingsystem.requests.Drink;
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
            }

        });

        return modelMapper;
    }
}
