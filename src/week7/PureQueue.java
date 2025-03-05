package week7;

import java.util.NoSuchElementException;

public interface PureQueue<E> {
    boolean offer(E element);
    boolean add(E element) throws IllegalStateException;
    E poll();
    E remove() throws NoSuchElementException;
    E peek();
    E element() throws NoSuchElementException;
    boolean isEmpty();
}
