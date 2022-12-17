package rainmaker.gameobjects;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;

public class GameText extends GameObject {
    private static final Scale SCALE = new Scale(1, -1);
    private static final FontWeight FONT_WEIGHT = FontWeight.NORMAL;
    private static final String FONT_FAMILY = "Arial";
    public static final int FONT_SIZE = 15;

    Label l = new Label();
    Color color;
    Point2D loc;

    GameText(String s, Point2D pos, Color c) {
        color = c;
        loc = pos;
        getTransforms().addAll(new Translate(loc.getX(),
                loc.getY()), SCALE);
        generateLabel(s, c);
        getChildren().add(l);
    }

    private void generateLabel(String s, Color c) {
        l = new Label(s);
        l.setFont(Font.font(FONT_FAMILY, FONT_WEIGHT, FONT_SIZE));
        l.setTextFill(c);
    }

    public void moveLabel(Point2D pos) {
        getTransforms().add(new Translate(pos.getX(), pos.getY()));
    }

    public void updateLabel(String s) {
        getChildren().remove(l);
        generateLabel(s, color);
        getChildren().add(l);
    }
}