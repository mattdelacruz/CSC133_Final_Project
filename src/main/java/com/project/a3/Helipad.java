package com.project.a3;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

class Helipad extends GameObject {
    private static final int GAP = 5;
    private static final Color HELIPAD_FILL = Color.TRANSPARENT;

    private Rectangle rect;
    private Circle circle;

    Helipad(Point2D s, Color c, int w, int h) {
        rect = new Rectangle(s.getX(), s.getY(), w, h);
        circle = new Circle(rect.getX() + rect.getWidth() / 2,
                rect.getY() + rect.getHeight() / 2,
                (rect.getWidth() / 2) - GAP);
        rect.setFill(HELIPAD_FILL);
        rect.setStroke(c);
        circle.setFill(HELIPAD_FILL);
        circle.setStroke(c);
        getChildren().addAll(rect, circle);
    }

    public Point2D getCenter() {
        return new Point2D(circle.getCenterX(), circle.getCenterY());
    }
}