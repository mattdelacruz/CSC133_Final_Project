package com.project.a3;

import java.util.concurrent.ThreadLocalRandom;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;

public class BezierOval extends Group {
    private static final int MIN_CURVE_WIDTH = 30;
    private static final int MAX_CURVE_WIDTH = 50;
    private static final int MIN_CURVE_HEIGHT = 15;
    private static final int MAX_CURVE_HEIGHT = 25;
    private Ellipse ellipse;
    private Group cubicCurveGroup;
    private Point2D center;

    BezierOval(Point2D start, double radiusX, double radiusY, double startAngle) {
        center = start;
        ellipse = new Ellipse(center.getX(), center.getY(), radiusX, radiusY);
        ellipse.setStroke(Color.BLACK);
        cubicCurveGroup = new Group();
        createCubicCurves(startAngle);
        getChildren().addAll(ellipse, cubicCurveGroup);
    }

    private void createCubicCurves(double startAngle) {
        double cubicCurveStartX, cubicCurveStartY, cubicCurveControlX1, cubicCurveControlY1, cubicCurveControlX2,
                cubicCurveControlY2, cubicCurveEndX,
                cubicCurveEndY;

        cubicCurveStartX = cubicCurveStartY = cubicCurveControlX1 = cubicCurveControlY1 = cubicCurveControlX2 = cubicCurveControlY2 = cubicCurveEndX = cubicCurveEndY = 0;

        double endAngle = startAngle + ThreadLocalRandom.current().nextDouble(MIN_CURVE_WIDTH, MAX_CURVE_WIDTH);

        if (Math.toRadians(startAngle) < Math.toRadians(360)) {
            cubicCurveStartX = center.getX() + ellipse.getRadiusX() *
                    Math.cos(Math.toRadians(startAngle));
            cubicCurveStartY = center.getY() + ellipse.getRadiusY() *
                    Math.sin(Math.toRadians(startAngle));
            cubicCurveEndX = center.getX() + ellipse.getRadiusX() *
                    Math.cos(Math.toRadians(endAngle));
            cubicCurveEndY = center.getY() + ellipse.getRadiusY() *
                    Math.sin(Math.toRadians(endAngle));
            if (Math.toRadians(startAngle) == Math.toRadians(180)) {

            } else if (Math.toRadians(startAngle) > Math.toRadians(180)) {
                cubicCurveControlY1 = cubicCurveStartY
                        - ThreadLocalRandom.current().nextDouble(MIN_CURVE_HEIGHT, MAX_CURVE_HEIGHT);

            } else {
                cubicCurveControlY1 = cubicCurveStartY
                        + ThreadLocalRandom.current().nextDouble(MIN_CURVE_HEIGHT, MAX_CURVE_HEIGHT);

            }

            cubicCurveControlX1 = center.getX()
                    + ellipse.getRadiusX() * Math.cos(Math.toRadians(startAngle + ((endAngle - startAngle) / 2)));

            if (Math.abs(cubicCurveEndX - cubicCurveStartX) >= MIN_CURVE_WIDTH) {
                cubicCurveGroup.getChildren()
                        .addAll(new CubicCurve(cubicCurveStartX, cubicCurveStartY, cubicCurveControlX1,
                                cubicCurveControlY1, cubicCurveControlX1, cubicCurveControlY1, cubicCurveEndX,
                                cubicCurveEndY));
            }
            createCubicCurves(endAngle);
        } else {
            return;
        }
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
                ((Shape) s).setFill(value);
            }

            if (s instanceof Group) {
                for (Node n : ((Group) s).getChildren()) {
                    if (n instanceof CubicCurve) {
                        ((CubicCurve) n).setFill(value);
                        ((CubicCurve) n).setStroke(Color.BLACK);
                    }
                }
            }
        }
    }
}