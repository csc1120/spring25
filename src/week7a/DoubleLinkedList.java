/*
 * Course: CSC1120
 * Double Linked List Implementation
 *
 */

package week7a;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A Simple Double Linked List Implementation
 * @param <E> the type of element stored in the List
 */
public class DoubleLinkedList<E> implements List<E> {
    private static class Node<E> {
        private E element;
        private Node<E> next;
        private Node<E> prev;

        private Node(E element) {
            this(element, null, null);
        }

        private Node(E element, Node<E> next) {
            this(element, next, null);
        }

        private Node(E element, Node<E> next, Node<E> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private static class SJSubList<E> extends DoubleLinkedList<E> {
        private final DoubleLinkedList<E> list;
        private final int offset;
        private int size;

        private SJSubList(DoubleLinkedList<E> list, int fromIndex, int toIndex) {
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
            DoubleLinkedList.this.remove(lastReturned.element);
            lastReturned = null;
        }
    }

    private class SJListIterator implements ListIterator<E> {
        private Node<E> lastReturned;
        private Node<E> next;
        private int nextIndex;

        private SJListIterator(int index) {
            validateIndex(index);
            this.next = (index == size) ? null : findNode(index);
            this.nextIndex = index;
            this.lastReturned = null;
        }

        @Override
        public boolean hasNext() {
            return this.nextIndex < size;
        }

        @Override
        public E next() {
            if(!hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastReturned = this.next;
            this.next = this.next.next;
            ++nextIndex;
            return lastReturned.element;
        }

        @Override
        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        @Override
        public E previous() {
            if(!hasPrevious()) {
                throw new NoSuchElementException();
            }
            // if we are at the end of the list, then going backwards
            // will make the next node the tail
            this.lastReturned = (this.next == null) ? tail : this.next.prev;
            // The node we have just bounced over is both the last node seen and the
            // next node in the list
            this.next = this.lastReturned;
            // we have gone backwards one index
            --nextIndex;
            return lastReturned.element;
        }

        @Override
        public int nextIndex() {
            return this.nextIndex;
        }

        @Override
        public int previousIndex() {
            return this.nextIndex - 1;
        }

        @Override
        public void remove() {
            if(lastReturned == null) {
                throw new IllegalStateException();
            }
            // Remove the node from the list
            Node<E> lastNext = this.lastReturned.next;
            Node<E> lastPrev = this.lastReturned.prev;
            // if there is no previous, we are removing the head
            if(lastPrev == null) {
                head = lastNext;
            } else {
                lastPrev.next = lastNext;
            }
            // If there is no next, we are removing the tail
            if(lastNext == null) {
                tail = lastPrev;
            }
            // If we called previous before remove, next and lastReturned are the same
            if(this.next == this.lastReturned) {
                this.next = lastNext;
            } else {
                --nextIndex;
            }
            // lastReturned node now no longer exists
            this.lastReturned = null;
        }



        @Override
        public void set(E e) {
            if(lastReturned == null) {
                throw new IllegalStateException();
            }
            this.lastReturned.element = e;
        }

        @Override
        public void add(E e) {
            this.lastReturned = null;
            // if next ==  null, adding at the end of the list
            if(this.next == null) {
                DoubleLinkedList.this.add(e);
            } else {
                DoubleLinkedList.this.add(nextIndex++, e);
            }
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size;

    /**
     * No-param constructor
     */
    public DoubleLinkedList() {
        this.head = null;
        this.tail = null;
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
        return new SJIterator();
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
        Node<E> newNode = new Node<>(e, null, tail);
        if(this.head == null) {
            this.head = newNode;
            this.tail = this.head;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        ++this.size;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node<E> current = this.head;
        boolean found = false;
        while(current != null && !found) {
            if(current.element.equals(o)) {
                found = true;
            } else {
                current = current.next;
            }
        }
        if(found) {
            if(current.prev == null) {
                this.head = this.head.next;
                this.head.prev = null;
            } else {
                current.prev.next = current.next;
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
            Node<E> newNode = new Node<>(element, prev.next, prev);
            prev.next.prev = newNode;
            prev.next = newNode;
        } else {
            Node<E> newNode = new Node<>(element, this.head, null);
            this.head.prev = newNode;
            this.head = newNode;
        }
        ++this.size;
    }

    @Override
    public E remove(int index) {
        validateIndex(index);
        Node<E> current = findNode(index);
        if(current == this.tail) {
            current.prev.next = null;
            this.tail = current.prev;
        } else if(current == this.head) {
            current.next.prev = null;
            this.head = current.next;
        } else {
            current.next.prev = current.prev;
            current.prev.next = current.next;
        }
        --this.size;
        return current.element;
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
        if(this.tail != null) {
            Node<E> current = this.tail;
            for(int i = this.size - 1; i >= 0; --i) {
                if(current.equals(o)) {
                    return i;
                }
                current = current.prev;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new SJListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new SJListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new SJSubList<>(this, fromIndex, toIndex);
    }
}
