package com.project.a3;

import javafx.scene.transform.Translate;

interface CloudState {
    public void move(Translate t, GameObject c);

    public void addToWind(GameObject c, CloudWind w);

    public void removeFromWind(GameObject c, CloudWind w);
}