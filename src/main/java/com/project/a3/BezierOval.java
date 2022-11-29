package com.project.a3;

import javafx.geometry.Point2D;
import javafx.scene.shape.Ellipse;

public class BezierOval extends Ellipse { 
    Ellipse ellipse;

    BezierOval(Point2D start, double radius) {
        new Ellipse(start.getX(), start.getY(), radius, radius);
        
    }
    
}