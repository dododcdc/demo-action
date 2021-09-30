package com.action.demoaction.service;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 50; i++) {
            executorService.execute(() -> {
                Random random = new Random();
                System.out.println(random.nextInt(1000) +"gggg"+ Thread.currentThread().getName());

            });
        }

        executorService.shutdown();
    }
}
