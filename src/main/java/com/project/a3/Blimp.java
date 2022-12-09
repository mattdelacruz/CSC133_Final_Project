package com.project.a3;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;

public class Blimp extends GameObject{
    Group bezierCurves = new Group();
    Ellipse ellipse;
    public Blimp(Point2D pos, Point2D radius) {

        ellipse = new Ellipse(pos.getX(), pos.getY(), radius.getX(), radius.getY());
        
        for (int i = 0; i < 2; i++) {
            bezierCurves.getChildren().add(createCubicCurve());
        }


    }
    private Node createCubicCurve() {
        double cubicCurveStartX, cubicCurveStartY, cubicCurveControlX1, cubicCurveControlY1, cubicCurveControlX2,
                cubicCurveControlY2, cubicCurveEndX,
                cubicCurveEndY;

        cubicCurveStartX = cubicCurveStartY = cubicCurveControlX1 = cubicCurveControlY1 = cubicCurveControlX2 = cubicCurveControlY2 = cubicCurveEndX = cubicCurveEndY = 0;

        cubicCurveStartX = ellipse.getCenterX() - ellipse.getRadiusX();
        cubicCurveStartY = ellipse.getCenterY();
        cubicCurveEndX = ellipse.getCenterX() + ellipse.getRadiusX();
        cubicCurveEndY = ellipse.getCenterY();
        cubicCurveControlX1 = ellipse.getCenterX();
        cubicCurveControlY1 = ellipse.getCenterY() + 10;

        return new CubicCurve(cubicCurveStartX, cubicCurveStartY, cubicCurveControlX1, cubicCurveControlY1, cubicCurveControlX1, cubicCurveControlY1, cubicCurveEndX, cubicCurveEndY);

    }
}
