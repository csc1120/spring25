/*
 * Course: CSC1120
 * SJArrayList
 */
package week4;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * A simplified ArrayList implementation using the java.util.List interface
 *
 * @param <E> the type stored in the List
 */
public class SJArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] data;
    private int size;

    /**
     * A no-parameter constructor
     */
    public SJArrayList() {
        this(DEFAULT_CAPACITY);
        // O(1)
    }

    /**
     * A constructor that is given an initial starting capacity.
     * This is useful if you know how large your List will end up being,
     * especially if it is  fairly large value, because it will remove
     * the need to resize the backing array numerous times
     *
     * @param initialCapacity the initial length of the backing array
     */
    public SJArrayList(int initialCapacity) {
        this.size = 0;
        data = (E[]) new Object[initialCapacity];
        // O(1)
    }

    @Override
    public int size() {
        return this.size;
        // O(1)
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
        // O(1)
    }

    @Override
    public boolean contains(Object o) {
        for(int i = 0; i < this.size; ++i) {
            if(data[i].equals(o)) {
                return true;
            }
        }
        return false;
        // O(n)
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(E e) {
        // ask are we full?
        // if yes, resize
        data[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
}