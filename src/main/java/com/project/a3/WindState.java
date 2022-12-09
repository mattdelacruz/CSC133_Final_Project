package com.project.a3;

import javafx.scene.transform.Translate;

interface WindState {
    public void move(Translate t, GameObject c);

    public void addToWind(GameObject c, Wind w);

    public void removeFromWind(GameObject c, Wind w);
}