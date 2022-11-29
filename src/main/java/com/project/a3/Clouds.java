package com.project.a3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Clouds extends GameObjectPane<Cloud> implements Iterable<Cloud> {
    private List<Cloud> cloudList = new ArrayList<Cloud>();
    private CloudWind wind = new CloudWind();

    Clouds() {
        super();
    }

    public void clear() {
        for (Iterator<Cloud> iter = cloudList.iterator(); iter.hasNext();) {
            Cloud c = iter.next();
            iter.remove();
            cloudList.remove(c);
            super.remove(c);
        }
    }

    @Override
    public Iterator<Cloud> iterator() {
        return cloudList.iterator();
    }

    public void add(Cloud o) {
        super.add(o);
        cloudList.add(o);
        o.getState().addToWind(o, wind);
    }

    public int size() {
        return cloudList.size();
    }

    public void remove(Cloud o) {
        super.remove(o);
        cloudList.remove(o);
        o.getState().removeFromWind(o, wind);
    }

    public void updateBoundingBox() {
        for (Iterator<Cloud> iter = cloudList.iterator(); iter.hasNext();) {
            Cloud c = iter.next();
            c.updateBoundingBox();
        }
    }

    public void move() {
        wind.updateWind();
    }
}