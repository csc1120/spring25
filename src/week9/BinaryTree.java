/*
 * Course: CSC-1120
 * Binary Search Tree
 */
package week9;

import java.util.function.BiConsumer;

/**
 * A basic Binary Tree implementation.
 * @param <E> the element type stored in the tree
 */
public class BinaryTree<E> {
    protected static class Node<E> {
        /**
         * The left child of the node
         */
        public Node<E> left;
        /**
         * The right child of the node
         */
        public Node<E> right;
        /**
         * The data stored in the node
         */
        public E data;

        protected Node(E data) {
            this.data = data;
            left = null;
            right = null;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    protected Node<E> root;

    /**
     * No-param constructor that sets the root node to null
     */
    public BinaryTree() {
        this.root = null;
    }

    /**
     * Constructor that assigns data to the root of the tree
     * @param data the data for the root node
     */
    public BinaryTree(E data) {
        this.root = new Node<>(data);
    }

    /**
     * Constructor that assigns a root node
     * @param localRoot the root node of the tree
     */
    public BinaryTree(Node<E> localRoot) {
        this.root = localRoot;
    }

    /**
     * Constructor that adds data to the root as well as left and right subtrees
     * @param data the data for the root node
     * @param leftTree the left subtree
     * @param rightTree the right subtree
     */
    public BinaryTree(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree) {
        this.root = new Node<>(data);
        if(leftTree != null) {
            this.root.left = leftTree.root;
        } else {
            this.root.left = null; // not necessary to code
        }
        if(rightTree != null) {
            this.root.right = rightTree.root;
        }
    }

    /**
     * Returns the left subtree or null if none exists
     * @return the left subtree or null if none exists
     */
    public BinaryTree<E> getLeftSubtree() {
        if(this.root != null && this.root.left != null) {
            return new BinaryTree<>(root.left);
        } else {
            return null;
        }
    }

    /**
     * Returns the right subtree or null if none exists
     * @return the right subtree or null if none exists
     */
    public BinaryTree<E> getRightSubtree() {
        if(this.root != null && this.root.right != null) {
            return new BinaryTree<>(root.right);
        } else {
            return null;
        }
    }

    /**
     * Returns the data stored in the root node
     * @return the data stored in the root node or null if the root is null
     */
    public E getData() {
        return this.root == null ? null: root.data;
    }

    /**
     * Returns true if the root of the tree is a leaf node
     * @return true if both children of the root are null, false otherwise
     */
    public boolean isLeaf() {
        return this.root.left == null && this.root.right == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Uses a post-order traversal to print out all the elements in the tree
        // using the depth of the nodes to indent and space the output
        postOrderTraversal(this.root, 1, (e, i) -> {
            sb.append("  ".repeat(Math.max(0, i)));
            sb.append(e.toString()).append("\n");
        });
        return sb.toString();
    }

    /**
     * Pre-order traversal of the node (Visit, Left, Right)
     * @param consumer the function performed during the traversal
     */
    public void preOrderTraversal(BiConsumer<E, Integer> consumer) {
        preOrderTraversal(this.root, 1, consumer);
    }
    private void preOrderTraversal(Node<E> node, int depth, BiConsumer<E, Integer> consumer) {
        // Visit, Left, Right
        if(node != null) {
            // Visit
            consumer.accept(node.data, depth);
            // Left
            preOrderTraversal(node.left, depth + 1, consumer);
            // Right
            preOrderTraversal(node.right, depth + 1, consumer);
        }
    }

    /**
     * In-order traversal of the node (Left, Visit, Right)
     * @param consumer the function performed during the traversal
     */
    public void inOrderTraversal(BiConsumer<E, Integer> consumer) {
        inOrderTraversal(this.root, 1, consumer);
    }
    private void inOrderTraversal(Node<E> node, int depth, BiConsumer<E, Integer> consumer) {
        // Left, Visit, Right
        if(node != null) {
            // Left
            inOrderTraversal(node.left, depth + 1, consumer);
            // Visit
            consumer.accept(node.data, depth);
            // Right
            inOrderTraversal(node.right, depth + 1, consumer);
        }
    }

    /**
     * Post-order traversal of the node (Left, Right, Visit)
     * @param consumer the function performed during the traversal
     */
    public void postOrderTraversal(BiConsumer<E, Integer> consumer) {
        postOrderTraversal(this.root, 1, consumer);
    }

    private void postOrderTraversal(Node<E> node, int depth, BiConsumer<E, Integer> consumer) {
        // Left, Right, Visit
        if(node != null) {
            // Left
            postOrderTraversal(node.left, depth + 1, consumer);
            // Right
            postOrderTraversal(node.right, depth + 1, consumer);
            // Visit
            consumer.accept(node.data, depth);
        }
    }
}