package com.project.a3;

import java.util.Iterator;

import javafx.scene.Node;

public class Blimps extends GameObjectPane<Blimp> implements Iterable<Node>{

    public void clear() {
        getChildren().clear();
    }

    @Override
    public Iterator<Node> iterator() {
        return getChildren().iterator();
    }

    public void add(Blimp o) {
        getChildren().add(o);
        o.getState().addToWind(o, wind);
    }

    public State getState() {
        return state;
    }
    
}
