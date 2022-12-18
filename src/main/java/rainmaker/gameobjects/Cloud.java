package rainmaker.gameobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import rainmaker.AliveWindState;
import rainmaker.Ponds;
import rainmaker.Updateable;

public class Cloud extends TransientGameObject implements Updateable {
    private static final double MAX_COLOR_VALUE = 255;
    private static final double MIN_COLOR_VALUE = 155;
    private static final double PERCENT_VALUE = 0.01;
    private static final double PERCENT_ADDER = (MAX_COLOR_VALUE -
            MIN_COLOR_VALUE) * PERCENT_VALUE;
    private static final double SIZE_X_FACTOR = 1.4;
    private static final String LABEL_FORMAT = "%.0f%%";
    private static final Color FONT_COLOR = Color.BLACK;
    private static final Color CLOSEST_POND_DISTANCE_COLOR = Color.DARKVIOLET;
    private static final Color FARTHEST_POND_DISTANCE_COLOR = Color.LEMONCHIFFON;

    private BezierOval circle;
    private GameText cloudLabel;
    private ArrayList<Pond> closest = new ArrayList<Pond>();
    private ArrayList<Pond> farthest = new ArrayList<Pond>();
    private Map<Pond, Integer> distanceToPond = new HashMap<Pond, Integer>();
    private double percentage, cloudColorValue;
    private double rand = ThreadLocalRandom.current().nextDouble(0.5, 2);

    Cloud(Point2D s, double size) {
        circle = new BezierOval(new Point2D(s.getX(), s.getY()), size * SIZE_X_FACTOR, size, 0);
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
            Line lineTo = createDistanceLine(((Pond) p), FARTHEST_POND_DISTANCE_COLOR);
            distanceLines.getChildren().addAll(lineTo, createDistanceValueLabel(((Pond) p), lineTo));
        }

        for (Pond p : closest) {
            Line lineTo = createDistanceLine(((Pond) p), CLOSEST_POND_DISTANCE_COLOR);
            distanceLines.getChildren().addAll(lineTo, createDistanceValueLabel(((Pond) p), lineTo));

        }

        return distanceLines;
    }

    private Line createDistanceLine(Pond p, Color c) {
        Line lineTo = new Line(p.getCenter().getX(), p.getCenter().getY(), getBoundsInParent().getCenterX(),
                getBoundsInParent().getCenterY());
        lineTo.setStroke(c);
        lineTo.setStrokeWidth(1);

        return lineTo;
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
        if (closest.isEmpty()) {
            return;
        }

        for (Pond p : closest) {
            p.update(Math.abs(p.getBoundsInLocal().getCenterX() - distanceToPond.get(p))
                    / p.getBoundsInLocal().getMinX());
        }
    }

}