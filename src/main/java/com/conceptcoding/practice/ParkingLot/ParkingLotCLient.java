package com.conceptcoding.practice.ParkingLot;

import com.conceptcoding.practice.ParkingLot.Entity.*;
import com.conceptcoding.practice.ParkingLot.Enums.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotCLient {
    public static void main(String[] args) {
        //create 5 twowheelerspots and 5 fourwheelerspots
        List<ParkingSpot> twoWheelerSpots = new ArrayList<>();
        for(int i=1; i<=5; i++){
            twoWheelerSpots.add(new TwoWheelerSpot("T"+i));
        }
        List<ParkingSpot> fourWheelerSpots = new ArrayList<>();
        for(int i=1; i<=5; i++){
            fourWheelerSpots.add(new FourWheelerSpot("F"+i));
        }
        ParkingManagerFactory twoWheelerManager = new TwoWheelParkingSpotManager(twoWheelerSpots);
        ParkingManagerFactory fourWheelerManager = new FourWheelerParkingSpotManager(fourWheelerSpots);




    }
}
