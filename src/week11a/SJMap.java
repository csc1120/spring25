/*
 * Course: CSC-1120
 * SJMap
 */
package week11a;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A simple Map implementation
 * @param <K> the type of the key
 * @param <V> the type of the value
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
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }
    }
    private final List<Entry<K, V>> data;

    /**
     * A no-parameter constructor
     */
    public SJMap() {
        this.data = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.data.size();
    }

    @Override
    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) { // O(n)
        if(key != null) {
            for(Entry<K, V> e : this.data) {
                if(key.equals(e.key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) { // O(n)
        if(value != null) {
            for(Entry<K, V> e : this.data) {
                if(value.equals(e.value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) { // O(n)
        if(key != null) {
            for(Entry<K, V> e : this.data) {
                if(key.equals(e.key)) {
                    return e.value;
                }
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) { // O(n)
        if(key != null) {
            if (!containsKey(key)) {
                this.data.add(new Entry<>(key, value));
                return value;
            }
        }
        return null;
    }

    @Override
    public V remove(Object key) { // O(n)
        if(key != null) {
            for(Entry<K, V> e : this.data) {
                if(key.equals(e.key)) {
                    this.data.remove(e);
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
