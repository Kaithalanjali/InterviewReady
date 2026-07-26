package com.conceptcoding.designpattern.behavioralpatterns.strategy.vehicledrivemodes.solution.context;

import com.conceptcoding.designpattern.behavioralpatterns.strategy.vehicledrivemodes.solution.strategies.DriveStrategy;

// Concrete context subclass
public class HybridVehicle extends Vehicle {

    public HybridVehicle(DriveStrategy driveStrategy) {
        super(driveStrategy);
    }
}
