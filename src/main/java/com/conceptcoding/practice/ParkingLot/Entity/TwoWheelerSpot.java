package com.conceptcoding.practice.ParkingLot.Entity;

import com.conceptcoding.practice.ParkingLot.Enums.VehicleType;

public class TwoWheelerSpot extends ParkingSpot{
    public TwoWheelerSpot(String id) {
        super(id);
        this.price = 10; // Example price for two-wheeler spot
    }

}
