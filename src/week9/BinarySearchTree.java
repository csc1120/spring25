/*
 * Course: CSC-1120
 * Binary Search Tree
 */
package week9;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic Binary Search Tree implementation.
 * @param <E> the element stored in the tree
 */
public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E>
        implements SearchTree<E> {
    private boolean addResult; // did we add or not?
    private E deleteReturn;

    @Override
    public boolean add(E item) {
        this.root = add(item, this.root);
        return this.addResult;
    }

    /**
     * Add helper method. Because the parent node needs to be the place where we add
     * the new node (by pointing to the new node as a child of the parent) we need to
     * return the node, as that is the only way to share data between recursive method calls.
     * This means we must always return either the new node we created, or the node that we
     * visited that remains unchanged
     *
     * @param item the item to add to the tree
     * @param node the current node
     * @return the result of the add call, either a new node containing the item we are adding
     * or the current node, which remains unchanged.
     */
    private Node<E> add(E item, Node<E> node) {
        // base case
        if(node == null) {
            // found it. It goes here. Add it.
            this.addResult = true;
            // return the new Node to be added to the tree
            return new Node<>(item);
        }
        // Compare the item to the current node's data
        int compResult = item.compareTo(node.data);
        // item is "less than" node.data, go left
        if(compResult < 0) {
            node.left = add(item, node.left);
        // item is "greater than" node.data, go right
        } else if (compResult > 0) {
            node.right = add(item, node.right);
        } else {
            // they are equal, already exists, do not add
            this.addResult = false;
        }
        // Made no change to the current node, return it
        return node;
    }

    @Override
    public boolean contains(E item) {
        return find(item) != null; // if the item does not exist, find will return null
    }

    @Override
    public E find(E target) {
        return find(target, this.root);
    }

    /**
     * This is binary search. We compare the target to the data in each node, moving
     * left or right based on the comparison until we either find the target or we
     * reach a null node, meaning the target does not exist in the tree.
     * @param target the item we are looking for
     * @param node the current node
     * @return the target element or null if the target does not exist in the tree
     */
    private E find(E target, Node<E> node) {
        // didn't find it, doesn't exist, return null
        if(node == null) {
            return null;
        }
        // Compare the target to the current node's data
        int compResult = target.compareTo(node.data);
        // found it. Return the data
        if(compResult == 0) {
            return node.data;
        }
        // look for it
        // target "less than" data, go left
        if(compResult < 0) {
            return find(target, node.left);
        // target "greater than" data, go right
        } else {
            return find(target, node.right);
        }
    }

    @Override
    public E delete(E target) {
        // call helper method
        this.root = delete(target, this.root);
        return deleteReturn;
    }

    private Node<E> delete(E target, Node<E> node) {
        // node == null
        if(node == null) {
            // not in the tree
            deleteReturn = null;
        } else {
            // do something
            int compResult = target.compareTo(node.data);
            if(compResult < 0) {
                node.left = delete(target, node.left);
            } else if(compResult > 0) {
                node.right = delete(target, node.right);
            } else {
                // found it!!!
                deleteReturn = node.data; // save return value
                // delete the node
                if(node.left == null) {
                    // replace node being deleted with right child
                    return node.right;
                } else if(node.right == null) {
                    return node.left;
                } else {
                    // two children
                    // go to the left tree, find largest value
                    if(node.left.right == null) {
                        node.data = node.left.data;
                        node.left = node.left.left;
                    } else {
                        // left child DOES have a right child
                        // find the largest value of the left subtree
                        node.data = findLargestChild(node.left);
                    }
                }
            }
        }
        return node;
    }

    private E findLargestChild(Node<E> node) {
        E result = null;
        if(node.right.right == null) { // found largest node
            result = node.right.data;
            node.right = node.right.left;
        } else {
            // keep looking
            findLargestChild(node.right);
        }
        return result;
    }

    @Override
    public boolean remove(E target) {
        return delete(target) != null;
    }

    @Override
    public void clear() {
        // Like the LinkedList, a single point of entry to the structure
        // remove it, and the tree is empty
        this.root = null;
    }

    @Override
    public List<E> toList() {
        final List<E> result = new ArrayList<>();
        inOrderTraversal((e, _) -> {
            if(e != null) {
                result.add(e);
            }
        });
        return result;
    }


}
