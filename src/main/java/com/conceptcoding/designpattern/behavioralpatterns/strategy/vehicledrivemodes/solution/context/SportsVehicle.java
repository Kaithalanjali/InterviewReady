package com.conceptcoding.designpattern.behavioralpatterns.strategy.vehicledrivemodes.solution.context;

import com.conceptcoding.designpattern.behavioralpatterns.strategy.vehicledrivemodes.solution.strategies.DriveStrategy;

// Concrete context subclass
public class SportsVehicle extends Vehicle {

    public SportsVehicle(DriveStrategy driveStrategy) {
        super(driveStrategy);
    }
}
