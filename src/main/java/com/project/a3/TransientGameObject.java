package com.project.a3;

import javafx.scene.transform.Translate;

abstract class TransientGameObject extends GameObject {
    private WindState state = new DeadWindState();
    private WindState inPlay = new InPlayWindState();

    public void setState(WindState s) {
        state = s;
    }

    public WindState getState() {
        return state;
    }

    public void move(Translate t) {
        if (getBoundsInParent().getCenterX() > 0) {
            setState(inPlay);
        }

        state.move(t, this);
    }

    public WindState getInPlayState() {
        return inPlay;
    }

}
