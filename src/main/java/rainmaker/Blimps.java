package rainmaker;

import java.util.Iterator;

import javafx.scene.Node;
import rainmaker.gameobjects.Blimp;

public class Blimps extends GameObjectPane<Blimp> implements Iterable<Node> {
    private Wind wind = new Wind();

    public void clear() {
        getChildren().clear();
    }

    @Override
    public Iterator<Node> iterator() {
        return getChildren().iterator();
    }

    public void add(Blimp o) {
        getChildren().add(o);
        o.getState().addToWind(o, wind);
    }

    public void move() {
        wind.updateWind();
    }

    public int size() {
        return getChildren().size();
    }

}
