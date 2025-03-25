/*
 * Course: CSC-1120
 * Search Tree Interface
 */
package week9;

import java.util.List;

/**
 * An interface for all search trees. Contains the basic methods needed for
 * a search tree.
 * @param <E> the element type stored in the tree
 */
public interface SearchTree<E extends Comparable<E>> {
    /**
     * Adds an element to the tree if the element does not already exist in the tree
     * @param item the element to add to the tree
     * @return true if the element is added, false otherwise
     */
    boolean add(E item);

    /**
     * Checks if a given item exists in the tree
     * @param item the target item
     * @return true if the item exists in the tree, false otherwise
     */
    boolean contains(E item);

    /**
     * Checks if a given item exists in the tree and returns the item
     * @param target the target item
     * @return the target item, or null if the item does not exist in the tree
     */
    E find(E target);

    /**
     * Removes the target item from the tree and returns it
     * @param target the item to remove from the tree
     * @return the item removed from the tree or null if the item does not exist in the tree
     */
    E delete(E target);

    /**
     * Removes the target item from the tree and returns it
     * @param target the item to remove from the tree
     * @return true if the item was removed from the tree
     */
    boolean remove(E target);

    /**
     * Clears the tree
     */
    void clear();

    /**
     * Returns all the elements in the tree as a List
     * @return a List containing all the elements stored in the tree
     */
    List<E> toList();
}
