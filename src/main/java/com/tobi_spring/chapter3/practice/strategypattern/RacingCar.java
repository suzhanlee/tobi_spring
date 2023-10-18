package com.tobi_spring.chapter3.practice.strategypattern;

public class RacingCar implements Car {

    int speed;

    @Override
    public int race(int totalDistance) {
        return totalDistance / speed;
    }
}
