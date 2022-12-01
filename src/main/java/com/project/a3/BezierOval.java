package com.project.a3;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Translate;

public class BezierOval extends Group { 
    Ellipse ellipse;

    BezierOval(Point2D start, double radius) {
        ellipse = new Ellipse(start.getX(), start.getY(), radius * 2, radius);
        //CubicCurve(double startX, double startY, double controlX1, double controlY1, double controlX2, double controlY2, double endX, double endY)

        //to get a point on the ellipse, x = acos(theta) y = bsin(theta)
        //control points : y = (b + alpha)sin(theta), x = (c + alpha)cos(theta)
        getChildren().add(ellipse);
    }

    public double getCenterX() {
        return ellipse.getCenterX();
    }

    public double getCenterY() {
        return ellipse.getCenterY();
    }

    public void setFill(Paint value) {
        for (Node s : this.getChildren()) {
            if (s instanceof Shape) {
                ((Shape)s).setFill(value);
            }
        }
    }
    
}