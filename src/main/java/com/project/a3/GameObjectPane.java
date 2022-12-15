package com.project.a3;

import java.util.ArrayList;

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

    public void checkOutOfBounds(double minBounds) {
        ArrayList<TransientGameObject> toRemove = new ArrayList<TransientGameObject>();
        for (Node o : getChildren()) {
            if (o instanceof TransientGameObject) {
                if (o.getBoundsInParent().getMinX() >= minBounds) {
                    ((TransientGameObject) o).setState(new DeadWindState());
                    toRemove.add((TransientGameObject) o);
                }
            }
        }
        for (TransientGameObject c : toRemove) {
            getChildren().remove(c);
        }
    }
}