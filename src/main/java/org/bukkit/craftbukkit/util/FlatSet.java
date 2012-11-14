package org.bukkit.craftbukkit.util;

import java.util.Iterator;

public class FlatSet extends FlatLookup {

    private static final Object PRESENT = new Object();

    public void add(long msw, long lsw) {
        put(msw, lsw, PRESENT);
    }

    public long popFirst() {
        Iterator iter = mapLookup.keySet().iterator();
        long ret = (Long) iter.next();
        iter.remove();
        return ret;
    }

    public boolean contains(long msw, long lsw) {
        return containsKey(msw, lsw);
    }

    public boolean isEmpty() {
        return mapLookup.isEmpty();
    }

    public int size() {
        return mapLookup.size();
    }
}
