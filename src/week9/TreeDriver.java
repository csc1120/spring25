/*
 * Course: CSC-1120
 * Binary Tree Driver
 */
package week9;

/**
 * A simple driver to show a basic Binary Tree (without rules for adding/removing)
 */
public class TreeDriver {
    public static void main(String[] args) {
        BinaryTree<Integer> d = new BinaryTree<>(4);
        BinaryTree<Integer> c = new BinaryTree<>(2, null, d);
        BinaryTree<Integer> b = new BinaryTree<>(3);
        BinaryTree<Integer> a = new BinaryTree<>(4, b, c);
        System.out.println(a);
    }
}
