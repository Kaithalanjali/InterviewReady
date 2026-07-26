package com.conceptcoding.PracticeLLD.ParkingLotLLD.Entity;

import java.util.List;

public class FourWheelerParkingSpotManager implements ParkingManagerFactory {
    private List<ParkingSpot> fourWheelerSpots;

    public FourWheelerParkingSpotManager(List<ParkingSpot> fourWheelerSpots) {
        this.fourWheelerSpots = fourWheelerSpots;
    }

    public ParkingSpotManager getParkingSpotManager() {
        return new FourWheelerManager(fourWheelerSpots);
    }
}
