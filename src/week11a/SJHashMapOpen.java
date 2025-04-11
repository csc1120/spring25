/*
 * Course: CSC-1120
 * SJHashMapOpen
 */
package week11a;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private static final double LOAD_FACTOR_THRESHOLD = 0.8;
    private final Entry<K, V> deleted = new Entry<>(null, null);
    private Entry<K, V>[] data;
    private int numKeys;

    public SJHashMapOpen() {
        data = (Entry<K, V>[]) new Entry[INITIAL_CAPACITY];
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
        int index = findKey(key);
        return data[index] != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        int index = findKey(key);
        return data[index] == null ? null : data[index].value;
    }

    @Override
    public V put(K key, V value) {
        int index = findKey(key);
        if (data[index] != null) {
            return null;
        }
        this.data[index] = new Entry<>(key, value);
        ++numKeys;
        // check load factor threshold every time
        if((double)this.numKeys / this.data.length >= LOAD_FACTOR_THRESHOLD) {
            rehash();
        }
        return value;
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

    private int findKey(Object key) {
        int index = key.hashCode() % data.length;
        if (index < 0) {
            index += data.length;
        }
        while (data[index] != null && !key.equals(data[index].key)) {
            ++index; // linear probing
            index %= data.length; // wraparound
        }
        return index;
    }

    private void rehash() {
        Entry<K, V>[] bigger = (Entry<K, V>[]) new Entry[data.length * 2 + 1];
        Entry<K, V>[] old = this.data;
        this.data = bigger;
        // homework
        for(Entry<K, V> e : old) {
            if(e != null) {
                put(e.key, e.value);
            }
        }
    }
}
