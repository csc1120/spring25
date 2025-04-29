/*
 * Course: CSC-1120
 * Binary Search Tree with Rotations
 */
package week14;

import week9.BinarySearchTree;

/**
 * A BST that includes rotation
 * @param <E> the element type stored in the tree
 */
public class BinarySearchTreeWithRotate<E extends Comparable<E>> extends BinarySearchTree<E> {
    /**
     * A method to perform a left rotation on a tree at the given root
     * 1. Set temp to the node's right child
     * 2. Set the node's right child to temp's left child
     * 3. Set temp's left child to the node
     * 4. Return temp as the new root of the subtree
     * @param root the root of the subtree to be rotated
     * @return the new root of the subtree after rotation
     */
    protected Node<E> rotateLeft(Node<E> root) {
        Node<E> temp = root.right;
        root.right = temp.left;
        temp.left = root;
        return temp;
    }

    /**
     * A method to perform a right rotation on a tree at the given root
     * 1. Set temp to the node's left child
     * 2. Set the node's left child to temp's right child
     * 3. Set temp's right child to the node
     * 4. Return temp as the new root of the subtree
     * @param root the root of the subtree to be rotated
     * @return the new root of the subtree after rotation
     */
    protected Node<E> rotateRight(Node<E> root) {
        Node<E> temp = root.left;
        root.left = temp.right;
        temp.right = root;
        return temp;
    }
}
