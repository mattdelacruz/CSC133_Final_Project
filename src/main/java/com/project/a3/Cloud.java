package com.project.a3;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Transform;

class Cloud extends GameObject implements Updateable {
    private static final double MAX_COLOR_VALUE = 255;
    private static final double MIN_COLOR_VALUE = 155;
    private static final double PERCENT_VALUE = 0.01;
    private static final double PERCENT_ADDER = (MAX_COLOR_VALUE -
            MIN_COLOR_VALUE) * PERCENT_VALUE;
    private static final String LABEL_FORMAT = "%.0f%%";
    private static final Color FONT_COLOR = Color.BLACK;
    private static final Color DISTANCE_LINE_COLOR = Color.MAGENTA;

    private BezierOval circle;
    private GameText cloudLabel;
    private Pond min;
    private Line distance;
    private CloudState state = new DeadCloudState();
    private double percentage, cloudColorValue, lengthX;
    private double rand = ThreadLocalRandom.current().nextDouble(0.5, 2);

    Cloud(Point2D s, double size) {
        
        circle = new BezierOval(new Point2D(s.getX() + rand, s.getY() + rand), size);
        cloudColorValue = 0;
        percentage = cloudColorValue / MAX_COLOR_VALUE;
        percentage *= 100;
        circle.setFill(Color.rgb(
                (int) (MAX_COLOR_VALUE - cloudColorValue),
                (int) (MAX_COLOR_VALUE - cloudColorValue),
                (int) (MAX_COLOR_VALUE - cloudColorValue)));
        cloudLabel = createLabel(String.format(LABEL_FORMAT, percentage),
                new Point2D(
                        circle.getCenterX() -
                                (GameText.FONT_SIZE / 2),
                        circle.getCenterY() +
                                (GameText.FONT_SIZE / 2)),
                FONT_COLOR);
        setState(new AliveCloudState());
        getChildren().addAll(circle, cloudLabel);
    }

    public Point2D getCenter() {
        return new Point2D(circle.getCenterX(), circle.getCenterY());
    }

    public Pond findClosestPond(Iterator<Pond> iter) {
        while (iter.hasNext()) {
            Pond p = iter.next();
            if (intersects(p.getFillBounds().getBoundsInParent())) {
                min = p;
                return min;
            }
        }
        return null;
    }

    @Override
    public void update() {
        if (cloudColorValue < (MAX_COLOR_VALUE - MIN_COLOR_VALUE)) {
            cloudColorValue += PERCENT_ADDER;
            updateCloudValue();
        }
    }

    public void move(Transform t) {
        state.move(t, this);
    }

    public void decrease() {
        if (cloudColorValue > 0) {
            cloudColorValue -= PERCENT_ADDER;
            updateCloudValue();
        }
    }

    private void updateCloudValue() {
        circle.setFill(Color.rgb((int) (MAX_COLOR_VALUE - cloudColorValue),
                (int) (MAX_COLOR_VALUE - cloudColorValue),
                (int) (MAX_COLOR_VALUE - cloudColorValue)));
        percentage = cloudColorValue / (MAX_COLOR_VALUE - MIN_COLOR_VALUE);
        percentage *= 100;
        updateLabel(percentage);
    }

    public double getPercentageToPond() {
        return lengthX / min.getFillBounds().getBoundsInLocal().getWidth();
    }

    public Line createDistanceLine() {
        distance = new Line(circle.getBoundsInParent().getCenterX(),
                circle.getBoundsInParent().getCenterY(),
                min.getCenter().getX(), min.getCenter().getY());
        distance.setStroke(DISTANCE_LINE_COLOR);
        lengthX = Math.abs(distance.getBoundsInLocal().getMinX() - distance.getBoundsInLocal().getMaxX());
        return distance;

    }

    private void updateLabel(double val) {
        cloudLabel.updateLabel(String.format(LABEL_FORMAT, val));
    }

    public double getPercentage() {
        return Math.min(100.0, percentage);
    }

    public CloudState getState() {
        return state;
    }

    public double getRand() {
        return rand;
    }

    public void setState(CloudState s) {
        state = s;
    }

    public BezierOval getCloudCircle() {
        return circle;
    }

    public GameText getCloudLabel() {
        return cloudLabel;
    }
}