package com.project.a3;

import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

class Game extends Pane implements Updateable {
    public static final int GAME_WIDTH = 800;
    public static final int GAME_HEIGHT = 800;
    private static final int HELI_RADIUS = GAME_WIDTH / 30;
    private static final int START_FUEL = 250000;
    private static final int POND_SIZE = GAME_WIDTH / 10;
    private static final int CLOUD_SIZE = GAME_WIDTH / 15;
    private static final int HELIPAD_SIZE = GAME_WIDTH / 5;
    private static final int CLOUD_DECAY_TIME = 2500;
    private static final int POND_UPDATE_TIME = 600;
    private static final int POND_SPAWN = 3;
    private static final int CLOUD_SPAWN = 5;
    private static final double PERCENT_THRESHOLD = 30.0;
    private static final Color HELIPAD_COLOR = Color.GRAY;
    private static final Color HELI_COLOR = Color.YELLOW;
    private static final Scale SCALE = new Scale(1, -1);
    private static final String LOSE_TEXT = "You have lost! Play again?";
    private static final String WIN_TEXT = "You have won! Play again?";

    private Helicopter heli;
    private Helipad helipad;
    private Ponds pondPane = new Ponds();
    private Clouds cloudPane = new Clouds();
    private Alert alert = new Alert(AlertType.CONFIRMATION);
    private ButtonType Yes = new ButtonType("Yes", ButtonData.YES);
    private ButtonType No = new ButtonType("No", ButtonData.NO);
    private GameBackground background = new GameBackground();
    private Group distanceLines = new Group();
    private Rectangle bounds = new Rectangle(GAME_WIDTH + (CLOUD_SIZE * 2), 0, 1, GAME_HEIGHT);
    private Pond closest = null;

    boolean isBoundsOn = false;
    boolean isDistanceLinesOn = false;
    private int score = 0;

    public Game() {
        init();
        startAnimation();
    }

    private void startAnimation() {
        AnimationTimer loop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                checkCloudOutOfBounds();
                checkNumCloudsOnScreen();
                decreaseClouds(now);
                resetDistanceLines();
                updateClosestPond(now);
                checkIfEndGame();
            }

            private void checkIfEndGame() {
                if (checkIfLose() || checkIfWon()) {
                    stop();
                    getEndResult();
                }
            }

            private void decreaseClouds(long now) {
                if (now % CLOUD_DECAY_TIME == 0) {
                    for (Cloud c : cloudPane)
                        c.decrease();
                }
            }

            private void updateClosestPond(long now) {
                for (Cloud c : cloudPane) {
                    closest = c.findClosestPond(pondPane.iterator());
                    if (closest != null) {
                        distanceLines.getChildren().add(c.createDistanceLine());

                        if (c.getPercentage() > PERCENT_THRESHOLD &&
                                now % POND_UPDATE_TIME == 0) {
                            closest.update(c.getPercentageToPond());
                        }
                    }
                }
            }

            private void resetDistanceLines() {
                distanceLines.getChildren().clear();

                if (!isDistanceLinesOn) {
                    distanceLines.setVisible(false);
                }
            }

            private void checkNumCloudsOnScreen() {
                int dice = ThreadLocalRandom.current().nextInt(0, 1);
                if (cloudPane.size() <= 2) {
                    createCloud();
                }

                if (cloudPane.size() == 3 || cloudPane.size() == 4 && 
                dice == 1) {
                    createCloud();
                }
            }

