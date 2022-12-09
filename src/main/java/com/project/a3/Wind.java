package com.project.a3;

import java.util.ArrayList;

import javafx.scene.transform.Translate;

public class Wind implements WindSpeed {
    private static final double WIND_SPEED = 1;
    private ArrayList<GameObject> windBlowingOn = new ArrayList<GameObject>();

    @Override
    public void attach(GameObject o) {
        windBlowingOn.add(o);
    }

    @Override
    public void detach(GameObject o) {
        windBlowingOn.remove(o);
    }

    @Override
    public void updateWind() {
        for (GameObject o : windBlowingOn) {
            if (o instanceof Cloud)
                ((Cloud) o).move(new Translate(WIND_SPEED * ((Cloud) o).getRand(), 0));
            if (o instanceof Blimp) {
                ((Blimp) o).move(new Translate(WIND_SPEED * ((Blimp) o).getRand(), 0));
            }
        }
    }
}