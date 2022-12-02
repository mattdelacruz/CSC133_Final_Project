package com.project.a3;

import javafx.scene.transform.Translate;

class InPlayCloudState implements CloudState {

    @Override
    public void move(Translate t, Cloud c) {
        c.getTransforms().add(t);
    }

    @Override
    public void addToWind(Cloud c, CloudWind w) {
        return;
    }

    @Override
    public void removeFromWind(Cloud c, CloudWind w) {
        return;
    }
}