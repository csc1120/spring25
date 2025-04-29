/*
 * Course: CSC-1120
 * Red-Black Tree
 */
package week14;

import java.util.Objects;

/**
 * Apartial implementation of a Red-Black Tree
 * @param <E> the element type stored in the tree
 */
public class RedBlackTree<E extends Comparable<E>> extends BinarySearchTreeWithRotate<E> {
    private static class RedBlackNode<E> extends Node<E> {
        private boolean red;
        private RedBlackNode(E item) {
            super(item);
            red = true;
        }

        @Override
        public String toString() {
            return (red ? "Red: " : "Black: ") + super.toString();
        }
    }

    private boolean blackReduced;

    @Override
    public boolean add(E item) {
        if(this.root == null) {
            this.root = new RedBlackNode<>(item);
            ((RedBlackNode<E>)this.root).red = false;
            this.addResult = true;
        } else {
            this.root = add((RedBlackNode<E>) this.root, item);
            ((RedBlackNode<E>) Objects.requireNonNull(this.root)).red = false;
        }
        return addResult;
    }

    private Node<E> add(RedBlackNode<E> localRoot, E item) {
        if (item.compareTo(localRoot.data) == 0) {
            this.addResult = false;
        } else if (item.compareTo(localRoot.data) < 0) {
            if (localRoot.left == null) {
                localRoot.left = new RedBlackNode<>(item);
                this.addResult = true;
            } else {
                // check for case 1
                if (localRoot.right != null &&
                        ((RedBlackNode<E>) localRoot.left).red &&
                        ((RedBlackNode<E>) localRoot.right).red) {
                    ((RedBlackNode<E>) localRoot.left).red = false;
                    ((RedBlackNode<E>) localRoot.right).red = false;
                    localRoot.red = true;
                }
                localRoot.left = add((RedBlackNode<E>) localRoot.left, item);
                // check for case 2 (L-L and both are red)
                if(((RedBlackNode<E>) Objects.requireNonNull(localRoot.left)).red) {
                    if(localRoot.left.left != null &&
                            ((RedBlackNode<E>) localRoot.left.left).red) {
                        // rotate and change colors
                        ((RedBlackNode<E>) localRoot.left).red = false;
                        localRoot.red = true;
                        localRoot = (RedBlackNode<E>) rotateRight(localRoot);
                        // check for case 3 (L-R and both are red)
                    } else if(localRoot.left.right != null &&
                            ((RedBlackNode<E>) localRoot.left.right).red) {
                        // rotate parent left, rotate grandparent right, swap colors
                        localRoot.left = rotateLeft(localRoot.left);
                        ((RedBlackNode<E>) localRoot.left).red = false;
                        localRoot.red = true;
                        localRoot = (RedBlackNode<E>) rotateRight(localRoot);
                    }
                }
            }
        } else {
            // homework - add to the right
        }
        return localRoot;
    }

}
