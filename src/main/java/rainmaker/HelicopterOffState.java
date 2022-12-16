package rainmaker;

import rainmaker.gameobjects.Helicopter;

public class HelicopterOffState implements HelicopterState {

    @Override
    public boolean isIgnitionOn() {
        return false;
    }

    @Override
    public void moveHeli(Helicopter heli) {
        return;
    }

    @Override
    public void engineStart(Helicopter heli) {
        heli.setState(new HelicopterStartingState());
    }

    @Override
    public void increaseSpeed(Helicopter heli) {
        return;
    }

    @Override
    public void decreaseSpeed(Helicopter heli) {
        return;

    }
}