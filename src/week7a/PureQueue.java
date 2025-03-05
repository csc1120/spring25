/*
 * Course: CSC1120
 * Pure Queue Interface
 */
package week7a;

import java.util.NoSuchElementException;

/**
 * A simple Queue interface with only basic queue methods
 * @param <E> the type of element stored in the queue
 */
public interface PureQueue<E> {
    /**
     * Adds an element at the back of the queue
     * @param e the element to add at the back of the queue
     * @return true if the element was added, false otherwise
     */
    boolean offer(E e);

    /**
     * Adds an element at the back of the queue
     * @param e the element to add at the back of the queue
     * @return true if the element was added, false otherwise
     * @throws IllegalStateException if the element cannot be added to the queue
     */
    boolean add(E e) throws IllegalStateException;

    /**
     * Removes and returns the element at the head of the queue
     * @return the head of the queue, or null if the queue is empty
     */
    E poll();

    /**
     * Removes and returns the element at the head of the queue
     * @return the head of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    E remove() throws NoSuchElementException;

    /**
     * Returns the element at the head of the queue
     * @return the head of the queue, or null if the queue is empty
     */
    E peek();

    /**
     * Returns the element at the head of the queue
     * @return the head of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    E element() throws NoSuchElementException;

    /**
     * Returns true if the queue is empty
     * @return true if the queue is empty, false otherwise
     */
    boolean isEmpty();
}
