package rainmaker.gameobjects;

import java.util.concurrent.ThreadLocalRandom;

import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;
import rainmaker.AliveWindState;
import rainmaker.SoundPlayer;

public class Blimp extends TransientGameObject {
    private static final int CURVE_DISTANCE = 10;
    private static final Color BLIMP_STROKE = Color.RED;
    private static final Color BLIMP_FILL = Color.rgb(0, 0, 0, 0.5);
    private static final Color FONT_COLOR = Color.LEMONCHIFFON;
    private static final int MIN_FUEL = 5;
    private static final int MAX_FUEL = 10;
    private static final int FUEL_FACTOR = 1000;
    private static final double BLIMP_VOLUME = 0.1;

    private Group bezierCurves = new Group();
    private Ellipse ellipse;
    private GameText fuelLabel;
    private SoundPlayer blimpSound;
    private double rand = ThreadLocalRandom.current().nextDouble(0.5, 2);
    private int fuel;
    private double speed;

    public Blimp(Point2D pos, Point2D radius) {
        fuel = ThreadLocalRandom.current().nextInt(MIN_FUEL, MAX_FUEL);
        fuel *= FUEL_FACTOR;
        ellipse = new Ellipse(pos.getX(), pos.getY(), radius.getX(), radius.getY());
        fuelLabel = createLabel(Integer.toString(fuel),
                new Point2D(ellipse.getCenterX() - (GameText.FONT_SIZE / 2),
                        ellipse.getCenterY() + (GameText.FONT_SIZE / 2)),
                FONT_COLOR);
        ellipse.setFill(BLIMP_FILL);
        ellipse.setStroke(BLIMP_STROKE);
        createSounds();
        createCurveGroup(CURVE_DISTANCE);
        createCurveGroup(-CURVE_DISTANCE);
        setState(new AliveWindState());
        getChildren().addAll(ellipse, bezierCurves, fuelLabel);
    }

    private void createSounds() {
        blimpSound = new SoundPlayer(getClass().getResource("/blimp-sound.mp3").toExternalForm(), 1);
        blimpSound.setVolume(BLIMP_VOLUME);
    }

    private void updateLabel(int val) {
        fuelLabel.updateLabel(Integer.toString(val));
    }

    public void setSpeed(double s) {
        speed = s;
    }

    public double getSpeed() {
        return speed;
    }

    public double getRand() {
        return rand;
    }

    public void setFuel(int f) {
        fuel = f;
        updateLabel(f);
    }

    public int getFuel() {
        return fuel;
    }

    private void createCurveGroup(int curveDistance) {
        int h = curveDistance;
        for (int i = 0; i < ellipse.getRadiusY() / CURVE_DISTANCE; i++) {
            bezierCurves.getChildren().add(createCubicCurve(h));
            h += curveDistance;
        }
    }

    private Node createCubicCurve(int height) {
        double cubicCurveStartX, cubicCurveStartY, cubicCurveControlX1, cubicCurveControlY1, cubicCurveControlX2,
                cubicCurveEndX,
                cubicCurveEndY;

        cubicCurveStartX = cubicCurveStartY = cubicCurveControlX1 = cubicCurveControlY1 = cubicCurveControlX2 = cubicCurveEndX = cubicCurveEndY = 0;

        cubicCurveStartX = ellipse.getCenterX() - ellipse.getRadiusX();
        cubicCurveStartY = ellipse.getCenterY();
        cubicCurveEndX = ellipse.getCenterX() + ellipse.getRadiusX();
        cubicCurveEndY = ellipse.getCenterY();
        cubicCurveControlX1 = ellipse.getCenterX() - ellipse.getRadiusX() + CURVE_DISTANCE / 2;
        cubicCurveControlX2 = ellipse.getCenterX() + ellipse.getRadiusX() - CURVE_DISTANCE / 2;
        cubicCurveControlY1 = ellipse.getCenterY() + height;

        CubicCurve c = new CubicCurve(cubicCurveStartX, cubicCurveStartY, cubicCurveControlX1, cubicCurveControlY1,
                cubicCurveControlX2, cubicCurveControlY1, cubicCurveEndX, cubicCurveEndY);

        c.setStroke(BLIMP_STROKE);
        c.setFill(Color.TRANSPARENT);
        return c;
    }

    public void play() {
        blimpSound.play();
    }

    public void stop() {
        blimpSound.stop();
    }
}
