/*
 * Course: CSC1120
 * Circular Queue Implementation
 */
package week7a;

import java.util.NoSuchElementException;

/**
 * Circular Queue implementation
 * @param <E> the element type stored in the queue
 */
public class CircularQueue<E> implements PureQueue<E> {
    private static final int CAPACITY = 10;
    private final E[] data;
    private int front;
    private int back;
    private int size;

    /**
     * No-param constructor for the Circular Queue
     */
    public CircularQueue() {
        this.data = (E[]) new Object[CAPACITY];
        this.front = 0;
        this.back = 0;
        this.size = 0;
    }

    @Override
    public boolean offer(E e) {
        if(this.size == CAPACITY) {
            return false;
        }
        this.data[back++] = e;
        back %= CAPACITY;
        ++this.size;
        return true;
    }

    @Override
    public boolean add(E e) throws IllegalStateException {
        if(this.size == CAPACITY) {
            throw new IllegalStateException();
        }
        return this.offer(e);
    }

    @Override
    public E poll() {
        if(isEmpty()) {
            return null;
        }
        E result = this.data[front++];
        front %= CAPACITY;
        --this.size;
        return result;
    }

    @Override
    public E remove() throws NoSuchElementException {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.poll();
    }

    @Override
    public E peek() {
        if(isEmpty()) {
            return null;
        }
        return this.data[front];
    }

    @Override
    public E element() throws NoSuchElementException {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.peek();
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
}
