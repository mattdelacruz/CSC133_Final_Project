package com.project.a3;

import java.util.Iterator;
import javafx.scene.Node;

class Ponds extends GameObjectPane<Pond> implements Iterable<Node> {
    public void clear() {
        getChildren().clear();
    }

    public void add(Pond o) {
        getChildren().add(o);
    }

    public void updateBoundingBox() {
        for (Node n : getChildren()) {
            if (n instanceof Pond) {
                ((Pond) n).updateBoundingBox();
            }
        }
    }

    @Override
    public Iterator<Node> iterator() {
        return getChildren().iterator();
    }
}