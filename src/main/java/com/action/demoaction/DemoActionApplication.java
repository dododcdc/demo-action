package com.action.demoaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class DemoActionApplication implements CommandLineRunner {

    @Autowired
    IFoo iFoo ;

    public static void main(String[] args) {
        SpringApplication.run(DemoActionApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(BConfig.class);
        annotationConfigApplicationContext.refresh();
        annotationConfigApplicationContext.getBean(IFoo.class);
        iFoo.say();
    }
}
