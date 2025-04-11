/*
 * Course: CSC-1120
 * SJHashMapChained
 */
package week11a;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A simple Chained HashMap implementation
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
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

    private static final int INITIAL_CAPACITY = 11;
    private static final double LOAD_FACTOR_THRESHOLD = 3.0;
    private List<Entry<K, V>>[] data;
    private int numKeys;

    /**
     * No-parameter constructor
     */
    public SJHashMapChained() {
        this.data = (List<Entry<K, V>>[]) new List[INITIAL_CAPACITY];
        this.numKeys = 0;
    }

    @Override
    public int size() {
        return this.numKeys;
    }

    @Override
    public boolean isEmpty() {
        return this.numKeys == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        if (key != null) {
            int index = findKey(key);
            if (data[index] != null) {
                for (Entry<K, V> e : data[index]) {
                    if (key.equals(e.key)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        if (value != null) {
            for (List<Entry<K, V>> list : this.data) {
                if (list != null) {
                    for (Entry<K, V> e : list) {
                        if (value.equals(e.value)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        if (key != null) {
            int index = findKey(key);
            if (data[index] != null) {
                for (Entry<K, V> e : data[index]) {
                    if (key.equals(e.key)) {
                        return e.value;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (key != null) {
            int index = findKey(key);
            if (data[index] == null) {
                data[index] = new LinkedList<>();
            }
            // does key already exits?
            for (Entry<K, V> e : data[index]) {
                if (key.equals(e.key)) {
                    return null;
                }
            }
            data[index].add(new Entry<>(key, value));
            ++this.numKeys;
            if ((double) this.numKeys / this.data.length >= LOAD_FACTOR_THRESHOLD) {
                rehash();
            }
        }
        return value;
    }

    @Override
    public V remove(Object key) {
        if (key != null) {
            int index = findKey(key);
            if (data[index] != null) {
                for (Entry<K, V> e : data[index]) {
                    if (key.equals(e.key)) {
                        data[index].remove(e);
                        --this.numKeys;
                        return e.value;
                    }
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
        this.data = (List<Entry<K, V>>[]) new List[INITIAL_CAPACITY];
        this.numKeys = 0;
    }

    @Override
    public Set<K> keySet() {
        List<Entry<K, V>> list = new LinkedList<>();
        for(List<Entry<K, V>> l : this.data) {
            if(l != null) {
                list.addAll(l);
            }
        }
        return list.stream()
                .map(e -> e.key)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<V> values() {
        List<Entry<K, V>> list = new LinkedList<>();
        for(List<Entry<K, V>> l : this.data) {
            if(l != null) {
                list.addAll(l);
            }
        }
        return list.stream()
                .map(e -> e.value)
                .toList();
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        List<Entry<K, V>> list = new LinkedList<>();
        for(List<Entry<K, V>> l : this.data) {
            if(l != null) {
                list.addAll(l);
            }
        }
        return new HashSet<>(list);
    }

    private int findKey(Object key) {
        int index = key.hashCode() % data.length;
        if (index < 0) {
            index += data.length;
        }
        return index;
    }

    private void rehash() {
        List<Entry<K, V>>[] bigger = (List<Entry<K, V>>[]) new List[this.data.length * 2 + 1];
        List<Entry<K, V>>[] old = this.data;
        this.data = bigger;
        this.numKeys = 0;
        for (List<Entry<K, V>> list : old) {
            if (list != null) {
                for (Entry<K, V> e : list) {
                    put(e.key, e.value);
                }
            }
        }
    }
}
