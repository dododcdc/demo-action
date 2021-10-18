package com.action.demoaction;

import com.action.demoaction.config.Apple;
import com.action.demoaction.config.FoodConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class DemoActionApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoActionApplication.class, args);
    }
    @Bean
    public CommandLineRunner run(IFoo iFoo,Apple apple,FoodConfig foodConfig) throws Exception {
        return args -> {
            AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
            annotationConfigApplicationContext.register(BConfig.class);
            annotationConfigApplicationContext.refresh();
            annotationConfigApplicationContext.getBean(IFoo.class);
            iFoo.say();
            System.out.println(apple.getColor());
            System.out.println(foodConfig.apple.getColor());
        };
    }


}
