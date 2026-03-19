package com.conceptcoding.impdesignpattern.factorypattern;

// Concrete vehicle classes remain the same
public class Car implements Vehicle {
    public void start() {
        System.out.println("Car is starting...");
    }
    public void stop() {
        System.out.println("Car is stopping...");
    }
}
