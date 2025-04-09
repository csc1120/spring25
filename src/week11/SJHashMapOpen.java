/*
 * Course: CSC-1120
 * Open Addressing HashMap Implementation
 */
package week11;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Simple HashMap using Open Addressing
 * @param <K> the type of Key
 * @param <V> the type of Value
 */
public class SJHashMapOpen<K, V> implements Map<K, V> {
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
    private static final double LOAD_FACTOR_THRESHOLD = 0.8;
    private final Entry<K, V> deleted = new Entry<>(null, null);
    private Entry<K, V>[] entries;
    private int numKeys;

    /**
     * No-param constructor
     */
    public SJHashMapOpen() {
        numKeys = 0;
        entries = (Entry<K, V>[]) new Entry[INITIAL_CAPACITY];
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
        // get index
        int index = findIndex(key);
        // linear probing?
        K k = findKey(index, key);
        // does k exist?
        return k == null;
    }

    @Override
    public boolean containsValue(Object value) {
        if(value != null) {
            for (Entry<K, V> e : entries) {
                if (value.equals(e.value)) {
                    return true;
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
        return null;
    }

    @Override
    public V remove(Object key) {
        // homework
        // remember to replace the entry with the deleted entry and
        // keep track of the number of deleted keys with an instance
        // variable. This will also change how you calculate the
        // load factor.
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        entries = (Entry<K, V>[]) new Entry[INITIAL_CAPACITY];
        numKeys = 0;
    }

    @Override
    public Set<K> keySet() {
        return Arrays.stream(entries)
                .map(e -> e.key)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<V> values() {
        return Arrays.stream(entries)
                .map(e -> e.value)
                .toList();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new HashSet<>(Arrays.asList(entries));
    }

    private int findIndex(Object key) {
        int index = key.hashCode() % entries.length;
        if(index < 0) {
            index += entries.length;
        }
        return index;
    }

    private K findKey(int index, Object key) {
        while(entries[index] != null && !key.equals(entries[index].key)) {
            // linear probing
            ++index;
            // if at end?
            if(index == entries.length) {
                // go back to beginning
                index = 0;
            }
            // or: index %= entries.length;
        }
        return entries[index] == null ? null : entries[index].key;
    }
}
