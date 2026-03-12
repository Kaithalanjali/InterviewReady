package com.conceptcoding.practice.ParkingLot.Entity;

import com.conceptcoding.practice.ParkingLot.Enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Vehicle {
    String vehicleNumber;
    VehicleType vehicleType;
}
