package com.conceptcoding.practice.ParkingLot.Entity;

public class EntranceGate {
    private ParkingManagerFactory parkingManagerFactory;
    private ParkingSpotManager parkingSpotManager;

    ParkingSpot findParkingSpot(Vehicle vehicle){
        parkingManagerFactory = new ParkingManagerFactory();
        parkingSpotManager = parkingManagerFactory.getParkingSpotManager(vehicle.getVehicleType());
        return parkingSpotManager.findParkingSpot();
    }

    Ticket BookParkingSpot(Vehicle vehicle){
        ParkingSpot parkingSpot = findParkingSpot(vehicle);
        parkingSpot.parkVehicle(vehicle);
        return generateTicket(vehicle, parkingSpot);
    }

    Ticket generateTicket(Vehicle vehicle, ParkingSpot parkingSpot){
        return new Ticket(System.currentTimeMillis(),vehicle, parkingSpot);
    }

}
