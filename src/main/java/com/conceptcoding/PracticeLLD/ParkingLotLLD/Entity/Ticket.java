package com.conceptcoding.PracticeLLD.ParkingLotLLD.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Ticket {
    private final long entryTime;
    private final Vehicle vehicle;
    private final ParkingSpot parkingSpot;
}
