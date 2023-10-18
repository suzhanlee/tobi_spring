package com.tobi_spring.chapter3.practice.templatecallback;

public class SpeedCalculator {

    int totalDistance;

    public void calculateRacingCarRecord() {

        long startTime = System.currentTimeMillis();
        Car car = new Car() {
            int speed;
            @Override
            public int race(int totalDistance) {
                return totalDistance / speed;
            }
        };

        int time = car.race(totalDistance);
        long endTime = System.currentTimeMillis();

        System.out.println(time);
        System.out.println(endTime - startTime);
    }
}
