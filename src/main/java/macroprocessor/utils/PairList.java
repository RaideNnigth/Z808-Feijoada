package macroprocessor.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class PairList<K, V> {
    private final HashMap<K, V> mapa;

    public PairList() {
        mapa = new HashMap<>();
    }

    public void add(K first, V second) {
        mapa.put(first, second);
    }

    public boolean containsKey(K key) {
        return mapa.containsKey(key);
    }

    public void removeAllKeysWithValue(V value) {
        mapa.values().removeAll(Collections.singleton(value));
    }
}
