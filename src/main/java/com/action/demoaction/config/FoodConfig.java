package com.action.demoaction.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@EnableConfigurationProperties(Apple.class)
public class FoodConfig {

    @Autowired
    public Apple apple;


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder.setConnectTimeout(Duration.ofMinutes(10))
                .setReadTimeout(Duration.ofMinutes(10))
                .build();
    }


}
