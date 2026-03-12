package com.conceptcoding.practice.ParkingLot.Entity;

import java.util.List;

public class ParkingLot {
    private EntranceGate entranceGate = new EntranceGate();
    private ExitGate exitGate = new ExitGate();
    private List<TwoWheelerSpot> twoWheelerSpots;
    private List<FourWheelerSpot> fourWheelerSpots;

    public ParkingLot(List<TwoWheelerSpot> twoWheelerSpots, List<FourWheelerSpot> fourWheelerSpots) {
        this.twoWheelerSpots = twoWheelerSpots;
        this.fourWheelerSpots = fourWheelerSpots;
    }

}
