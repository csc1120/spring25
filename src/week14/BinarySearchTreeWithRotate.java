package week14;

import week9.BinarySearchTree;

public class BinarySearchTreeWithRotate<E extends Comparable<E>> extends BinarySearchTree<E> {
    protected Node<E> rotateLeft(Node<E> root) {
        // step 1 set temp to root
        Node<E> temp = root.right;
        // step 2 set root.right to temp.left
        root.right = temp.left;
        // step 3 set temp.left = root;
        temp.left = root;
        // make temp the root
        return temp;
    }

    protected Node<E> rotateRight(Node<E> root) {
        Node<E> temp = root.left;
        root.left = temp.right;
        temp.right = root;
        return temp;
    }
}
