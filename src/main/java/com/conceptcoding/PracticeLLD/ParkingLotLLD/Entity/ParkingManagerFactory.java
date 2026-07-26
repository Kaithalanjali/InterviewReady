package com.conceptcoding.PracticeLLD.ParkingLotLLD.Entity;

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
