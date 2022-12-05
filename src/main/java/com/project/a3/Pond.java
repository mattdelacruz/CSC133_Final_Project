package com.project.a3;

import java.util.concurrent.ThreadLocalRandom;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Pond extends GameObject {
    private static final Color POND_COLOR = Color.BLUE;
    private static final Color FONT_COLOR = Color.WHITE;
    private static final double PERCENT_VALUE = 0.01;
    private static final String LABEL_FORMAT = "%.0f%%";
    private static final int FILL_DISTANCE = 4;

    private BezierOval circle;
    private GameText pondLabel;
    private Point2D pos;
    private double currentRadius, maxRadius, percentAdder, currentArea,
            maxArea;
    private Rectangle fillBounds;

    Pond(Point2D s, double radius) {
        double rand = ThreadLocalRandom.current().nextDouble(1, radius + 1);
        pos = s;
        maxRadius = radius;
        currentRadius = Math.max(1, maxRadius - rand);
        currentArea = Math.PI * Math.pow(currentRadius, 2);
        maxArea = Math.PI * Math.pow(maxRadius, 2);
        percentAdder = (maxArea - currentArea) * PERCENT_VALUE;
        createPond();
    }

    public Point2D getCenter() {
        return new Point2D(circle.getCenterX(), circle.getCenterY());
    }

    public Rectangle getFillBounds() {
        return fillBounds;
    }

    public double getMaxArea() {
        return maxArea;
    }

    public double getCurrentArea() {
        return currentArea;
    }

    public void update(double factor) {
        if (currentArea < maxArea) {
            getChildren().clear();
            currentArea += (percentAdder * factor);
            createPond();
        }
    }

    private void createPond() {
        circle = new BezierOval(pos,
                Math.sqrt(currentArea / Math.PI), Math.sqrt(currentArea / Math.PI));
        circle.setFill(POND_COLOR);
        pondLabel = createLabel(String.format(LABEL_FORMAT,
                getSize()),
                new Point2D(
                        circle.getCenterX() -
                                (GameText.FONT_SIZE / 2),
                        circle.getCenterY() +
                                (GameText.FONT_SIZE / 2)),
                FONT_COLOR);
        fillBounds = new Rectangle(
                pos.getX() - (currentRadius * FILL_DISTANCE),
                pos.getY() - (currentRadius * FILL_DISTANCE), currentRadius * (FILL_DISTANCE * 2),
                currentRadius * (FILL_DISTANCE * 2));

        getChildren().addAll(circle, pondLabel);
    }

    public double getSize() {
        if ((currentArea / maxArea) * 100 > 100.0)
            return 100.0;
        return (currentArea / maxArea) * 100;
    }
}