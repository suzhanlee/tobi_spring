package com.tobi_spring.chapter3.practice.templatemethod;

public class RacingCar extends RecordCalculator {

    int speed;

    @Override
    protected int race(int totalDistance) {

        return totalDistance / speed;
    }
}
