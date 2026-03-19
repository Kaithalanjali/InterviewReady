package com.conceptcoding.impdesignpattern.abstractfactory;

public class ToyotaFactory implements VehicleFactory {
    public Vehicle createVehicle() {
        return new Toyota();
    }
}
