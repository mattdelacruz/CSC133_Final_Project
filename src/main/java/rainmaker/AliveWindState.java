package rainmaker;

import javafx.scene.transform.Translate;
import rainmaker.gameobjects.GameObject;

public class AliveWindState implements WindState {

    @Override
    public void move(Translate t, GameObject c) {
        c.getTransforms().add(t);
    }

    @Override
    public void addToWind(GameObject c, Wind w) {
        w.attach(c);
    }

    @Override
    public void removeFromWind(GameObject c, Wind w) {
        return;
    }
}