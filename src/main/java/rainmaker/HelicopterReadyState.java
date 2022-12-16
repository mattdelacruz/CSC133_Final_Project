package rainmaker;

import javafx.scene.transform.Translate;
import rainmaker.gameobjects.Helicopter;

public class HelicopterReadyState implements HelicopterState {

    @Override
    public boolean isIgnitionOn() {
        return true;
    }

    @Override
    public void moveHeli(Helicopter heli) {
        heli.getTransforms().add(new Translate(0, heli.getSpeed()));
    }

    @Override
    public void engineStart(Helicopter heli) {
        heli.setSpeed(0);
        heli.setState(new HelicopterStoppingState());
    }

    @Override
    public void increaseSpeed(Helicopter heli) {
        if (heli.getSpeed() <= Helicopter.MAX_SPEED) {
            heli.setSpeed(heli.getSpeed() + Helicopter.ACCELERATION);
        }
    }

    @Override
    public void decreaseSpeed(Helicopter heli) {
        if (heli.getSpeed() >= Helicopter.MIN_SPEED)
            heli.setSpeed(heli.getSpeed() - Helicopter.ACCELERATION);
        if (heli.getSpeed() > 0 && heli.getSpeed() < 1) {
            heli.setSpeed(0);
        }
    }
}