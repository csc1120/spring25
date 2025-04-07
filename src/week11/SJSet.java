/*
 * Course: CSC-1120
 * Set Implementation
 */
package week11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A simple Set implementation
 * @param <E> the type stored in the Set
 */
public class SJSet<E> implements Set<E> {
    private final List<E> data;

    /**
     * No-param constructor
     */
    public SJSet() {
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
    public boolean contains(Object o) {
        return data.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return data.iterator();
    }

    @Override
    public Object[] toArray() {
        return data.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return data.toArray(a);
    }

    @Override
    public boolean add(E e) {
        if(!data.contains(e)) {
            return data.add(e);
        }
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return data.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return new HashSet<>(data).containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return data.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return data.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return data.removeAll(c);
    }

    @Override
    public void clear() {
        data.clear();
    }
}