            private void checkCloudOutOfBounds() {
                for (Iterator<Cloud> iter = cloudPane.iterator(); iter.hasNext();) {
                    Cloud c = iter.next();
                    if (c.getBoundsInParent().intersects(bounds.getBoundsInLocal())) {
                        c.setState(new DeadCloudState());
                        iter.remove();
                        cloudPane.remove(c);
                    }
                }
            }
        };
        loop.start();
    }

    private void init() {
        setUpUI();
    }

    private void setUpUI() {
        createGameObjects();
        SCALE.setPivotY(GAME_HEIGHT / 2);
        getTransforms().add(SCALE);
        getChildren().addAll(background, helipad, pondPane,
                cloudPane, heli, bounds, distanceLines);
    }

    private void createGameObjects() {
        createHelipad();
        createHelicopter();
        for (int i = 0; i < POND_SPAWN; i++) {
            createPond();
        }

        for (int i = 0; i < CLOUD_SPAWN; i++) {
            createCloud();
        }
    }

    private void getEndResult() {
        Platform.runLater(() -> {
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == Yes) {
                handleReset();
                startAnimation();
            } else if (result.isPresent() && result.get() == No) {
                Platform.exit();
            }
        });
    }

    private boolean checkIfLose() {
        if (heli.getFuel() == 0) {
            createDialogBox(LOSE_TEXT);
            return true;
        }
        return false;
    }

    private boolean checkIfWon() {
        double currentCapacity = 0;
        double maxCapacity = 0;

        for (Pond p : pondPane) {
            currentCapacity += p.getCurrentArea();
            maxCapacity += p.getMaxArea();
        }

        if (currentCapacity / maxCapacity < .8) {
            return false;
        }

        if (heli.getFuel() > 0 && heli.getBoundsInParent().intersects(helipad.getBoundsInParent())
                && !heli.getState().isIgnitionOn()) {
            score = (int) (currentCapacity / maxCapacity) * heli.getFuel();
            createDialogBox(WIN_TEXT);
            return true;
        }
        return false;
    }

    private void createDialogBox(String s) {
        alert = new Alert(AlertType.CONFIRMATION);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(Yes, No);
        alert.setTitle(s);
        alert.setContentText(s + " Your score: " + score);
    }

    private void createHelipad() {
        helipad = new Helipad(new Point2D(
                GAME_WIDTH / 2 - HELIPAD_SIZE / 2,
                HELIPAD_SIZE / 2),
                HELIPAD_COLOR,
                HELIPAD_SIZE,
                HELIPAD_SIZE);
    }

    private void createHelicopter() {
        heli = new Helicopter(new Point2D(
                helipad.getCenter().getX(),
                helipad.getCenter().getY()),
                HELI_COLOR,
                HELI_RADIUS,
                START_FUEL);
    }

    private void createPond() {
        double maxX = GAME_WIDTH - POND_SIZE;
        double minX = POND_SIZE;
        double maxY = GAME_HEIGHT - POND_SIZE;
        double minY = helipad.getBoundsInParent().getMaxY() + POND_SIZE;

        Pond p;
        if (pondPane != null) {
            do {
                p = new Pond(new Point2D(
                        ThreadLocalRandom.current().nextDouble(minX, maxX + 1),
                        ThreadLocalRandom.current().nextDouble(minY, maxY +
                                1)),
                        POND_SIZE);
            } while (checkPondIntersection(p));
        } else {
            p = new Pond(new Point2D(
                    ThreadLocalRandom.current().nextDouble(minX, maxX + 1),
                    ThreadLocalRandom.current().nextDouble(minY, maxY + 1)),
                    POND_SIZE);
        }
        pondPane.add(p);
    }

    private void createCloud() {
        double maxX = 0;
        double minX = -GAME_WIDTH / 2;
        double maxY = GAME_HEIGHT - (CLOUD_SIZE / 2);
        double minY = helipad.getBoundsInParent().getMaxY() + CLOUD_SIZE;

        Cloud c;
        // if (cloudPane != null) {
        //     do {
        //         c = new Cloud(new Point2D(
        //                 ThreadLocalRandom.current().nextDouble(minX, maxX + 1),
        //                 ThreadLocalRandom.current().nextDouble(minY, maxY + 1)), CLOUD_SIZE);
        //     } while (checkCloudIntersection(c));
        // } else {
        //     c = new Cloud(new Point2D(
        //             ThreadLocalRandom.current().nextDouble(minX, maxX + 1),
        //             ThreadLocalRandom.current().nextDouble(minY, maxY + 1)), CLOUD_SIZE);
        // }

        c = new Cloud(new Point2D(
                    ThreadLocalRandom.current().nextDouble(minX, maxX + 1),
                    ThreadLocalRandom.current().nextDouble(minY, maxY + 1)), CLOUD_SIZE);

        cloudPane.add(c);
    }

    private boolean checkPondIntersection(GameObject p) {
        for (Pond pond : pondPane) {
            if (p.intersects(pond.getBoundsInLocal()))
                return true;
        }
        return false;
    }

    private boolean checkCloudIntersection(GameObject c) {
        for (Cloud cloud : cloudPane) {
            if (c.intersects(cloud.getBoundsInLocal()))
                return true;
        }
        return false;
    }

    public void handleMovement(KeyEvent e) {
        if (e.getCode() == KeyCode.LEFT) {
            heli.left();
        }
        if (e.getCode() == KeyCode.RIGHT) {
            heli.right();
        }
        if (e.getCode() == KeyCode.UP) {
            heli.increaseSpeed();
        }
        if (e.getCode() == KeyCode.DOWN) {
            heli.decreaseSpeed();
        }
    }

    public void handleSeeding() {
        for (Cloud c : cloudPane) {
            if (heli.getBoundsInParent().intersects(c.getBoundsInParent()) && heli.getState().isIgnitionOn()) {
                c.update();
            }
        }
    }

    public void handleIgnition() {
        heli.engineStart();
    }

    public void handleBoundBoxes() {
        heli.showBoundingBox();
        helipad.showBoundingBox();
        for (Pond p : pondPane) {
            p.showBoundingBox();
        }
        for (Cloud c : cloudPane) {
            c.showBoundingBox();
        }
    }

    public void handleReset() {
        handleBoundBoxes();
        getChildren().clear();
        getTransforms().clear();
        pondPane.clear();
        cloudPane.clear();
        init();
    }

    @Override
    public void update() {
        heli.moveHeli();
        heli.updateBoundingBox();
        for (Pond p : pondPane) {
            p.updateBoundingBox();
        }
        for (Cloud c : cloudPane) {
            c.updateBoundingBox();
        }
        if (heli.isIgnitionOn()) {
            heli.consumeFuel();
        }
        cloudPane.move();
    }

    public void handleDistanceLines() {
        if (distanceLines.isVisible()) {
            distanceLines.setVisible(false);
            isDistanceLinesOn = false;
        } else {
            distanceLines.setVisible(true);
            isDistanceLinesOn = true;
        }
    }
}