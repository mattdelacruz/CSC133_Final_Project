package com.project.a3;

import java.util.Iterator;
import javafx.scene.Node;

class Clouds extends GameObjectPane<Cloud> implements Iterable<Node> {
    private CloudWind wind = new CloudWind();

    public void clear() {
        getChildren().clear();
    }

    @Override
    public Iterator<Node> iterator() {
        return getChildren().iterator();
    }

    public void add(Cloud o) {
        getChildren().add(o);
        o.getState().addToWind(o, wind);
    }

    public int size() {
        return getChildren().size();
    }

    public void remove(Cloud o) {
        getChildren().remove(o);
        o.getState().removeFromWind(o, wind);
    }

    public void updateBoundingBox() {
        for (Node n : getChildren()) {
            if (n instanceof Cloud) {
                ((Cloud) n).updateBoundingBox();
            }
        }
    }

    public void move() {
        wind.updateWind();
    }
}