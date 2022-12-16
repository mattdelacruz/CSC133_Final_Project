package rainmaker;

import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Duration;
import rainmaker.gameobjects.Game;

public class GameApp extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        Game root = new Game();
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        scene = new Scene(root, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        stage.setScene(scene);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT ||
                    e.getCode() == KeyCode.RIGHT ||
                    e.getCode() == KeyCode.UP ||
                    e.getCode() == KeyCode.DOWN) {
                root.handleMovement(e);
            }

            if (e.getCode() == KeyCode.I) {
                root.handleIgnition();
            }
            if (e.getCode() == KeyCode.B) {
                root.handleBoundBoxes();
            }
            if (e.getCode() == KeyCode.R) {
                root.handleReset();
            }
            if (e.getCode() == KeyCode.SPACE) {
                root.handleSeeding();
                pause.setOnFinished(event -> {
                    root.handleRefueling();
                });
                pause.play();

            }
            if (e.getCode() == KeyCode.D) {
                root.handleDistanceLines();
            }
        });
        stage.setResizable(false);
        stage.setTitle("Rainmaker!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}