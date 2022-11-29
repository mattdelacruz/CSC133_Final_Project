package com.project.a3;

class HelicopterStoppingState implements HelicopterState {

    @Override
    public boolean isIgnitionOn() {
        return false;
    }

    @Override
    public void moveHeli(Helicopter heli) {
        if (heli.getSpinSpeed() > 0) {
            heli.setSpinSpeed(heli.getSpinSpeed() - 0.1);
        } else {
            heli.setState(new HelicopterOffState());
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
}