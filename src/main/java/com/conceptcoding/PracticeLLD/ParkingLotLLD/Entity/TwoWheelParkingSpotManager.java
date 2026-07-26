package com.conceptcoding.PracticeLLD.ParkingLotLLD.Entity;

import java.util.List;

public class TwoWheelParkingSpotManager implements ParkingManagerFactory {
    private List<ParkingSpot> parkingSpots;
    public TwoWheelParkingSpotManager(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }
    public ParkingSpotManager getParkingSpotManager() {
        return new TwoWheelerManager(parkingSpots);
    }
}
