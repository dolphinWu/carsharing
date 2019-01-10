package com.ziroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class MainConfig {

    @Bean
    public FormattingConversionServiceFactoryBean conversionService(CustomDateConverter customDateConverter) {
        FormattingConversionServiceFactoryBean factoryBean = new FormattingConversionServiceFactoryBean();
        Set<Converter> converters = new HashSet<>();
        converters.add(customDateConverter);
        factoryBean.setConverters(converters);
        return factoryBean;
    }
}
