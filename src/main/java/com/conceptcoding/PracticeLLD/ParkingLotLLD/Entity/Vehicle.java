package com.conceptcoding.PracticeLLD.ParkingLotLLD.Entity;

import com.conceptcoding.PracticeLLD.ParkingLotLLD.Enums.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Vehicle {
    String vehicleNumber;
    VehicleType vehicleType;
}
