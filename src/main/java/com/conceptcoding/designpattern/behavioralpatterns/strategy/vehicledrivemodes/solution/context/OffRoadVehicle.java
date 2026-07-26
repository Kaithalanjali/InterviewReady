package com.conceptcoding.designpattern.behavioralpatterns.strategy.vehicledrivemodes.solution.context;

import com.conceptcoding.designpattern.behavioralpatterns.strategy.vehicledrivemodes.solution.strategies.DriveStrategy;

// Concrete context subclass
public class OffRoadVehicle extends Vehicle {

    OffRoadVehicle(DriveStrategy driveStrategy) {
        super(driveStrategy);
    }
}
