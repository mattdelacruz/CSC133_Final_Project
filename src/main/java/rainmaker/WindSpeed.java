package rainmaker;

import rainmaker.gameobjects.GameObject;

public interface WindSpeed {
    public void attach(GameObject c);

    public void detach(GameObject c);

    public void updateWind();
}