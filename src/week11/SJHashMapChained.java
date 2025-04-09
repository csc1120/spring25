/*
 * Course: CSC-1120
 * Chained Addressing HashMap Implementation
 */
package week11;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple Chained addressing HashMap
 *
 * @param <K> the type of Key
 * @param <V> the type of Value
 */
public class SJHashMapChained<K, V> implements Map<K, V> {
    private static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }
    }
    private static final int INITIAL_CAPACITY = 11;
    private static final double LOAD_FACTOR_THRESHOLD = 3.0;
    private final List<Entry<K, V>>[] entries;
    private int numKeys;

    /**
     * No-param constructor
     */
    public SJHashMapChained() {
        this.entries = (List<Entry<K, V>>[]) new LinkedList[INITIAL_CAPACITY];
        numKeys = 0;
    }

    @Override
    public int size() {
        return numKeys;
    }

    @Override
    public boolean isEmpty() {
        return numKeys == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if(key != null) {
            int index = findIndex(key);
            for (Entry<K, V> e : entries[index]) {
                if(key.equals(e.key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        if(value != null) {
            for (List<Entry<K, V>> list : entries) {
                for(Entry<K, V> e : list) {
                    if(value.equals(e.value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        int index = findIndex(key);
        if(entries[index] == null) {
            entries[index] = new LinkedList<>();
        }
        entries[index].addFirst(new Entry<>(key, value));
        ++numKeys;
        // do we rehash
        if((double) numKeys / entries.length >= LOAD_FACTOR_THRESHOLD) {
            rehash();
        }
        return value;
    }

    private void rehash() {
        // do the same thing as open addressing
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public Collection<V> values() {
        return List.of();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return Set.of();
    }

    private int findIndex(Object key) {
        int index = key.hashCode() % entries.length;
        if(index < 0) {
            index += entries.length;
        }
        return index;
    }
}
