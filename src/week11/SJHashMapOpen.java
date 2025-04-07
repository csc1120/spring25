package week11;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SJHashMapOpen<K, V> implements Map<K, V> {
    private static class Entry<K, V> implements Map.Entry<K, V> {
        private final K key;
        private V value;

        public Entry(K key, V value) {
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
    private static final double LOAD_FACTOR = 0.8;
    private final Entry<K, V> deleted = new Entry<>(null, null);
    private Entry<K, V>[] entries;
    private int numKeys;

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
        int index = findIndex(key);
        // linear probing?
        K k = findKey(index, key);
        return k == null;
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
            // index %= entries.length;
        }
        return entries[index].key;
    }

    @Override
    public boolean containsValue(Object value) {
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
}
