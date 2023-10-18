package com.tobi_spring.chapter3.practice.templatemethod;

public abstract class RecordCalculator {

    int totalDistance;

    public void calculateRecord() {

        long startTime = System.currentTimeMillis();
        int time = race(totalDistance);
        long endTime = System.currentTimeMillis();

        System.out.println(time);
        System.out.println(endTime - startTime);
    }
    
    protected abstract int race(int totalDistance);

}
