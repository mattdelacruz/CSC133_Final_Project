package rainmaker;

import javafx.scene.transform.Translate;
import rainmaker.gameobjects.GameObject;

public interface WindState {
    public void move(Translate t, GameObject c);

    public void addToWind(GameObject c, Wind w);

    public void removeFromWind(GameObject c, Wind w);
}