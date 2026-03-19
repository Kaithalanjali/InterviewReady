package com.conceptcoding.impdesignpattern.abstractfactory;

// Concrete Factories for Each Car Brand
public class HondaFactory implements VehicleFactory {
    public Vehicle createVehicle() {
        return new Honda();
    }
}
