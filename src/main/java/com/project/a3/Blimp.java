package com.project.a3;

import java.util.concurrent.ThreadLocalRandom;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;

public class Blimp extends TransientGameObject {
    private static final int CURVE_DISTANCE = 10;
    private static final Color BLIMP_STROKE = Color.RED;
    private static final Color BLIMP_FILL = Color.rgb(0, 0, 0, 0.5);
    private static final Color FONT_COLOR = Color.LEMONCHIFFON;

    private Group bezierCurves = new Group();
    private Ellipse ellipse;
    private GameText fuelLabel;
    private double rand = ThreadLocalRandom.current().nextDouble(0.5, 2);
    private int fuel;
    private double speed;

    public Blimp(Point2D pos, Point2D radius) {
        int randFuel = ThreadLocalRandom.current().nextInt(5000, 10000);
        fuel = randFuel;
        ellipse = new Ellipse(pos.getX(), pos.getY(), radius.getX(), radius.getY());
        fuelLabel = createLabel(Integer.toString(randFuel),
                new Point2D(ellipse.getCenterX() - (GameText.FONT_SIZE / 2),
                        ellipse.getCenterY() + (GameText.FONT_SIZE / 2)),
                FONT_COLOR);
        ellipse.setFill(BLIMP_FILL);
        ellipse.setStroke(BLIMP_STROKE);
        createCurveGroup(CURVE_DISTANCE);
        createCurveGroup(-CURVE_DISTANCE);
        setState(new AliveWindState());
        getChildren().addAll(ellipse, bezierCurves, fuelLabel);

    }

    private void updateLabel(int val) {
        fuelLabel.updateLabel(Integer.toString(val));
    }

    public void setSpeed(double s) {
        speed = s;
    }

    public double getSpeed() {
        return speed;
    }

    public double getRand() {
        return rand;
    }

    public void setFuel(int f) {
        fuel = f;
        updateLabel(f);
    }

    public int getFuel() {
        return fuel;
    }

    private void createCurveGroup(int curveDistance) {
        int h = curveDistance;
        for (int i = 0; i < ellipse.getRadiusY() / CURVE_DISTANCE; i++) {
            bezierCurves.getChildren().add(createCubicCurve(h));
            h += curveDistance;
        }
    }

    private Node createCubicCurve(int height) {
        double cubicCurveStartX, cubicCurveStartY, cubicCurveControlX1, cubicCurveControlY1, cubicCurveControlX2,
                cubicCurveEndX,
                cubicCurveEndY;

        cubicCurveStartX = cubicCurveStartY = cubicCurveControlX1 = cubicCurveControlY1 = cubicCurveControlX2 = cubicCurveEndX = cubicCurveEndY = 0;

        cubicCurveStartX = ellipse.getCenterX() - ellipse.getRadiusX();
        cubicCurveStartY = ellipse.getCenterY();
        cubicCurveEndX = ellipse.getCenterX() + ellipse.getRadiusX();
        cubicCurveEndY = ellipse.getCenterY();
        cubicCurveControlX1 = ellipse.getCenterX() - ellipse.getRadiusX() + CURVE_DISTANCE / 2;
        cubicCurveControlX2 = ellipse.getCenterX() + ellipse.getRadiusX() - CURVE_DISTANCE / 2;
        cubicCurveControlY1 = ellipse.getCenterY() + height;

        CubicCurve c = new CubicCurve(cubicCurveStartX, cubicCurveStartY, cubicCurveControlX1, cubicCurveControlY1,
                cubicCurveControlX2, cubicCurveControlY1, cubicCurveEndX, cubicCurveEndY);

        c.setStroke(BLIMP_STROKE);
        c.setFill(Color.TRANSPARENT);
        return c;
    }
}
