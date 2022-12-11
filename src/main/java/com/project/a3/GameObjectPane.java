package com.project.a3;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

abstract class GameObjectPane<T> extends Pane {
    public void add(Node o) {
        getChildren().add(o);
    }

    public void remove(T o) {
        getChildren().remove(o);
    }

    public void updateBoundingBox() {
        for (Node o : getChildren()) {
            if (o instanceof GameObject)
                ((GameObject) o).updateBoundingBox();
        }
    }

    public void showBoundingBox() {
        for (Node o : getChildren()) {
            if (o instanceof GameObject)
                ((GameObject) o).showBoundingBox();
        }
    }

    public boolean checkObjectCollision(GameObject o) {
        for (Node n : getChildren()) {
            if (n instanceof GameObject) {
                if (o.intersects(((GameObject) n).getBoundsInLocal()))
                    return true;
            }
        }
        return false;
    }
}