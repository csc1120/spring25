/*
 * Course: CSC1120
 * Pure Stack Interface
 */
package week7a;

/**
 * A Pure Stack interface with only basic Stack methods
 * @param <E> the type of element stored in the stack
 */
public interface PureStack<E> {
    /**
     * Pushes an element onto the top of the stack and
     * returns the element pushed
     * @param e the element pushed on top of the stack
     * @return the element pushed on top of the stack
     */
    E push(E e);

    /**
     * Returns the element at teh top of the stack
     * @return the element at the top of the stack
     */
    E peek();

    /**
     * Removes and returns the element at the top of the stack
     * @return the element at the top of the stack
     */
    E pop();

    /**
     * Returns if the stack is empty or not
     * @return true if the stack is empty, false otherwise
     */
    boolean isEmpty();
}
