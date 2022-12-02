package com.project.a3;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

public class BezierOval extends Group { 
    private Ellipse ellipse;
    private CubicCurve cubicCurve;

    BezierOval(Point2D start, double radius) {
        ellipse = new Ellipse(start.getX(), start.getY(), radius * 2, radius);

        double cubicCurveStartX = start.getX() + ellipse.getRadiusX() * Math.cos(30);
        double cubicCurveStartY = start.getY() + ellipse.getRadiusY() * Math.sin(30);
        double cubicCurveControlX1 = cubicCurveStartX + 15;
        double cubicCurveControlY1 = cubicCurveStartY - 10;
        double cubicCurveControlX2 = cubicCurveControlX1; 
        double cubicCurveControlY2 = cubicCurveControlY1;

        cubicCurve = new CubicCurve(cubicCurveStartX, cubicCurveStartY, cubicCurveControlX1, cubicCurveControlY1, cubicCurveControlX1, cubicCurveControlY1, cubicCurveStartX + 50, cubicCurveStartY - 50);


        //CubicCurve(double startX, double startY, double controlX1, double controlY1, double controlX2, double controlY2, double endX, double endY)

        //to get a point on the ellipse, x = acos(theta) y = bsin(theta)
        //control points : y = (b + alpha)sin(theta), x = (c + alpha)cos(theta)
        getChildren().addAll(ellipse, cubicCurve);
    }

    public double getCenterX() {
        return ellipse.getCenterX();
    }

    public double getCenterY() {
        return ellipse.getCenterY();
    }

    public void setFill(Paint value) {
        for (Node s : getChildren()) {
            if (s instanceof Shape) {
                ((Shape)s).setFill(value);
            }
        }
        cubicCurve.setFill(Color.RED);
    }
    
}