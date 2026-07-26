package com.conceptcoding.designpattern.behavioralpatterns.strategy.vehicledrivemodes.solution.context;

import com.conceptcoding.designpattern.behavioralpatterns.strategy.vehicledrivemodes.solution.strategies.DriveStrategy;

// Concrete context subclass
public class GoodsVehicle extends Vehicle {

    public GoodsVehicle(DriveStrategy driveStrategy) {
        super(driveStrategy);
    }
}
