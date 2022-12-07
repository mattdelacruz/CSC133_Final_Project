package com.project.a3;

import javafx.scene.transform.Translate;

interface CloudState {
    public void move(Translate t, Cloud c);

    public void addToWind(Cloud c, CloudWind w);

    public void removeFromWind(Cloud c, CloudWind w);
}