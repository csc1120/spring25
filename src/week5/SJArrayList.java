/*
 * Course: CSC-1120
 * ArrayList implementation
 */
package week5;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A simple ArrayList implementation
 *
 * @param <E> the element type stored in the list
 */
public class SJArrayList<E> implements List<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private int size;
    private E[] data;

    /**
     * A no-param constructor that sets the backing array to a default starting length
     */
    public SJArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * A constructor that sets the initial capacity of the backing array
     *
     * @param initialCapacity the initial capacity of the backing array
     * @throws NegativeArraySizeException thrown if the capacity is less than 0
     */
    @SuppressWarnings("unchecked")
    public SJArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new NegativeArraySizeException();
        }

        this.size = 0;
        data = (E[]) new Object[initialCapacity];
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
        for (int i = 0; i < this.size; ++i) {
            if (Objects.equals(data[i], o)) {
                return true;
            }
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
        System.arraycopy(this.data, 0, result, 0, this.size);
        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < this.size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), this.size);
        }

        int index = 0;
        for (E element : this) {
            a[index++] = (T) element;
        }

        if (a.length > this.size) {
            a[this.size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E e) { // O(1)
        if (this.size == this.data.length) {
            reallocate(); // O(n), but amortized across all calls to add, so O(1)
        }
        data[size++] = e;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < this.size; ++i) {
            if (Objects.equals(this.data[i], o)) {
                for (int j = i; j < this.size - 1; ++j) {
                    this.data[j] = this.data[j + 1];
                }
                --this.size;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        for (E e : c) {
            this.add(e);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        validateIndex(index);
        for (E e : c) {
            this.add(index++, e);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object o : c) {
            if (this.contains(o)) {
                changed = true;
                this.remove(o);
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        int i = 0;
        while (i < this.size) {
            if (!c.contains(this.data[i])) {
                this.remove(i);
                changed = true;
            } else {
                i++;
            }
        }
        return changed;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        this.data = (E[]) Array.newInstance(this.data.getClass().getComponentType(),
                DEFAULT_CAPACITY);
        this.size = 0;
    }

    @Override
    public E get(int index) {
        validateIndex(index);
        return this.data[index];
    }

    @Override
    public E set(int index, E element) {
        validateIndex(index);
        E oldValue = this.data[index];
        this.data[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index " + index + " for a list of size "
                    + this.size + " is invalid.");
        }
        if (this.data.length == this.size) {
            reallocate();
        }
        for (int i = this.size - 1; i >= index; --i) {
            this.data[i + 1] = this.data[i];
        }
        this.data[index] = element;
        ++this.size;
    }

    @Override
    public E remove(int index) {
        validateIndex(index);
        E returnValue = this.data[index];
        for (int i = index; i < this.size - 1; ++i) {
            this.data[i] = this.data[i + 1];
        }
        --this.size;
        return returnValue;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < this.size; ++i) {
            if (this.data[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = this.size - 1; i >= 0; --i) {
            if (this.data[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return new SJListIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new SJListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new SubList(fromIndex, toIndex);
    }

    @SuppressWarnings("unchecked")
    private void reallocate() {
        E[] temp = (E[]) Array.newInstance(this.data.getClass().getComponentType(),
                this.size * 2);
        System.arraycopy(this.data, 0, temp, 0, this.size);
        this.data = temp;
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index " + index + " for a list of size "
                    + this.size + " is invalid.");
        }
    }

    private class SJIterator implements Iterator<E> {
        private int lastReturned;
        private int next;

        private SJIterator() {
            this.lastReturned = -1;
            this.next = 0;
        }

        @Override
        public boolean hasNext() {
            return this.next < SJArrayList.this.size;
        }

        @Override
        public E next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastReturned = this.next;
            ++this.next;
            return SJArrayList.this.data[this.lastReturned];
        }

        @Override
        public void remove() {
            if (this.lastReturned < 0) {
                throw new IllegalStateException();
            }
            SJArrayList.this.remove(this.lastReturned);
            --this.next;
            this.lastReturned = -1;
        }
    }

    private class SJListIterator implements ListIterator<E> {
        private int lastReturned;
        private int next;

        private SJListIterator() {
            this(0);
        }

        private SJListIterator(int index) {
            if (index < 0 || index > SJArrayList.this.size) {
                throw new IndexOutOfBoundsException("Index " + index + " for a list of size "
                        + SJArrayList.this.size + " is invalid.");
            }
            this.lastReturned = -1;
            this.next = index;
        }

        @Override
        public boolean hasNext() {
            return this.next < SJArrayList.this.size;
        }

        @Override
        public E next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.lastReturned = this.next;
            ++this.next;
            return SJArrayList.this.data[this.lastReturned];
        }

        @Override
        public boolean hasPrevious() {
            return this.next - 1 >= 0;
        }

        @Override
        public E previous() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.lastReturned = this.next - 1;
            --this.next;
            return SJArrayList.this.data[this.lastReturned];
        }

        @Override
        public int nextIndex() {
            return this.next;
        }

        @Override
        public int previousIndex() {
            return this.next - 1;
        }

        @Override
        public void remove() {
            if (this.lastReturned < 0) {
                throw new IllegalStateException();
            }
            SJArrayList.this.remove(this.lastReturned);
            if (this.lastReturned < this.next) {
                --this.next;
            }
            this.lastReturned = -1;
        }

        @Override
        public void set(E e) {
            if (this.lastReturned < 0) {
                throw new IllegalStateException();
            }
            SJArrayList.this.set(this.lastReturned, e);
        }

        @Override
        public void add(E e) {
            SJArrayList.this.add(this.next++, e);
            this.lastReturned = -1;
        }
    }

    private class SubList implements List<E> {
        private class SubListIterator implements Iterator<E> {
            private final SJListIterator parentIterator;
            private int next;

            private SubListIterator() {
                this.parentIterator = new SJListIterator(SubList.this.fromIndex);
                this.next = SubList.this.fromIndex;
            }

            @Override
            public boolean hasNext() {
                return this.next < SubList.this.toIndex;
            }

            @Override
            public E next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                ++this.next;
                return parentIterator.next();
            }

            @Override
            public void remove() {
                parentIterator.remove();
                --this.next;
            }
        }

        private class SubListListIterator implements ListIterator<E> {
            private final SJListIterator parentIterator;
            private int next;

            private SubListListIterator() {
                this(SubList.this.fromIndex);
            }

            private SubListListIterator(int index) {
                if (index < SubList.this.fromIndex || index > SubList.this.toIndex) {
                    throw new IndexOutOfBoundsException();
                }
                parentIterator = new SJListIterator(index);
                this.next = index;
            }

            @Override
            public boolean hasNext() {
                return this.next < SubList.this.toIndex;
            }

            @Override
            public E next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                ++this.next;
                return parentIterator.next();
            }

            @Override
            public boolean hasPrevious() {
                return this.next > SubList.this.fromIndex;
            }

            @Override
            public E previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                --this.next;
                return parentIterator.previous();
            }

            @Override
            public int nextIndex() {
                return this.next;
            }

            @Override
            public int previousIndex() {
                return this.next - 1;
            }

            @Override
            public void remove() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                --this.next;
                parentIterator.remove();
            }

            @Override
            public void set(E e) {
                parentIterator.set(e);
            }

            @Override
            public void add(E e) {
                parentIterator.add(e);
                ++this.next;
            }
        }

        private final int fromIndex;
        private int toIndex;

        private SubList(int fromIndex, int toIndex) {
            if (fromIndex < 0 || fromIndex >= SJArrayList.this.size ||
                    toIndex < 0 || toIndex > SJArrayList.this.size) {
                throw new IndexOutOfBoundsException();
            }
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

        @Override
        public int size() {
            return this.toIndex - this.fromIndex;
        }

        @Override
        public boolean isEmpty() {
            return this.size() == 0;
        }

        @Override
        public boolean contains(Object o) {
            for (int i = this.fromIndex; i < this.toIndex; ++i) {
                if (SJArrayList.this.get(i).equals(o)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Iterator<E> iterator() {
            return new SubListIterator();
        }

        @Override
        public Object[] toArray() {
            Object[] result = new Object[this.size()];
            System.arraycopy(SJArrayList.this.data, this.fromIndex, result, 0, this.size());
            return result;
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            if (a.length < this.size()) {
                a = (T[]) Array.newInstance(a.getClass().getComponentType(), this.size());
            }

            int index = this.fromIndex;
            for (E element : this) {
                a[index++] = (T) element;
            }

            if (a.length > this.size()) {
                a[this.size()] = null;
            }

            return a;
        }

        @Override
        public boolean add(E e) {
            SJArrayList.this.add(this.toIndex++, e);
            return true;
        }

        @Override
        public boolean remove(Object o) {
            for (int i = this.fromIndex; i < this.toIndex; ++i) {
                if (SJArrayList.this.data[i].equals(o)) {
                    SJArrayList.this.remove(i);
                    --this.toIndex;
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            for (Object o : c) {
                if (!this.contains(o)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            for (E e : c) {
                this.add(e);
            }
            return true;
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            if (c.isEmpty()) {
                return false;
            }
            validateIndex(index);
            for (E e : c) {
                this.add(index++, e);
            }
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            boolean changed = false;
            for (Object o : c) {
                if (this.contains(o)) {
                    changed = true;
                    this.remove(o);
                }
            }
            return changed;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            boolean changed = false;
            for (E e : this) {
                if (!c.contains(e)) {
                    changed = true;
                    this.remove(e);
                }
            }
            return changed;
        }

        @Override
        public void clear() {
            int numItems = this.size();
            for (int i = 0; i < numItems; ++i) {
                SJArrayList.this.remove(this.fromIndex);
            }
            this.toIndex = this.fromIndex;
        }

        @Override
        public E get(int index) {
            validateIndex(index);
            return SJArrayList.this.data[this.fromIndex + index];
        }

        @Override
        public E set(int index, E element) {
            validateIndex(index);
            E oldValue = SJArrayList.this.data[this.fromIndex + index];
            SJArrayList.this.data[this.fromIndex + index] = element;
            return oldValue;
        }

        @Override
        public void add(int index, E element) {
            if (index < this.fromIndex || index > this.toIndex) {
                throw new IndexOutOfBoundsException();
            }
            SJArrayList.this.add(this.fromIndex + index, element);
            ++toIndex;
        }

        @Override
        public E remove(int index) {
            validateIndex(this.fromIndex + index);
            E returnValue = SJArrayList.this.remove(this.fromIndex + index);
            --this.toIndex;
            return returnValue;
        }

        @Override
        public int indexOf(Object o) {
            for (int i = this.fromIndex; i < this.toIndex; ++i) {
                if (SJArrayList.this.data[i].equals(o)) {
                    return i - this.fromIndex;
                }
            }
            return -1;
        }

        @Override
        public int lastIndexOf(Object o) {
            for (int i = this.toIndex - 1; i >= 0; --i) {
                if (SJArrayList.this.data[i].equals(o)) {
                    return i - this.fromIndex;
                }
            }
            return -1;
        }

        private void validateIndex(int index) {
            if (index < 0 || index >= this.size()) {
                throw new IndexOutOfBoundsException();
            }
        }

        public ListIterator<E> listIterator() {
            return new SubListListIterator();
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            return new SubListListIterator(this.fromIndex + index);
        }

        @Override
        public List<E> subList(int fromIndex, int toIndex) {
            return new SubList(this.fromIndex + fromIndex, this.fromIndex + toIndex);
        }
    }
}