package com.project.a3;

import javafx.scene.transform.Transform;

class InPlayCloudState implements CloudState {

    @Override
    public void move(Transform t, Cloud c) {
        c.getCloudCircle().getTransforms().add(t);
        c.getCloudLabel().getTransforms().add(t);
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