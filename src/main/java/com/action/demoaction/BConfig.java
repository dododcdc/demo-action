package com.action.demoaction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BConfig {

    @Bean("ifoo")
    public IFoo ifoo() {
        return new Foo();
    }
}
