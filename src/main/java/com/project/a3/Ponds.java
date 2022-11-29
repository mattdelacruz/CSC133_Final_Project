package com.project.a3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Ponds extends GameObjectPane<Pond> implements Iterable<Pond> {
    private List<Pond> pondList = new ArrayList<Pond>();

    Ponds() {
        super();
    }

    public void clear() {
        for (Iterator<Pond> iter = pondList.iterator(); iter.hasNext();) {
            Pond p = iter.next();
            iter.remove();
            pondList.remove(p);
            super.remove(p);
        }
    }

    public void add(Pond o) {
        super.add(o);
        pondList.add(o);
    }

    public void updateBoundingBox() {
        for (Iterator<Pond> iter = pondList.iterator(); iter.hasNext();) {
            Pond p = iter.next();
            p.updateBoundingBox();
        }
    }

    @Override
    public Iterator<Pond> iterator() {
        return pondList.iterator();
    }
}