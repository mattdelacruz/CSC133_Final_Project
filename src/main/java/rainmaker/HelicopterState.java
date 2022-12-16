package rainmaker;

import rainmaker.gameobjects.Helicopter;

public interface HelicopterState {

    public boolean isIgnitionOn();

    public void engineStart(Helicopter heli);

    public void moveHeli(Helicopter heli);

    public void increaseSpeed(Helicopter heli);

    public void decreaseSpeed(Helicopter heli);

}