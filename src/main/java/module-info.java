module rainmaker {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens rainmaker to javafx.fxml;

    exports rainmaker;
    exports rainmaker.gameobjects;

}
