package com.project.a3;

import javafx.scene.transform.Transform;

interface CloudState {
    public void move(Transform t, Cloud c);

    public void addToWind(Cloud c, CloudWind w);

    public void removeFromWind(Cloud c, CloudWind w);
}