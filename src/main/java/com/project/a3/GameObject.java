package com.project.a3;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

abstract class GameObject extends Group {
    private static final Color BOUND_FILL = Color.TRANSPARENT;
    private static final Color BOUND_STROKE = Color.YELLOW;

    private boolean isBoundOn = false;
    private int distanceValue, minDistance;

    Rectangle bound = new Rectangle(getBoundsInLocal().getMinX(),
            getBoundsInLocal().getMinY(),
            getBoundsInLocal().getWidth(),
            getBoundsInLocal().getHeight());

    public void showBoundingBox() {
        updateBound();
        checkIfBoundOn();
    }

    public void updateBoundingBox() {
        updateBound();
        if (isBoundOn) {
            getChildren().add(bound);
            bound.setFill(BOUND_FILL);
            bound.setStroke(BOUND_STROKE);
        }
    }

    public GameText createLabel(String s, Point2D p, Color c) {
        return new GameText(s, p, c);
    }

    private void updateBound() {
        getChildren().remove(bound);
        bound = new Rectangle(getBoundsInLocal().getMinX(),
                getBoundsInLocal().getMinY(),
                getBoundsInLocal().getWidth(),
                getBoundsInLocal().getHeight());
    }

    private void checkIfBoundOn() {
        bound.setFill(BOUND_FILL);
        if (!isBoundOn) {
            getChildren().add(bound);
            bound.setStroke(BOUND_STROKE);
            isBoundOn = true;

        } else if (isBoundOn) {
            getChildren().remove(bound);
            isBoundOn = false;
        }
    }

    public Group createDistanceLine(Object lineTo) {
        Group distanceGroup = new Group();

        if (lineTo instanceof GameObject) {
            Line distanceLine = new Line(getBoundsInParent().getCenterX(),
                    getBoundsInParent().getCenterY(),
                    ((GameObject) lineTo).getBoundsInLocal().getCenterX(), ((GameObject) lineTo).getBoundsInLocal().getCenterY());
            distanceLine.setStroke(Color.MAGENTA);
            distanceValue = (int) Math.sqrt(Math
                    .pow(getBoundsInParent().getCenterX() - ((GameObject) lineTo).getBoundsInLocal().getCenterX(), 2)
                    + (Math.pow(getBoundsInParent().getCenterY() - ((GameObject) lineTo).getBoundsInLocal().getCenterY(),
                            2)));
            if (minDistance == 0) {
                minDistance = distanceValue;
            }
            else if (minDistance > distanceValue) {
                minDistance = distanceValue;
            }
            

            GameText g = new GameText(Integer.toString(distanceValue),
                    new Point2D(distanceLine.getStartX() + (distanceLine.getEndX() - distanceLine.getStartX()) / 2,
                            distanceLine.getStartY() + (distanceLine.getEndY() - distanceLine.getStartY()) / 2),
                    Color.RED);
            distanceGroup.getChildren().addAll(distanceLine, g);
        }
        return distanceGroup;
    }

    public int getDistanceValue() {
        return distanceValue;
    }

    public int getMinDistance() {
        return minDistance;
    }
}