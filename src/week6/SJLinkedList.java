/*
 * Course; CSC1120
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
    private class SJIterator implements Iterator<E> {
        private Node<E> next;
        private Node<E> lastSeen;

        private SJIterator() {
            this.next = head;
            this.lastSeen = null;
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public E next() {
            if(next == null) {
                throw new NoSuchElementException();
            }
            E result = this.next.element;
            this.lastSeen = this.next;
            this.next = this.next.next;
            return result;
        }

        @Override
        public void remove() {
            Iterator.super.remove();
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
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object o) {
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
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[this.size];
        Node<E> current = this.head;
        for(int i = 0; i < this.size; ++i) {
            result[i] = current.element;
            current = current.next;
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    private Node<E> findNode(int index) {
        validateIndex(index);
        Node<E> current = head;
        int i = 0;
        while(i < index) {
            current = current.next;
            ++i;
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
    public boolean add(E e) {
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
    public boolean remove(Object o) {
        Node<E> prev = null;
        Node<E> current = this.head;
        boolean found = false;
        while(current != null && !found) {
            if(current.element.equals(o)) {
                found = true;
            } else {
                current = current.next;
                prev = current;
            }
        }
        if(found) {
            if(prev == null) {
                this.head = this.head.next;
            } else {
                prev.next = current.next;
            }
            --this.size;
            return true;
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
    public void clear() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public E get(int index) {
        validateIndex(index);
        Node<E> current = this.head;
        for (int i = 0; i < this.size; i++) {
            current = current.next;
        }
        return current.element;
    }

    @Override
    public E set(int index, E element) {
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
    public void add(int index, E element) {
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
    public E remove(int index) {
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
    public int indexOf(Object o) {
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
    public int lastIndexOf(Object o) {
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
        throw new UnsupportedOperationException();
    }
}
