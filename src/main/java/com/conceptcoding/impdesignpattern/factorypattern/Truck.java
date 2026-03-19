package com.conceptcoding.impdesignpattern.factorypattern;

public class Truck implements Vehicle {
    public void start() {
        System.out.println("Truck is starting...");
    }
    public void stop() {
        System.out.println("Truck is stopping...");
    }
}
