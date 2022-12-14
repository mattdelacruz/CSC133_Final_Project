package com.project.a3;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class Helicopter extends GameObject {
    private static final int ROTATION_ANGLE = 15;
    private static final int FUEL_CONSUMPTION = 1;
    public static final int MAX_SPEED = 10;
    public static final int MIN_SPEED = -2;
    private static final int LABEL_GAP = 15;
    public static final double ACCELERATION = 0.1;
    public static final double MAX_SPIN_SPEED = 30;
    private static final String LABEL_FORMAT = "F: %d";

    private HeloBody heloBody;
    private HeloBlade heloBlade;
    private GameText fuelLabel;
    private HelicopterState state = new HelicopterOffState();
    private double speed = 0;
    private double spinSpeed = 0;
    private int fuelValue = 0;

    Helicopter(Point2D s, Color c, double r, int startFuel) {
        heloBody = new HeloBody(s);
        heloBlade = new HeloBlade(s);
        fuelValue = startFuel;
        fuelLabel = createLabel(String.format(LABEL_FORMAT, fuelValue),
                new Point2D(
                        heloBody.getBoundsInParent().getCenterX() -
                                LABEL_GAP,
                        heloBody.getBoundsInParent().getMinY() -
                                LABEL_GAP),
                Color.MAGENTA);
        getChildren().addAll(heloBody, heloBlade, fuelLabel);
    }

    public void consumeFuel() {
        if (fuelValue > 0)
            updateFuel(fuelValue -= FUEL_CONSUMPTION);
    }

    public boolean isIgnitionOn() {
        return state.isIgnitionOn();
    }

    private void updateFuel(int f) {
        fuelLabel.updateLabel(String.format(LABEL_FORMAT, f));
    }

    public void left() {
        getTransforms()
                .add(new Rotate(
                        ROTATION_ANGLE,
                        heloBody.getPivotPoint().getX(),
                        heloBody.getPivotPoint().getY()));
    }

    public void right() {
        getTransforms()
                .add(new Rotate(
                        -ROTATION_ANGLE,
                        heloBody.getPivotPoint().getX(),
                        heloBody.getPivotPoint().getY()));
    }

    public void moveHeli() {
        state.moveHeli(this);
        heloBlade.spinBlade(getSpinSpeed());
        return;
    }

    public void engineStart() {
        state.engineStart(this);
    }

    public void increaseSpeed() {
        state.increaseSpeed(this);
    }

    public void decreaseSpeed() {
        state.decreaseSpeed(this);
    }

    public void setState(HelicopterState s) {
        state = s;
    }

    public HelicopterState getState() {
        return state;
    }

    public double getSpinSpeed() {
        return spinSpeed;
    }

    public void setSpinSpeed(double s) {
        spinSpeed = s;
    }

    public void setSpeed(double s) {
        speed = s;
    }

    public double getSpeed() {
        return speed;
    }

    public int getFuel() {
        return fuelValue;
    }

    public void setFuel(int f) {
        fuelValue = f;
    }

}