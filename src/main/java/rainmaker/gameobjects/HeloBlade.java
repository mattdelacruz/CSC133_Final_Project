package rainmaker.gameobjects;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

public class HeloBlade extends GameObject {
    private static final int BLADE_LENGTH = 150;
    private static final int BLADE_SIZE = 5;
    private static final int PIVOT_SIZE = 2;
    private static final Color BLADE_COLOR = Color.LEMONCHIFFON;
    private static final Color PIVOT_COLOR = Color.BLACK;

    private Line blade;
    private Circle pivot;
    private Point2D center;

    HeloBlade(Point2D pos) {
        center = pos;
        blade = new Line(center.getX() - BLADE_LENGTH / 2, center.getY(),
                center.getX() + BLADE_LENGTH / 2, center.getY());
        blade.setStroke(BLADE_COLOR);
        blade.setStrokeWidth(BLADE_SIZE);
        pivot = new Circle(0, 0, PIVOT_SIZE);
        pivot.setFill(PIVOT_COLOR);
        pivot.setCenterX(center.getX());
        pivot.setCenterY(center.getY());
        getChildren().addAll(blade, pivot);
    }

    public void spinBlade(double spin) {
        getTransforms().add(new Rotate(spin, center.getX(), center.getY()));
    }
}