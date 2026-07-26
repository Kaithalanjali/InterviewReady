package com.conceptcoding.designpattern.creationalpatterns.factory.factorymethod;

import com.conceptcoding.designpattern.creationalpatterns.factory.Rectangle;
import com.conceptcoding.designpattern.creationalpatterns.factory.Shape;

// Step 4: Concrete Creator classes
public class RectangleCreator extends ShapeFactory {

    @Override
    public Shape createShape() {
        return new Rectangle();
    }
}
