package com.conceptcoding.designpattern.creationalpatterns.factory.factorymethod;

import com.conceptcoding.designpattern.creationalpatterns.factory.Circle;
import com.conceptcoding.designpattern.creationalpatterns.factory.Shape;

// Step 4: Concrete Creator classes
public class CircleCreator extends ShapeFactory {

    @Override
    public Shape createShape() {
        return new Circle();
    }
}
