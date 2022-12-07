package com.project.a3;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.transform.Translate;

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
    private CloudState state = new DeadCloudState();
    private CloudState inPlay = new InPlayCloudState();
    private double percentage, cloudColorValue;
    private double rand = ThreadLocalRandom.current().nextDouble(0.5, 2);

    Cloud(Point2D s, double size) {
        circle = new BezierOval(new Point2D(s.getX(), s.getY()), size * 1.5, size, 0);
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

    public Pond findClosestPond(Iterator<Node> iterator) {
        min = (Pond) iterator.next();

        if (!getState().equals(inPlay)) {
            return null;
        }

        while (iterator.hasNext()) {
            Node p = iterator.next();
            if (p instanceof Pond) {
                if (getBoundsInParent().intersects(((Pond) p).getFillBounds().getBoundsInParent())) {

                    // double slope1 = Math.abs(((Pond) p).getCenter().getY() - getBoundsInParent().getCenterY())
                    //         / Math.abs(((Pond) p).getCenter().getX() - getBoundsInParent().getCenterX());

                    // double slope2 = Math.abs(min.getCenter().getY() - getBoundsInParent().getCenterY())
                    //         / Math.abs(min.getCenter().getX() - getBoundsInParent().getCenterX());

                    if (slope1 >= slope2)
                        min = ((Pond) p);
                }
            }
        }
        return min;
    }

    @Override
    public void update() {
        if (cloudColorValue < (MAX_COLOR_VALUE - MIN_COLOR_VALUE)) {
            cloudColorValue += PERCENT_ADDER;
            updateCloudValue();
        }
    }

    public void move(Translate t) {
        if (getBoundsInParent().getCenterX() > 0) {
            setState(inPlay);
        }
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
        return getDistanceValue() / min.getFillBounds().getBoundsInLocal().getWidth();
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

}