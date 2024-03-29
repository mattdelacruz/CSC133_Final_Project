package rainmaker.gameobjects;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Helipad extends GameObject {
    private static final int GAP = 5;
    private static final Color HELIPAD_FILL = Color.BLACK;

    private Rectangle rect;
    private Circle circle;
    private Group letter = new Group();

    Helipad(Point2D s, Color c, int w, int h) {
        rect = new Rectangle(s.getX(), s.getY(), w, h);
        circle = new Circle(rect.getX() + rect.getWidth() / 2,
                rect.getY() + rect.getHeight() / 2,
                (rect.getWidth() / 2) - GAP);
        rect.setFill(HELIPAD_FILL);
        rect.setStroke(c);
        circle.setFill(HELIPAD_FILL);
        circle.setStroke(c);
        getChildren().addAll(rect, circle, letter);
    }

    public Point2D getCenter() {
        return new Point2D(circle.getCenterX(), circle.getCenterY());
    }
}