package com.project.a3;

import javafx.scene.transform.Translate;

class DeadCloudState implements CloudState {

    @Override
    public void move(Translate t, GameObject c) {
        return;
    }

    @Override
    public void addToWind(GameObject c, CloudWind w) {
        return;
    }

    @Override
    public void removeFromWind(GameObject c, CloudWind w) {
        w.detach(c);
    }
}