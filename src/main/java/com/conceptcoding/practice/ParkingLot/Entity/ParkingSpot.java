package com.conceptcoding.practice.ParkingLot.Entity;

public class ParkingSpot {
    String id;
    boolean isEmpty;
    Vehicle vehicle;
    int price;

    public ParkingSpot(String id){
        this.id = id;
        this.isEmpty = true;
        this.vehicle = null;
    }

    void parkVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
        this.isEmpty = false;
    }

    void unparkVehicle(){
        this.vehicle = null;
        this.isEmpty = true;
    }

    boolean isEmpty(){
        return this.isEmpty;
    }

}
