package com.conceptcoding.impdesignpattern.abstractfactory;

public class BMW implements Vehicle {
    public void start() {
        System.out.println("BMW Car is starting");
    }
    public void stop() {
        System.out.println("BMW Car is stopping");
    }
}
