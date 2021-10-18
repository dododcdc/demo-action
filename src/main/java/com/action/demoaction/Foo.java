package com.action.demoaction;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Foo implements IFoo{
    @Override
    public void say() {
        System.out.println("today is good day");
        System.out.println("today is good day too");
        System.out.println("hello jack");
    }

    public static void main(String[] args) {

        ArrayBlockingQueue<Integer> integers = new ArrayBlockingQueue<>(20);


        integers.offer(1);
        integers.offer(2);
        integers.offer(3);
        integers.offer(4);
        integers.offer(5);
        integers.offer(6);
        System.out.println("cc");

        Integer poll = integers.poll();
        System.out.println("dd");

        List<Integer> integers1 = Arrays.asList(99, 88, 77);
        List<Integer> integers2 = Arrays.asList(66, 55, 44);
        integers.addAll(integers1);
        integers.addAll(integers2);
        System.out.println("gg");

    }
}
