package com.project.a3;

class HelicopterStartingState implements HelicopterState {

    @Override
    public boolean isIgnitionOn() {
        return true;
    }

    @Override
    public void moveHeli(Helicopter heli) {
        if (heli.getSpinSpeed() < Helicopter.MAX_SPIN_SPEED) {
            heli.setSpinSpeed(heli.getSpinSpeed() + 0.1);
        } else {
            heli.setState(new HelicopterReadyState());
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
}