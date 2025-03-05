/*
 * Course: CSC1120
 * Simple Stack Implementation
 */
package week7a;

import java.util.LinkedList;

/**
 * A simple implementation of a Stack using a PureStack interface
 * @param <E>
 */
public class SJStack<E> implements PureStack<E> {
    private final LinkedList<E> stack;

    /**
     * No-param constructor for the SJStack
     */
    public SJStack() {
        stack = new LinkedList<>();
    }

    @Override
    public E push(E e) {
        stack.push(e);
        return e;
    }

    @Override
    public E peek() {
        return stack.peek();
    }

    @Override
    public E pop() {
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
