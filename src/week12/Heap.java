/*
 * Course: CSC-1120
 * Heap
 */
package week12;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A simple heap using a binary tree structure
 *
 * @param <E> the type of element stored in the heap
 */
public class Heap<E extends Comparable<E>> {
    private static class Node<E> {
        private Node<E> left;
        private Node<E> right;
        private Node<E> parent;
        private E data;

        private Node(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    private Node<E> root;
    private int size;

    /**
     * Heap constructor that adds all elements of a Collection
     *
     * @param c the Collection to add to the Heap
     */
    public Heap(Collection<E> c) {
        this.root = null;
        this.size = 0;
        for(E e : c) {
            add(e);
        }
    }

    /**
     * Adds an element to the heap. The element is added at the bottom level of the heap
     * at the first open location (the heap must remain a complete tree), then will
     * be moved up the tree, swapping places with its parent until the element is no
     * longer "less than" its parent
     *
     * @param element the element to add to the heap
     */
    public void add(E element) {
        Node<E> newNode = new Node<>(element);
        // if the heap is empty, make the new node the root
        if (this.root == null) {
            this.root = newNode;
        } else {
            // otherwise, find the parent
            Node<E> parent = findInsertionParent();
            // If there is no left child, make the new node the left child
            if (parent.left == null) {
                parent.left = newNode;
            } else {
                // otherwise, make it the right child
                parent.right = newNode;
            }
            // add the parent reference to the new node
            newNode.parent = parent;
            // put the node in the proper place in the heap
            heapifyUp(newNode);
        }
        ++this.size;
    }

    /**
     * Removes the root from the heap. The rightmost element in the lowest level
     * replaces the root and will be moved down until it is no longer "greater than"
     * one of its children
     *
     * @return the element removed
     */
    public E remove() {
        // if the heap is empty, return null
        if (this.root == null) {
            return null;
        }
        // store the root's data
        E result = this.root.data;
        // if the heap just has the root, remove it and be done
        if (this.size == 1) {
            this.root = null;
        } else {
            // find the last node in the complete tree (furthest right node at the lowest level)
            // and replace the root data with it
            Node<E> lastNode = findLastNode();
            this.root.data = lastNode.data;
            // remove the last node
            if (lastNode.parent.right == lastNode) {
                lastNode.parent.right = null;
            } else {
                lastNode.parent.left = null;
            }
            // put the node in the proper place
            heapifyDown(this.root);
        }
        --this.size;
        return result;
    }

    /**
     * Reports the size of the heap
     *
     * @return the size of the heap
     */
    public int size() {
        return this.size;
    }

    private void heapifyUp(Node<E> node) {
        // while the node isn't the root and its data is still less than its parent's
        // swap the node and its parent
        while (node.parent != null && node.data.compareTo(node.parent.data) < 0) {
            E temp = node.data;
            node.data = node.parent.data;
            node.parent.data = temp;
            node = node.parent;
        }
    }

    private void heapifyDown(Node<E> node) {
        boolean done = false;
        // while we have not hit the bottom layer and have not found the correct spot for the node
        while (node != null && !done) {
            // get the node with the smallest data value between the node and its children
            Node<E> smallest = getSmallest(node);
            // if one of the children's data is still less than the node's data, swap them
            if (smallest != node) {
                E temp = node.data;
                node.data = smallest.data;
                smallest.data = temp;
                node = smallest;
            } else {
                // otherwise, neither children have smaller data, we found the spot and are done
                done = true;
            }
        }
    }

    private Node<E> getSmallest(Node<E> node) {
        Node<E> smallest = node;
        // if the left child exists and the data is less than the node, set that as smallest
        if (node.left != null && node.left.data.compareTo(smallest.data) < 0) {
            smallest = node.left;
        }
        // if the right child exists and the data is less than the node, set that as smallest
        // This means the right will always be chosen if both nodes have smaller data
        if (node.right != null && node.right.data.compareTo(smallest.data) < 0) {
            smallest = node.right;
        }
        return smallest;
    }

    /**
     * Finds the next available parent for a new node. This is done by starting with the root and
     * for each node, checking if it has two children. If not, that is the parent for the new node.
     * Otherwise, it will add both children to the queue, ensuring the nodes are visited top down,
     * left to right until an open spot
     *
     * @return the parent of the new node
     */
    private Node<E> findInsertionParent() {
        Queue<Node<E>> queue = new LinkedList<>();
        Node<E> node = null;
        queue.offer(this.root);
        boolean done = false;
        while (!queue.isEmpty() && !done) {
            node = queue.poll();
            if (node.left == null || node.right == null) {
                done = true;
            } else {
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        return node;
    }

    /**
     * Finds the furthest right node in the lowest level and returns it
     * @return the furthest right node in the lowest level
     */
    private Node<E> findLastNode() {
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(this.root);
        Node<E> last = null;
        while (!queue.isEmpty()) {
            last = queue.poll();
            if (last.left != null) {
                queue.offer(last.left);
            }
            if (last.right != null) {
                queue.offer(last.right);
            }
        }
        return last;
    }

    /**
     * A toString that prints an ASCII representation of the heap
     * @return the heap
     */
    @Override
    public String toString() {
        return buildString(this.root, "", true);
    }

    private String buildString(Node<E> node, String prefix, boolean isTail) {
        StringBuilder sb = new StringBuilder();
        if (node != null) {
            if (node.right != null) {
                sb.append(buildString(node.right, prefix + (isTail ? "│   " : "    "), false));
            }

            sb.append(prefix)
                    .append(isTail ? "└── " : "┌── ")
                    .append(node.data)
                    .append("\n");

            if (node.left != null) {
                sb.append(buildString(node.left, prefix + (isTail ? "    " : "│   "), true));
            }
        }
        return sb.toString();
    }
}
