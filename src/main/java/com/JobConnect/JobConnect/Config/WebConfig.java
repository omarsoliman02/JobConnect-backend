package com.JobConnect.JobConnect.Config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Find and modify the Jackson converter to accept charset
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jacksonConverter = 
                    (MappingJackson2HttpMessageConverter) converter;
                
                List<MediaType> supportedTypes = new ArrayList<>(jacksonConverter.getSupportedMediaTypes());
                supportedTypes.add(MediaType.valueOf("application/json;charset=UTF-8"));
                // Add additional variants that might be sent by the frontend
                supportedTypes.add(MediaType.valueOf("application/json;charset=utf-8"));
                supportedTypes.add(MediaType.valueOf("application/json; charset=UTF-8"));
                supportedTypes.add(MediaType.valueOf("application/json; charset=utf-8"));
                jacksonConverter.setSupportedMediaTypes(supportedTypes);
                
                // Move this converter to the beginning of the list to give it priority
                converters.remove(converter);
                converters.add(0, converter);
                break;
            }
        }
    }
} 