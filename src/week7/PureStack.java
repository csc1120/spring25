package week7;

public interface PureStack<E> {
    E push(E element);
    E pop();
    E peek();
    boolean isEmpty();
}
