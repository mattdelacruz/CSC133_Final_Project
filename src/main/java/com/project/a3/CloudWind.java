package com.project.a3;

import java.util.ArrayList;

import javafx.scene.transform.Translate;

class CloudWind implements WindSpeed {
    private static final double WIND_SPEED = 1;

    private ArrayList<Cloud> windBlowingOn = new ArrayList<Cloud>();

    @Override
    public void attach(Cloud c) {
        windBlowingOn.add(c);
    }

    @Override
    public void detach(Cloud c) {
        windBlowingOn.remove(c);
    }

    @Override
    public void updateWind() {
        for (Cloud c : windBlowingOn) {
            c.move(new Translate(WIND_SPEED * c.getRand(), 0));
        }
    }
}