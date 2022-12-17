package rainmaker;

import rainmaker.gameobjects.Helicopter;

public class HelicopterStoppingState implements HelicopterState {

    @Override
    public boolean isIgnitionOn() {
        return false;
    }

    @Override
    public void moveHeli(Helicopter heli) {
        if (heli.getSpinSpeed() > 0) {
            heli.setSpinSpeed(heli.getSpinSpeed() - 0.1);
        } else {
            heli.setState(heli.getOffState());
            heli.setSpinSpeed(0);
        }
    }

    @Override
    public void engineStart(Helicopter heli) {
        heli.setSpeed(0);
        heli.setState(new HelicopterStoppingState());
    }

    @Override
    public void increaseSpeed(Helicopter heli) {
        return;
    }

    @Override
    public void decreaseSpeed(Helicopter heli) {
        return;
    }

    @Override
    public void playSound(Helicopter heli) {
        heli.getFlyingSound().stop();
        heli.getStartUpSound().stop();
        heli.getShutDownSound().play();
    }
}