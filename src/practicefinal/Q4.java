/*
 * Course: CSC-1120
 * LinkedList question
 */
package practicefinal;

import week6.SJLinkedList;

/**
 * removeAll for a single linked list
 * @param <E> the type stored in the list
 */
public class Q4<E> extends SJLinkedList<E> {
    /**
     * Removes all instances of an element from the list
     * @param target the element(s) to remove from the list
     * @return the number of elements removed from the list
     */
    public int removeAll(E target) {
        int count = 0;
        for(E e : this) {
            if(e.equals(target)) {
                remove(e);
                ++count;
            }
        }
        return count;
    }
}
