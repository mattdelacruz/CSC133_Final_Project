package rainmaker;

import java.util.Iterator;

import javafx.scene.Node;
import rainmaker.gameobjects.Cloud;

public class Clouds extends GameObjectPane<Cloud> implements Iterable<Node> {
    private Wind wind = new Wind();

    public void clear() {
        getChildren().clear();
    }

    @Override
    public Iterator<Node> iterator() {
        return getChildren().iterator();
    }

    public void add(Cloud o) {
        getChildren().add(o);
        o.getState().addToWind(o, wind);
    }

    public int size() {
        return getChildren().size();
    }

    public void remove(Cloud o) {
        getChildren().remove(o);
        o.getState().removeFromWind(o, wind);
    }

    public void move() {
        wind.updateWind();
    }
}