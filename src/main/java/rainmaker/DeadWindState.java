package rainmaker;

import javafx.scene.transform.Translate;
import rainmaker.gameobjects.Blimp;
import rainmaker.gameobjects.GameObject;

public class DeadWindState implements WindState {

    @Override
    public void move(Translate t, GameObject c) {
        return;
    }

    @Override
    public void addToWind(GameObject c, Wind w) {
        return;
    }

    @Override
    public void removeFromWind(GameObject c, Wind w) {
        w.detach(c);
    }

    @Override
    public void playSound(GameObject c) {
        if (c instanceof Blimp) {
            ((Blimp) c).stop();
        }
    }
}