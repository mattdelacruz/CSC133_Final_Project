package rainmaker;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import rainmaker.gameobjects.Blimp;
import rainmaker.gameobjects.GameObject;
import rainmaker.gameobjects.TransientGameObject;

abstract class GameObjectPane<T> extends Pane {
    public void add(Node o) {
        if (o instanceof TransientGameObject) {
            ((TransientGameObject) o).add(((TransientGameObject) o));
        }
        getChildren().add(o);
    }

    public void clear() {
        getChildren().clear();
        getTransforms().clear();
    }

    public void remove(T o) {
        getChildren().remove(o);
    }

    public int size() {
        return getChildren().size();
    }

    public void move() {
        for (Node o : getChildren()) {
            if (o instanceof TransientGameObject) {
                ((TransientGameObject) o).move();
            }
        }
    }

    public void playSounds() {
        for (Node b : getChildren()) {
            if (b instanceof Blimp) {
                ((Blimp) b).getState().playSound((Blimp) b);
            }
        }
    }

    public void stopAllSounds() {
        for (Node b : getChildren()) {
            if (b instanceof Blimp) {
                ((Blimp) b).stop();
            }
        }
    }

    public void updateBoundingBox() {
        for (Node o : getChildren()) {
            if (o instanceof GameObject)
                ((GameObject) o).updateBoundingBox();
        }
    }

    public void showBoundingBox() {
        for (Node o : getChildren()) {
            if (o instanceof GameObject)
                ((GameObject) o).showBoundingBox();
        }
    }

    public boolean checkObjectCollision(GameObject o) {
        for (Node n : getChildren()) {
            if (n instanceof GameObject) {
                if (o.intersects(((GameObject) n).getBoundsInLocal()))
                    return true;
            }
        }
        return false;
    }

    public void checkOutOfBounds(double minBounds) {
        ArrayList<TransientGameObject> toRemove = new ArrayList<TransientGameObject>();
        for (Node o : getChildren()) {
            if (o instanceof TransientGameObject) {
                if (o.getBoundsInParent().getMinX() >= minBounds) {
                    ((TransientGameObject) o).setState(new DeadWindState());
                    toRemove.add((TransientGameObject) o);
                }
            }
        }

        if (toRemove.isEmpty())
            return;

        for (TransientGameObject c : toRemove) {
            c.getTransforms().clear();
            getChildren().remove(c);
        }
        Runtime.getRuntime().gc();
    }

}