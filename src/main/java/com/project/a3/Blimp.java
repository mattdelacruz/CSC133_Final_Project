package com.project.a3;

import java.util.concurrent.ThreadLocalRandom;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Translate;

public class Blimp extends GameObject {
    private Group bezierCurves = new Group();
    private Ellipse ellipse;
    private CloudState state = new DeadCloudState();
    private CloudState inPlay = new InPlayCloudState();
    private double rand = ThreadLocalRandom.current().nextDouble(0.5, 2);

    public Blimp(Point2D pos, Point2D radius) {

        ellipse = new Ellipse(pos.getX(), pos.getY(), radius.getX(), radius.getY());

        ellipse.setFill(Color.TRANSPARENT);
        ellipse.setStroke(Color.RED);

        for (int i = 0; i < 2; i++) {
            bezierCurves.getChildren().add(createCubicCurve());
        }
        setState(new AliveCloudState());
        getChildren().addAll(ellipse, bezierCurves);

    }

    public void setState(CloudState s) {
        state = s;
    }

    public CloudState getState() {
        return state;
    }

    public double getRand() {
        return rand;
    }

    public void move(Translate t) {
        if (getBoundsInParent().getCenterX() > 0) {
            setState(inPlay);
        }

        state.move(t, this);
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

        CubicCurve c = new CubicCurve(cubicCurveStartX, cubicCurveStartY, cubicCurveControlX1, cubicCurveControlY1,
                cubicCurveControlX1, cubicCurveControlY1, cubicCurveEndX, cubicCurveEndY);

        c.setStroke(Color.MAGENTA);
        return c;

    }
}
