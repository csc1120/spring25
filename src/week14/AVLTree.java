package week14;

public class AVLTree<E extends Comparable<E>> extends BinarySearchTreeWithRotate<E> {
    private static class AVLNode<E> extends Node<E> {
        private static final int LEFT_HEAVY = -1;
        private static final int RIGHT_HEAVY = 1;
        private static final int BALANCED = 0;
        private int balance;

        private AVLNode(E data) {
            super(data);
            this.balance = BALANCED;
        }

        @Override
        public String toString() {
            return balance + ": " + super.toString();
        }
    }

    private boolean increase;
    private boolean decrease;

    @Override
    public boolean add(E item) {
        this.increase = false;
        this.root = add((AVLNode<E>) this.root, E item);
        return this.addResult;
    }

    private Node<E> add(AVLNode<E> localRoot, E item) {
        // base case
        if(localRoot == null) {
            addResult = true;
            this.increase = true;
            return new AVLNode<>(item);
        }
        if(item.compareTo(localRoot.data) < 0) {
            // go left
            localRoot.left = add((AVLNode<E>) localRoot.left, item);
            // added to the left, decrement balance
            if(increase) {
                decrementBalance(localRoot);
                // do I need to rebalance?
                if (localRoot.balance < AVLNode.LEFT_HEAVY) {
                    // critical imbalance. fix.
                    increase = false;
                    return rebalanceLeft(localRoot);
                }
            }
        } else if(item.compareTo(localRoot.data) > 0) {
            localRoot.right = add((AVLNode<E>) localRoot.right, item);
            if(increase) {
                incrementBalance(localRoot);
                // do I need to rebalance?
                if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
                    // critical imbalance. fix.
                    increase = false;
                    return rebalanceRight(localRoot);
                }
            }
        } else {
            this.increase = false;
            addResult = false;
        }
        return localRoot;
    }

    private void decrementBalance(AVLNode<E> localRoot) {
        --localRoot.balance;
        if(localRoot.balance == AVLNode.BALANCED) {
            this.increase = false;
        }
    }

    private void incrementBalance(AVLNode<E> localRoot) {
        ++localRoot.balance;
        if(localRoot.balance == AVLNode.BALANCED) {
            this.increase = false;
        }
    }

    private AVLNode<E> rebalanceLeft(AVLNode<E> localRoot) {
        // what kind of imbalance?
        AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
        if(leftChild.balance > AVLNode.BALANCED) {
            // L-R imbalance
            // rotate subtree to the left
//            updateLeftBalances(localRoot, leftChild);
            localRoot.left = rotateLeft(leftChild);
        } else {
            // L-L imbalance
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        return (AVLNode<E>) rotateRight(localRoot);
    }

    private AVLNode<E> rebalanceRight(AVLNode<E> localRoot) {

        return null;
    }
}

