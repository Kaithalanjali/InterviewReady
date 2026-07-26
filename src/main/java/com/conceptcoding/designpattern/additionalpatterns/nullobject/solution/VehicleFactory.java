package com.conceptcoding.designpattern.additionalpatterns.nullobject.solution;

import com.conceptcoding.designpattern.additionalpatterns.nullobject.Bike;
import com.conceptcoding.designpattern.additionalpatterns.nullobject.Car;
import com.conceptcoding.designpattern.additionalpatterns.nullobject.Vehicle;

public class VehicleFactory {

    public static Vehicle getVehicle(String type) {
        if (type.equals("car")) {
            return new Car("Toyota", "Red", 5, 60, true);
        } else if (type.equals("bike")) {
            return new Bike("Yamaha", "Black", 60);
        } else {
            return new NullVehicle(); // THE SOLUTION
        }
    }
}
