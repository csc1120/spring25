/*
 * Course: CSC-1120
 * Map Implementation
 */
package week11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Simple Map implementation. No hash functions or ordering
 * @param <K> the type of Key
 * @param <V> the type of Value
 */
public class SJMap<K, V> implements Map<K, V> {
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

    private final ArrayList<Entry<K, V>> data;

    /**
     * No-param constructor that initializes the backing list
     */
    public SJMap() {
        data = new ArrayList<>();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        if(key != null) {
            for (Entry<K, V> e : data) {
                if (key.equals(e.key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        if(value != null) {
            for (Entry<K, V> e : data) {
                if (value.equals(e.value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        if(key != null) {
            for (Entry<K, V> e : data) {
                if (e.key.equals(key)) {
                    return e.value;
                }
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        // does the key exist?
        if(!containsKey(key)) {
            data.add(new Entry<>(key, value));
            return value;
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        if(key != null) {
            for (Entry<K, V> e : data) {
                if (e.key.equals(key)) {
                    data.remove(e);
                    return e.value;
                }
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public Set<K> keySet() {
        return data.stream()
                .map(e -> e.key)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<V> values() {
        return data.stream()
                .map(e -> e.value)
                .toList();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new HashSet<>(data);
    }
}
