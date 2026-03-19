package com.conceptcoding.impdesignpattern.abstractfactory;

public class BMWFactory implements VehicleFactory {
    public Vehicle createVehicle() {
        return new BMW();
    }
}
