/*
 * Course: CSC-1120
 * Heap
 */
package week11a;

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
        if (this.root == null) {
            this.root = newNode;
        } else {
            Node<E> parent = findInsertionParent();
            if (parent.left == null) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
            newNode.parent = parent;
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
        if (this.root == null) {
            return null;
        }
        E min = this.root.data;
        if (this.size == 1) {
            this.root = null;
        } else {
            Node<E> lastNode = findLastNode();
            this.root.data = lastNode.data;

            if (lastNode.parent.right == lastNode) {
                lastNode.parent.right = null;
            } else {
                lastNode.parent.left = null;
            }
            heapifyDown(this.root);
        }
        --this.size;
        return min;
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
        while (node.parent != null && node.data.compareTo(node.parent.data) < 0) {
            E temp = node.data;
            node.data = node.parent.data;
            node.parent.data = temp;
            node = node.parent;
        }
    }

    private void heapifyDown(Node<E> node) {
        boolean done = false;
        while (node != null && !done) {
            Node<E> smallest = node;
            if (node.left != null && node.left.data.compareTo(smallest.data) < 0) {
                smallest = node.left;
            }
            if (node.right != null && node.right.data.compareTo(smallest.data) < 0) {
                smallest = node.right;
            }
            if (smallest != node) {
                E temp = node.data;
                node.data = smallest.data;
                smallest.data = temp;
                node = smallest;
            } else {
                done = true;
            }
        }
    }

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
