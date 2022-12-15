package com.project.a3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Cloud extends TransientGameObject implements Updateable {
    private static final double MAX_COLOR_VALUE = 255;
    private static final double MIN_COLOR_VALUE = 155;
    private static final double PERCENT_VALUE = 0.01;
    private static final double PERCENT_ADDER = (MAX_COLOR_VALUE -
            MIN_COLOR_VALUE) * PERCENT_VALUE;
    private static final String LABEL_FORMAT = "%.0f%%";
    private static final Color FONT_COLOR = Color.BLACK;
    private static final Color CLOSEST_POND_DISTANCE_COLOR = Color.MAGENTA;
    private static final Color FARTHEST_POND_DISTANCE_COLOR = Color.AZURE;

    private BezierOval circle;
    private GameText cloudLabel;
    private ArrayList<Pond> closest = new ArrayList<Pond>();
    private ArrayList<Pond> farthest = new ArrayList<Pond>();
    private Map<Pond, Integer> distanceToPond = new HashMap<Pond, Integer>();
    private double percentage, cloudColorValue;
    private double rand = ThreadLocalRandom.current().nextDouble(0.5, 2);

    Cloud(Point2D s, double size) {
        circle = new BezierOval(new Point2D(s.getX(), s.getY()), size * 1.2, size, 0);
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
        setState(new AliveWindState());
        getChildren().addAll(circle, cloudLabel);
    }

    public Point2D getCenter() {
        return new Point2D(circle.getCenterX(), circle.getCenterY());
    }

    // public Pond findClosestPond(Ponds pondPane) {
    // min = (Pond) pondPane.getChildren().get(0);
    // min.setDistanceLineColor(CLOSEST_POND_DISTANCE_COLOR);
    // int dist1 = (int) (Math.sqrt(Math.pow(min.getCenter().getX() -
    // getBoundsInParent().getCenterX(), 2))
    // + Math.sqrt(Math.pow(min.getCenter().getY() -
    // getBoundsInParent().getCenterY(), 2)));

    // for (Node p : pondPane) {
    // if (p instanceof Pond) {
    // if (getBoundsInParent().intersects(((Pond)
    // p).getFillBounds().getBoundsInParent())) {
    // int dist2 = (int) (Math
    // .sqrt(Math.pow(((Pond) p).getCenter().getX() -
    // getBoundsInParent().getCenterX(), 2))
    // + Math.sqrt(Math.pow(((Pond) p).getCenter().getY() -
    // getBoundsInParent().getCenterY(), 2)));

    // if (dist1 > dist2) {
    // min.setDistanceLineColor(Color.AZURE);
    // min = ((Pond) p);
    // min.setDistanceLineColor(CLOSEST_POND_DISTANCE_COLOR);
    // }
    // }
    // }
    // }
    // return min;
    // }

    @Override
    public void update() {
        if (cloudColorValue < (MAX_COLOR_VALUE - MIN_COLOR_VALUE)) {
            cloudColorValue += PERCENT_ADDER;
            updateCloudValue();
        }
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

    private void updateLabel(double val) {
        cloudLabel.updateLabel(String.format(LABEL_FORMAT, val));
    }

    public double getPercentage() {
        return Math.min(100.0, percentage);
    }

    public double getRand() {
        return rand;
    }

    public void findClosestPond(Ponds pondPane) {
        for (Node p : pondPane) {
            if (p instanceof Pond) {
                if (((Pond) p).getFillBounds().getBoundsInLocal().intersects(getBoundsInParent())) {
                    addToClosestPonds(((Pond) p));
                } else {
                    removeFromClosestPonds(((Pond) p));
                }
            }
        }
    }

    public void addToClosestPonds(Pond p) {
        if (!closest.contains(p))
            closest.add(p);
        if (farthest.contains(p)) {
            farthest.remove(p);
        }
    }

    public void removeFromClosestPonds(Pond p) {
        if (!farthest.contains(p))
            farthest.add(p);
        if (closest.contains(p)) {
            closest.remove(p);
        }
    }

    public Group createDistanceLines() {
        Group distanceLines = new Group();

        for (Pond p : farthest) {
            Line lineTo = new Line(p.getCenter().getX(), p.getCenter().getY(), getBoundsInParent().getCenterX(),
                    getBoundsInParent().getCenterY());
            lineTo.setStroke(FARTHEST_POND_DISTANCE_COLOR);
            lineTo.setStrokeWidth(1);
            distanceLines.getChildren().addAll(lineTo, createDistanceValueLabel(((Pond) p), lineTo));
        }

        for (Pond p : closest) {
            Line lineTo = new Line(p.getCenter().getX(), p.getCenter().getY(), getBoundsInParent().getCenterX(),
                    getBoundsInParent().getCenterY());
            lineTo.setStroke(CLOSEST_POND_DISTANCE_COLOR);
            lineTo.setStrokeWidth(1);

            distanceLines.getChildren().addAll(lineTo, createDistanceValueLabel(((Pond) p), lineTo));

        }

        return distanceLines;
    }

    private GameText createDistanceValueLabel(Pond p, Line lineTo) {
        int distanceValue = (int) Math.sqrt(Math
                .pow(getBoundsInParent().getCenterX() - p.getBoundsInLocal().getCenterX(), 2)
                + (Math.pow(
                        getBoundsInParent().getCenterY() - p.getBoundsInLocal().getCenterY(),
                        2)));

        GameText g = new GameText(Integer.toString(distanceValue),
                new Point2D(lineTo.getStartX() + (lineTo.getEndX() - lineTo.getStartX()) / 2,
                        lineTo.getStartY() + (lineTo.getEndY() - lineTo.getStartY()) / 2),
                Color.RED);

        if (distanceToPond.containsKey(p))
            distanceToPond.replace(p, distanceValue);
        else
            distanceToPond.put(p, distanceValue);
        return g;
    }

    public void updateClosestPonds() {
        for (Pond p : closest) {
            p.update(BASELINE_OFFSET_SAME_AS_HEIGHT);
        }
    }

}