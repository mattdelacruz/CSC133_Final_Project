package com.project.a3;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

abstract class GameObject extends Group {
    private static final Color BOUND_FILL = Color.TRANSPARENT;
    private static final Color BOUND_STROKE = Color.YELLOW;

    boolean isBoundOn = false;

    Rectangle bound = new Rectangle(getBoundsInParent().getMinX(),
            getBoundsInParent().getMinY(),
            getBoundsInParent().getWidth(),
            getBoundsInParent().getHeight());

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
        bound = new Rectangle(getBoundsInParent().getMinX(),
                getBoundsInParent().getMinY(),
                getBoundsInParent().getWidth(),
                getBoundsInParent().getHeight());
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
}