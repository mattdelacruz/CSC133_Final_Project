package com.project.a3;

import javafx.scene.transform.Translate;

class AliveCloudState implements CloudState {

    @Override
    public void move(Translate t, GameObject c) {
        c.getTransforms().add(t);
    }

    @Override
    public void addToWind(GameObject c, CloudWind w) {
        w.attach(c);
    }

    @Override
    public void removeFromWind(GameObject c, CloudWind w) {
        return;
    }
}