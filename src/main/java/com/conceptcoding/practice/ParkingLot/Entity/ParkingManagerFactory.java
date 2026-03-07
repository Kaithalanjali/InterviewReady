package com.conceptcoding.practice.ParkingLot.Entity;

import com.conceptcoding.practice.ParkingLot.Enums.VehicleType;

import java.util.List;

public interface ParkingManagerFactory {
    public ParkingSpotManager getParkingSpotManager();
//    {
//        if(vehicleType == VehicleType.CAR){
//            return new FourWheelerManager(parkingSpots);
//        } else if(vehicleType == VehicleType.MOTORCYCLE){
//            return new TwoWheelerManager(parkingSpots);
//        }
//        return null;

//    }
}
