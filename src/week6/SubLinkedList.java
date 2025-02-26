package week6;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SubLinkedList<E> implements List<E> {
    private int size;
    private Node<E> head;
    private Node<E> tail;

    private static class Node<E> {
        E element;
        Node<E> next;

        Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        Node(E element) {
            this(element, null);
        }
    }

    public SubLinkedList() {
        size = 0;
        head = null;
        tail = null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return 0 == size();
    }

    @Override
    public boolean add(E e) {
        if (isEmpty()) {
            head = new Node(e);
            tail = head;
        } else {
            tail.next = new Node(e);
            tail = tail.next;
        }
        size++;
        return true;
    }

    @Override
    public boolean contains(Object o) {
        Node<E> walker = head;
        boolean targetNotFound = true;
        while(walker != null && targetNotFound){
            targetNotFound = !walker.element.equals(o);
            walker = walker.next;
        }
        return !targetNotFound;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        Node<E> walker = head;
        boolean targetNotFound = true;
        if(head.element.equals(o)){  // Objects.equals(o,head.element)
            if(head == tail){
                tail = null;
            }
            head = walker.next;
            targetNotFound = false;
        } else {
	    // <edit>
	    while(walker.next != null && targetNotFound){
                targetNotFound = !walker.next.element.equals(o);
                if(targetNotFound){
                    walker = walker.next;
                }
            }
	    // In the loop above, we test the value of targetNotFound
	    //   twice each time through the loop
	    // If we rearrange the code a little, though, we can only
	    //   test it once per iteration.  The key is that we want
	    //   to test the value after we assign it.  This means
	    //   putting the assignment before the loop test.
	    
	    // targetNotFound = !walker.next.element.equals(o);
	    // while(walker.next != null && targetNotFound){
	    // 	walker = walker.next;
	    // 	targetNotFound = !walker.next.element.equals(o);
            // }

	    // </edit>
	    
            if(walker.next == tail){
                tail = walker;
            }
            walker.next = walker.next.next;
        }
        if(!targetNotFound){
            --size;
        }
        return !targetNotFound;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        size = 0;
        head = null;
        tail = null;
    }

    @Override
    public E get(int index) {
        validateIndex(index);
        Node<E> walker = head;
        while (index > 0) {
            walker = walker.next;
            --index;
        }
        return walker.element;
    }

    private void validateIndex(int index) throws IndexOutOfBoundsException {
        if(index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
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
        return List.of();
    }
}
