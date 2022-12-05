package com.project.a3;

import java.util.concurrent.ThreadLocalRandom;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class BezierOval extends Group {
    private static final int MIN_CURVE_WIDTH = 20;
    private static final int MAX_CURVE_WIDTH = 30;
    private static final int MIN_CURVE_HEIGHT = 15;
    private static final int MAX_CURVE_HEIGHT = 20;
    private Ellipse ellipse;
    private Group cubicCurveGroup;
    private Point2D center;

    BezierOval(Point2D start, double radius) {
        center = start;
        ellipse = new Ellipse(center.getX(), center.getY(), radius * 2, radius);
        cubicCurveGroup = new Group();
        createCubicCurves(0);
        getChildren().addAll(cubicCurveGroup, ellipse);
    }

    private void createCubicCurves(double startAngle) {
        double cubicCurveStartX, cubicCurveStartY, cubicCurveControlX1, cubicCurveControlY1, cubicCurveEndX,
                cubicCurveEndY;

        cubicCurveStartX = cubicCurveStartY = cubicCurveControlX1 = cubicCurveControlY1 = cubicCurveEndX = cubicCurveEndY = 0;

        double endAngle = startAngle + ThreadLocalRandom.current().nextDouble(MIN_CURVE_WIDTH, MAX_CURVE_WIDTH);

        if (Math.toRadians(startAngle) < Math.toRadians(360)) {
            if (Math.toRadians(startAngle) >= Math.toRadians(180)) {
                cubicCurveStartX = center.getX() + ellipse.getRadiusX() * Math.cos(Math.toRadians(startAngle));
                cubicCurveStartY = center.getY() + ellipse.getRadiusY() * Math.sin(Math.toRadians(startAngle));

                cubicCurveEndX = center.getX() + ellipse.getRadiusX() * Math.cos(Math.toRadians(endAngle));
                cubicCurveEndY = center.getY() + ellipse.getRadiusY() * Math.sin(Math.toRadians(endAngle));

                cubicCurveControlX1 = cubicCurveStartX + (cubicCurveEndX - cubicCurveStartX) / 2;
                cubicCurveControlY1 = cubicCurveStartY
                        - ThreadLocalRandom.current().nextDouble(MIN_CURVE_HEIGHT, MAX_CURVE_HEIGHT);

            } else {
                cubicCurveStartX = center.getX() + ellipse.getRadiusX() *
                        Math.cos(Math.toRadians(startAngle));
                cubicCurveStartY = center.getY() + ellipse.getRadiusY() *
                        Math.sin(Math.toRadians(startAngle));

                cubicCurveEndX = center.getX() + ellipse.getRadiusX() *
                        Math.cos(Math.toRadians(endAngle));
                cubicCurveEndY = center.getY() + ellipse.getRadiusY() *
                        Math.sin(Math.toRadians(endAngle));

                cubicCurveControlX1 = cubicCurveStartX + (cubicCurveEndX - cubicCurveStartX) / 2;
                cubicCurveControlY1 = cubicCurveStartY +
                        ThreadLocalRandom.current().nextDouble(MIN_CURVE_HEIGHT, MAX_CURVE_HEIGHT);
            }

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
                        ((CubicCurve) n).setFill(Color.WHITE);
                        ((CubicCurve) n).setStroke(Color.GRAY);
                    }
                }
            }
        }
    }
}