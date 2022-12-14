package com.project.a3;

import javafx.scene.transform.Translate;

public class InPlayWindState implements WindState {

    @Override
    public void move(Translate t, GameObject c) {
        c.getTransforms().add(t);
    }

    @Override
    public void addToWind(GameObject c, Wind w) {
        return;
    }

    @Override
    public void removeFromWind(GameObject c, Wind w) {
        return;
    }
}