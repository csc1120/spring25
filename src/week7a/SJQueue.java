/*
 * Course: CSC1120
 * Simple Queue implementation
 */
package week7a;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A simple Queue implementation without all the Collection methods
 * @param <E> the element type stored in the queue
 */
public class SJQueue<E> implements PureQueue<E> {
    private final LinkedList<E> queue;

    /**
     * No-param constructor for the queue
     */
    public SJQueue() {
        queue = new LinkedList<>();
    }

    @Override
    public boolean offer(E e) {
        return queue.offer(e);
    }

    @Override
    public boolean add(E e) throws IllegalStateException {
        return queue.add(e);
    }

    @Override
    public E poll() {
        return queue.isEmpty() ? null : queue.poll();
    }

    @Override
    public E remove() throws NoSuchElementException {
        if(queue.isEmpty()) {
            throw new NoSuchElementException();
        }
        return queue.poll();
    }

    @Override
    public E peek() {
        return queue.isEmpty() ? null : queue.peek();
    }

    @Override
    public E element() throws NoSuchElementException {
        if(queue.isEmpty()) {
            throw new NoSuchElementException();
        }
        return queue.peek();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
