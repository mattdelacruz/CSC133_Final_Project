package com.project.a3;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

class GameObjectPane<T> extends Pane {
    GameObjectPane() {
    }

    public void add(Node o) {
        getChildren().add(o);
    }

    public void remove(T o) {
        getChildren().remove(o);
    }
}