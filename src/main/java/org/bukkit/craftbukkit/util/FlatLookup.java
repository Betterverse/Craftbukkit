package org.bukkit.craftbukkit.util;

import java.util.Collection;

public class FlatLookup<E> {

    private static final int FLAT_LOOKUP_SIZE = 512;
    private final Object[][] flatLookup = new Object[FLAT_LOOKUP_SIZE * 2][FLAT_LOOKUP_SIZE * 2];
    protected final LongObjectHashMap<E> mapLookup = new LongObjectHashMap<E>();

    public E get(long hash) {
        return get(LongHash.msw(hash), LongHash.lsw(hash));
    }

    public E get(long msw, long lsw) {
        // Long is used to avoid integer overflow
        long acx = Math.abs(msw);
        long acz = Math.abs(lsw);
        E value;
        if (acx < FLAT_LOOKUP_SIZE && acz < FLAT_LOOKUP_SIZE) {
            value = (E) flatLookup[(int) (msw + FLAT_LOOKUP_SIZE)][(int) (lsw + FLAT_LOOKUP_SIZE)];
        } else {
            value = mapLookup.get(LongHash.toLong((int) msw, (int) lsw));
        }
        return value;
    }

    public void put(long key, E value) {
        put(LongHash.msw(key), LongHash.lsw(key), value);
    }

    public void put(long msw, long lsw, E value) {
        long acx = Math.abs(msw);
        long acz = Math.abs(lsw);
        if (acx < FLAT_LOOKUP_SIZE && acz < FLAT_LOOKUP_SIZE) {
            flatLookup[(int) (msw + FLAT_LOOKUP_SIZE)][(int) (lsw + FLAT_LOOKUP_SIZE)] = value;
        }
        if (value == null) {
            mapLookup.remove(LongHash.toLong((int) msw, (int) lsw));
        } else {
            mapLookup.put(LongHash.toLong((int) msw, (int) lsw), value);
        }
    }

    public void remove(long msw, long lsw) {
        put(msw, lsw, null);
    }

    public void remove(long key) {
        remove(LongHash.msw(key), LongHash.lsw(key));
    }

    public boolean containsKey(long key) {
        return containsKey(LongHash.msw(key), LongHash.lsw(key));
    }

    public boolean containsKey(long msw, long lsw) {
        return get(msw, lsw) != null;
    }

    public Collection<E> values() {
        return mapLookup.values();
    }
}
