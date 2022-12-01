package com.project.a3;

import javafx.scene.Node;
import javafx.scene.shape.Ellipse;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

class AliveCloudState implements CloudState {

    @Override
    public void move(Translate t, Cloud c) {  
        c.getCloudCircle().move(t);
        c.getCloudLabel().getTransforms().add(t);
    }

    @Override
    public void addToWind(Cloud c, CloudWind w) {
        w.attach(c);
    }

    @Override
    public void removeFromWind(Cloud c, CloudWind w) {
        return;
    }
}