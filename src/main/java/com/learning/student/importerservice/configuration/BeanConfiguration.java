package com.learning.student.importerservice.configuration;

import com.learning.student.importerservice.util.XmlToJsonTransformer;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public XmlToJsonTransformer xmlToJsonTransformer() {
        return new XmlToJsonTransformer();
    }
}
