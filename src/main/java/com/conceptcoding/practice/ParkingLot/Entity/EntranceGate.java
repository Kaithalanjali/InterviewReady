package com.conceptcoding.practice.ParkingLot.Entity;

import com.conceptcoding.practice.ParkingLot.Enums.VehicleType;

import java.util.List;

public class EntranceGate {
    private ParkingManagerFactory parkingManagerFactory;
    private ParkingSpotManager parkingSpotManager;

    ParkingSpot findParkingSpot(Vehicle vehicle, List<ParkingSpot> parkingSpots) {
        if(vehicle.getVehicleType() == VehicleType.CAR){
                parkingManagerFactory = new FourWheelerParkingSpotManager(parkingSpots);
            } else if(vehicle.getVehicleType() == VehicleType.MOTORCYCLE){
                parkingManagerFactory = new TwoWheelParkingSpotManager(parkingSpots);
        }
//        parkingManagerFactory = new ParkingManagerFactory();
        parkingSpotManager = parkingManagerFactory.getParkingSpotManager();
        return parkingSpotManager.findParkingSpot();
    }

    Ticket BookParkingSpot(Vehicle vehicle, List<ParkingSpot> parkingSpots){
        ParkingSpot parkingSpot = findParkingSpot(vehicle, parkingSpots);
        parkingSpot.parkVehicle(vehicle);
        return generateTicket(vehicle, parkingSpot);
    }

    Ticket generateTicket(Vehicle vehicle, ParkingSpot parkingSpot){
        return new Ticket(System.currentTimeMillis(),vehicle, parkingSpot);
    }

}
