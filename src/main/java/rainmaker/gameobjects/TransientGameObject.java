package rainmaker.gameobjects;

import javafx.scene.transform.Translate;
import rainmaker.DeadWindState;
import rainmaker.InPlayWindState;
import rainmaker.Wind;
import rainmaker.WindState;

public abstract class TransientGameObject extends GameObject {
    private WindState state = new DeadWindState();
    private WindState inPlay = new InPlayWindState();
    private Wind wind = new Wind();

    public void add(TransientGameObject o) {
        o.getState().addToWind(o, wind);
    }

    public void move() {
        wind.updateWind();
    }

    public void setState(WindState s) {
        state = s;
    }

    public WindState getState() {
        return state;
    }

    public void remove(TransientGameObject o) {
        getChildren().remove(o);
        o.getState().removeFromWind(o, wind);
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
