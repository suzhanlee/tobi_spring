package com.tobi_spring.chapter3.practice.strategypattern;

public class RecordCalculator {

    int totalDistance;

    public void calculateRecord(Car car) {

        long startTime = System.currentTimeMillis();
        int time = car.race(totalDistance);
        long endTime = System.currentTimeMillis();

        System.out.println(time);
        System.out.println(endTime - startTime);
    }
}
