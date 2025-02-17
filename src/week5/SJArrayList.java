/*
 * Course: CSC1120
 * SJArrayList
 */
package week5;

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
        if(this.size == this.data.length) {
            reallocate();
        }
        // add element and increment size
        data[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        boolean found = false;
        int index = -1;
        // Looks for the element in the backing array
        for(int i = 0; i < size && !found; ++i) {
            if(data[i].equals(o)) {
                // if it's found, remove it
                found = true;
                index = i;
            }
        }
        if(found) {
            // shifts all the elements to the left to close the gap made
            // by removing the element, overwriting the old element
            for(int i = index; i < size - 1; ++i) {
                data[i] = data[i + 1];
            }
            --size;
        }
        return found;
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
        this.size = 0;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        // check for bad index
        // if bad, throw exception
        validateIndex(index);
        // otherwise get the thing
        return data[index];

    }

    @Override
    public E set(int index, E element) {
        // validates the index
        validateIndex(index);
        // stores the old value
        E old = data[index];
        // sets the new values
        data[index] = element;
        // returns the old value
        return old;
    }

    @Override
    public void add(int index, E element) {
        // validates the index
        validateIndex(index);
        // verifies there is room to add an element
        if(size == data.length) {
            reallocate();
        }
        // shifts all the elements to the right to make room for the
        // element to insert at the given locations
        for(int i = size - 1; i >= index; --i) {
            data[i + 1] = data[i];
        }
        // inserts the new element at the given location
        data[index] = element;
        // increments the size
        ++size;
    }

    @Override
    public E remove(int index) {
        // validates the index
        validateIndex(index);
        // stores the value at the index
        E old = data[index];
        // shifts all the elements to the left to close the gap made
        // by removing the element, overwriting the old element
        for(int i = index; i < size - 1; ++i) {
            data[i] = data[i + 1];
        }
        // decrements the size
        --size;
        // returns the removed values
        return old;
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


    /**
     * Allocates a new backing array of size * 2 + 1 (based on Java's implementation),
     * copies the values into the new array in the same index locations.
     */
    private void reallocate() {
        E[] newData = (E[]) new Object[data.length * 2 + 1];
        for(int i = 0; i < data.length; ++i) {
            newData[i] = data[i];
        }
        // or System.arraycopy(data, 0, newData, 0, data.length);
        data = newData;
    }

    /**
     * Validates the given index as being within the bounds of the list
     * @param index the index passed into the method
     * @throws IndexOutOfBoundsException thrown if the index is negative or is greater than
     * the last valid index of the list
     */
    private void validateIndex(int index) throws IndexOutOfBoundsException {
        if(index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }
}