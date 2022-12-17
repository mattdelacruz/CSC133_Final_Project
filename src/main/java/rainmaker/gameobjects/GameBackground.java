package rainmaker.gameobjects;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

public class GameBackground extends GameObject {
    private static final Scale SCALE = new Scale(1, -1);
    private final String BACKGROUND_SOURCE = getClass().getResource("/rainmaker-background.jpg").toExternalForm();

    private BackgroundImage backgroundImage;
    private Pane backgroundPane;

    GameBackground() {
        backgroundImage = new BackgroundImage(
                new Image(
                        BACKGROUND_SOURCE,
                        Game.GAME_WIDTH, Game.GAME_HEIGHT, false, false),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        SCALE.setPivotY(Game.GAME_HEIGHT / 2);
        backgroundPane = new Pane();
        backgroundPane.setBackground(new Background(backgroundImage));
        backgroundPane.setPrefWidth(Game.GAME_WIDTH);
        backgroundPane.setPrefHeight(Game.GAME_HEIGHT);
        backgroundPane.getTransforms().add(SCALE);
        getChildren().add(backgroundPane);
    }
}