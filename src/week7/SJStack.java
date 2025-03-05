package week7;

import java.util.EmptyStackException;
import java.util.LinkedList;

public class SJStack<E> implements PureStack<E> {
    // adaptor class
    private LinkedList<E> data;

    public SJStack() {
        this.data = new LinkedList<>();
    }

    @Override
    public E push(E element) {
        data.push(element);
        return element;
    }

    @Override
    public E pop() {
        if(this.isEmpty()) {
            throw new EmptyStackException();
        }
        return data.pop();
    }

    @Override
    public E peek() {
        if(this.isEmpty()) {
            throw new EmptyStackException();
        }
        return data.peek();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }
}
