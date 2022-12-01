package com.project.a3;

import javafx.scene.transform.Translate;

class DeadCloudState implements CloudState {

    @Override
    public void move(Translate t, Cloud c) {
        return;
    }

    @Override
    public void addToWind(Cloud c, CloudWind w) {
        return;
    }

    @Override
    public void removeFromWind(Cloud c, CloudWind w) {
        w.detach(c);
    }
}