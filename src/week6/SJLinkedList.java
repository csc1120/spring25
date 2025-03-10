/*
 * Course: CSC1120
 * LinkedList Implementation
 *
 */
package week6;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A Simple Linked List Implementation
 * @param <E> the type of element stored in the List
 */
public class SJLinkedList<E> implements List<E> {
    private static class Node<E> {
        private E element;
        private Node<E> next;

        private Node(E element) {
            this(element, null);
        }

        private Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    private static class SJSubList<E> extends SJLinkedList<E> {
        private final SJLinkedList<E> list;
        private final int offset;
        private int size;

        private SJSubList(SJLinkedList<E> list, int fromIndex, int toIndex) {
            this.list = list;
            this.offset = fromIndex;
            this.size = toIndex - fromIndex;
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public E get(int index) {
            this.validateIndex(index);
            return list.get(offset + index);
        }

        @Override
        public E set(int index, E element) {
            this.validateIndex(index);
            return list.set(offset + index, element);
        }

        @Override
        public void add(int index, E element) {
            this.validateIndex(index);
            list.add(offset + index, element);
        }

        @Override
        public E remove(int index) {
            this.validateIndex(index);
            E result = list.remove(offset + index);
            --this.size;
            return result;
        }

        private void validateIndex(int index) {
            if(index < 0 || index >= this.size) {
                throw new IndexOutOfBoundsException("Index " + index +
                        " is invalid for list of size " + this.size);
            }
        }
    }

    private class SJIterator implements Iterator<E> {
        private Node<E> next;
        private Node<E> lastReturned;

        private SJIterator() {
            this.next = head;
            this.lastReturned = null;
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public E next() {
            if(this.next == null) {
                throw new NoSuchElementException();
            }
            E result = this.next.element;
            this.lastReturned = this.next;
            this.next = this.next.next;
            return result;
        }

        @Override
        public void remove() {
            if(this.lastReturned == null) {
                throw new IllegalStateException();
            }
            SJLinkedList.this.remove(lastReturned.element);
            lastReturned = null;
        }
    }

    private Node<E> head;
    private int size;

    /**
     * No-param constructor
     */
    public SJLinkedList() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public int size() { // O(1)
        return this.size;
    }

    @Override
    public boolean isEmpty() { // O(1)
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) { // O(n)
        Node<E> current = head;
        while(current != null) {
            if(current.element.equals(o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new SJIterator();
    }

    @Override
    public Object[] toArray() { // O(n)
        Object[] result = new Object[this.size];
        Node<E> current = this.head;
        for(int i = 0; i < this.size; ++i) {
            result[i] = current.element;
            current = current.next;
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) { // O(n)
        if(a.length < this.size) {
            a = (T[]) new Object[this.size];
        }
        Node<E> current = this.head;
        for(int i = 0; i < this.size; ++i) {
            a[i] = (T) current.element;
        }
        if(a.length > this.size) {
            a[this.size] = null;
        }
        return a;
    }

    /**
     * Returns a Node stored at a given index
     * @param index the index of the Node to find
     * @return the Node at the given index
     */
    private Node<E> findNode(int index) { // O(n)
        validateIndex(index);
        Node<E> current = head;
        for(int i = 0; i < index; ++i) {
            current = current.next;
        }
        return current;
    }

    /**
     * Validates that the index is valid
     * @param index the index to validate
     * @throws IndexOutOfBoundsException if index is not valid
     */
    private void validateIndex(int index) {
        if(index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Invalid index " + index + " for size "
                    + this.size);
        }
    }

    @Override
    public boolean add(E e) { // O(n)
        Node<E> newNode = new Node<>(e);
        if(this.head == null) {
            this.head = newNode;
        } else {
            Node<E> current = findNode(this.size - 1);
            current.next = newNode;
        }
        ++this.size;
        return true;
    }

    @Override
    public boolean remove(Object o) { // O(n)
        Node<E> prev = null;
        Node<E> current = this.head;
        for(int i = 0; i < this.size; ++i) {
            if(current.element.equals(o)) {
                if(prev == null) {
                    this.head = this.head.next;
                } else {
                    prev.next = current.next;
                }
                --this.size;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for(Object o : c) {
            if(!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for(E e : c) {
            this.add(e);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if(index != this.size) {
            validateIndex(index);
        }
        for(E e : c) {
            this.add(index++, e);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        int size = this.size;
        for(Object o : c) {
            this.remove(o);
        }
        return size != this.size;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int size = this.size;
        Node<E> current = this.head;
        while(current != null) {
            if(!c.contains(current.element)) {
                this.remove(current.element);
            }
            current = current.next;
        }
        return size != this.size;
    }

    @Override
    public void clear() { // O(1)
        this.head = null;
        this.size = 0;
    }

    @Override
    public E get(int index) { // O(n)
        validateIndex(index);
        Node<E> current = this.head;
        for (int i = 0; i < this.size; i++) {
            current = current.next;
        }
        return current.element;
    }

    public E getR(int index) { // O(n)
        validateIndex(index);
        return getR(index, this.head);
    }

    private E getR(int stepsLeft, Node<E> current) {
        if (stepsLeft == 0) {
            return current.element;
        }
        return getR(stepsLeft - 1, current.next);
    }

    @Override
    public E set(int index, E element) { // O(n)
        validateIndex(index);
        Node<E> current = this.head;
        for (int i = 0; i < this.size; i++) {
            current = current.next;
        }
        E old = current.element;
        current.element = element;
        return old;
    }

    @Override
    public void add(int index, E element) { // O(n)
        if(index != this.size) {
            validateIndex(index);
        }
        if(index > 0 && this.size > 0) {
            Node<E> prev = findNode(index - 1);
            prev.next = new Node<>(element, prev.next);
        } else {
            this.head = new Node<>(element, this.head);
        }
        ++this.size;
    }

    @Override
    public E remove(int index) { // O(n)
        validateIndex(index);
        E result;
        if(index > 0) {
            Node<E> prev = findNode(index - 1);
            result = prev.next.element;
            prev.next = prev.next.next;
        } else {
            result = this.head.element;
            this.head = this.head.next;
        }
        --this.size;
        return result;
    }

    @Override
    public int indexOf(Object o) { // O(n)
        if(this.head != null) {
            Node<E> current = this.head;
            for (int i = 0; i < this.size; ++i) {
                if(current.equals(o)) {
                    return i;
                }
                current = current.next;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) { // O(n)
        int index = -1;
        if(this.head != null) {
            Node<E> current = this.head;
            for(int i = 0; i < this.size; ++i) {
                if(current.equals(o)) {
                    index = i;
                }
            }
        }
        return index;
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
        return new SJSubList<>(this, fromIndex, toIndex);
    }
}
