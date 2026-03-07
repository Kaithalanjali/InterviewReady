package com.conceptcoding.practice.ParkingLot.Entity;

import java.util.List;

public class ParkingSpotManager {
    private List<ParkingSpot> parkingSpots;

    public ParkingSpotManager(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public void addParkingSpot(ParkingSpot spot) {
        parkingSpots.add(spot);
    }

    ParkingSpot ParkingSpotById(String id) {
        return parkingSpots.stream()
            .filter(parkingSpot -> id.equals(parkingSpot.id))
            .findFirst()
            .orElse(null);
    }

    void removeParkingSpot(ParkingSpot spot) {
        parkingSpots.remove(spot);
    }

    ParkingSpot findParkingSpot(){
        //here we can use strategy pattern to find the parking spot based on different strategies like random, nearest, etc.
        return parkingSpots.stream().filter(parkingSpot -> parkingSpot.isEmpty())
            .findFirst()
            .orElse(null);
    }
}
