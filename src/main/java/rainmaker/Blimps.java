package rainmaker;

import java.util.Iterator;

import javafx.scene.Node;
import rainmaker.gameobjects.Blimp;

public class Blimps extends GameObjectPane<Blimp> implements Iterable<Node> {

    @Override
    public Iterator<Node> iterator() {
        return getChildren().iterator();
    }

}
