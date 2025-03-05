package week7;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class SJQueue<E> implements PureQueue<E> {
    private LinkedList<E> data;

    public SJQueue() {
        this.data = new LinkedList<>();
    }

    @Override
    public boolean offer(E element) {
        return data.offer(element);
    }

    @Override
    public boolean add(E element) throws IllegalStateException {
        return data.offer(element);
    }

    @Override
    public E poll() {
        if(isEmpty()) {
            return null;
        }
        return data.poll();
    }

    @Override
    public E remove() throws NoSuchElementException {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        return data.poll();
    }

    @Override
    public E peek() {
        if(isEmpty()) {
            return null;
        }
        return data.peek();
    }

    @Override
    public E element() throws NoSuchElementException {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        return data.peek();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }
}
