package rainmaker;

import java.util.Iterator;

import javafx.scene.Node;
import rainmaker.gameobjects.Cloud;

public class Clouds extends GameObjectPane<Cloud> implements Iterable<Node> {

    @Override
    public Iterator<Node> iterator() {
        return getChildren().iterator();
    }
}