package com.dandelion.backend.configs;

import com.dandelion.backend.entities.enumType.Order;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();


        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);

//        modelMapper.addConverter(enumConverter());

        return modelMapper;
    }

    @Bean
    public Converter<String, Order> enumConverter() {
        return context -> Order.valueOf(context.getSource());
    }
}
