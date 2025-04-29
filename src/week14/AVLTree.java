/*
 * Course: CSC-1120
 * AVLTree implementation
 */
package week14;

/**
 * A "simple" AVLTree implementation
 *
 * @param <E> the element type stored in the tree
 */
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
        this.root = add((AVLNode<E>) this.root, item);
        return this.addResult;
    }

    private Node<E> add(AVLNode<E> localRoot, E item) {
        // base case
        if (localRoot == null) {
            // add the new node here
            addResult = true;
            // adding a node will increase the depth of the tree
            this.increase = true;
            return new AVLNode<>(item);
        }
        if (item.compareTo(localRoot.data) < 0) {
            // go left
            localRoot.left = add((AVLNode<E>) localRoot.left, item);
            // added to the left, decrement balance if the depth increases
            if (increase) {
                decrementBalance(localRoot);
                // do I need to rebalance?
                if (localRoot.balance < AVLNode.LEFT_HEAVY) {
                    // Critical imbalance. Fixing this will handle the depth increase
                    increase = false;
                    return rebalanceLeft(localRoot);
                }
            }
        } else if (item.compareTo(localRoot.data) > 0) {
            localRoot.right = add((AVLNode<E>) localRoot.right, item);
            if (increase) {
                incrementBalance(localRoot);
                if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
                    increase = false;
                    return rebalanceRight(localRoot);
                }
            }
        } else {
            // could not add the node (a duplicate)
            this.increase = false;
            addResult = false;
        }
        // return the resulting node to the parent
        return localRoot;
    }

    private void decrementBalance(AVLNode<E> localRoot) {
        --localRoot.balance;
        if (localRoot.balance == AVLNode.BALANCED) {
            this.increase = false;
        }
    }

    private void incrementBalance(AVLNode<E> localRoot) {
        ++localRoot.balance;
        if (localRoot.balance == AVLNode.BALANCED) {
            this.increase = false;
        }
    }

    private AVLNode<E> rebalanceLeft(AVLNode<E> localRoot) {
        // what kind of imbalance?
        AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
        if (leftChild.balance > AVLNode.BALANCED) {
            // L-R imbalance
            // Update the balances for rotating the subtree to the left
            updateLeftBalances(localRoot, leftChild);
            localRoot.left = rotateLeft(leftChild);
        } else {
            // L-L imbalance
            // Update the balances for rotating the tree to the right
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        return (AVLNode<E>) rotateRight(localRoot);
    }

    private void updateLeftBalances(AVLNode<E> localRoot, AVLNode<E> leftChild) {
        // Get the left child's right child, as this is only called on an L-R imbalanced tree
        AVLNode<E> leftRightChild = (AVLNode<E>) leftChild.right;
        // Three possibilities:
        if (leftRightChild.balance < AVLNode.BALANCED) {
            // The left child's right subtree is left imbalanced (L-R-L)
            leftChild.balance = AVLNode.BALANCED;
            leftRightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.RIGHT_HEAVY;
        } else if (leftRightChild.balance > AVLNode.BALANCED) {
            // The left child's right subtree is right imbalanced (L-R-R)
            leftChild.balance = AVLNode.LEFT_HEAVY;
            leftRightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        } else {
            // The left child's right subtree is balanced
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
    }

    private AVLNode<E> rebalanceRight(AVLNode<E> localRoot) {
        // what kind of imbalance?
        AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;
        if (rightChild.balance < AVLNode.BALANCED) {
            // R-L imbalance
            // Update the balances for rotating the subtree to the right
            updateRightBalances(localRoot, rightChild);
            localRoot.right = rotateLeft(rightChild);
        } else {
            // R-R imbalance
            // Update the balances for rotating the tree to the left
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
        return (AVLNode<E>) rotateLeft(localRoot);
    }

    private void updateRightBalances(AVLNode<E> localRoot, AVLNode<E> rightChild) {
        // Get the right child's left child, as this is only called on an R-L imbalanced tree
        AVLNode<E> rightLeftChild = (AVLNode<E>) rightChild.right;
        // Three possibilities:
        if (rightLeftChild.balance > AVLNode.BALANCED) {
            // The right child's left subtree is right imbalanced (R-L-R)
            rightChild.balance = AVLNode.RIGHT_HEAVY;
            rightLeftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        } else if (rightLeftChild.balance < AVLNode.BALANCED) {
            // The right child's left subtree is left imbalanced (R-L-L)
            rightChild.balance = AVLNode.BALANCED;
            rightLeftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.LEFT_HEAVY;
        } else {
            // The right child's left subtree is balanced
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }
    }

    @Override
    public E delete(E target) {
        this.decrease = false;
        this.root = delete((AVLNode<E>) this.root, target);
        return this.deleteReturn;
    }

    private Node<E> delete(AVLNode<E> localRoot, E target) {
        // base case - target does not exist
        if (localRoot == null) {
            this.deleteReturn = null;
            this.decrease = false;
        } else if (target.compareTo(localRoot.data) < 0) {
            // target is less than the current value, go left
            localRoot.left = delete((AVLNode<E>) localRoot.left, target);
            // if we removed a node, the balanced decreased
            if (this.decrease) {
                // increment the balance, because we removed a node from the left subtree
                incrementBalance(localRoot);
                // if this causes the treeto be critically imbalanced, rebalance
                if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
                    localRoot = rebalanceRightLeft(localRoot);
                }
            }
        } else if (target.compareTo(localRoot.data) > 0) {
            // target is greater than the current value, go right
            localRoot.right = delete((AVLNode<E>) localRoot.right, target);
            // if we removed a node, the balanced decreased
            if (this.decrease) {
                // decrement the balance, because we removed a node from the right subtree
                decrementBalance(localRoot);
                // if this causes the tree to be critically imbalanced, rebalance
                if (localRoot.balance < AVLNode.LEFT_HEAVY) {
                    localRoot = rebalanceLeftRight(localRoot);
                }
            }
        } else {
            // This is the node to return. Store the value
            this.deleteReturn = localRoot.data;
            // Find the node to replace this node
            localRoot = findReplacementNode(localRoot);
        }
        // Return the root of this subtree
        return localRoot;
    }

    private AVLNode<E> rebalanceRightLeft(AVLNode<E> localRoot) {
        AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;
        if (rightChild.balance > AVLNode.BALANCED) {
            updateRightBalances(localRoot, rightChild);
            this.increase = false;
            this.decrease = true;
            localRoot.right = rotateRight(rightChild);
        } else if(rightChild.balance < AVLNode.BALANCED) {
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
            this.increase = false;
            this.decrease = true;
        } else {
            rightChild.balance = AVLNode.LEFT_HEAVY;
            localRoot.balance = AVLNode.RIGHT_HEAVY;
        }
        return (AVLNode<E>) rotateLeft(localRoot);
    }

    private AVLNode<E> rebalanceLeftRight(AVLNode<E> localRoot) {
        AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;
        if (leftChild.balance > AVLNode.BALANCED) {
            updateLeftBalances(localRoot, leftChild);
            this.increase = false;
            this.decrease = true;
            localRoot.left = rotateLeft(leftChild);
        } else if(leftChild.balance < AVLNode.BALANCED) {
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
            this.increase = false;
            this.decrease = true;
        } else {
            leftChild.balance = AVLNode.RIGHT_HEAVY;
            localRoot.balance = AVLNode.LEFT_HEAVY;
        }
        return (AVLNode<E>) rotateRight(localRoot);
    }

    private AVLNode<E> findReplacementNode(AVLNode<E> node) {
        if(node.left == null) {
            this.decrease = true;
            return (AVLNode<E>) node.right;
        } else if(node.right == null) {
            this.decrease = true;
            return (AVLNode<E>) node.left;
        } else {
            // has two children. need to find the largest child in the left subtree
            if(node.left.right == null) {
                // if the left subtree has no right nodes, use the root of the subtree
                node.data = node.left.data;
                node.left = node.left.left;
                // removing from the left increases the balance
                incrementBalance(node);
            } else {
                // otherwise, go down the right of the subtree until you find the largest value
                node.data = findLargestChild((AVLNode<E>) node.left);
                // if removing the largest child makes the subtree critically unbalanced, rebalance
                if(((AVLNode<E>) node.left).balance < AVLNode.LEFT_HEAVY) {
                    node.left = rebalanceLeft((AVLNode<E>) node.left);
                }
                // if the height was decreased (on the left side), increment the balance
                if(this.decrease) {
                    incrementBalance(node);
                }
            }
        }
        return node;
    }
    private E findLargestChild(AVLNode<E> parent) {
        E returnValue = null;
        // if the right node is the last node, it is the largest
        if(parent.right.right == null) {
            returnValue = parent.right.data;
            parent.right = parent.right.left;
            decrementBalance(parent);
        } else {
            // recursively find the largest
            returnValue = findLargestChild((AVLNode<E>) parent.right);
            // if removing the node causes a critical imbalance, rebalance
            if(((AVLNode<E>) parent.right).balance < AVLNode.LEFT_HEAVY) {
                parent.right = rebalanceLeft((AVLNode<E>) parent.right);
            }
            // if the tree ended up decreasing (on the right side), decrement the balance
            if(this.decrease) {
                decrementBalance(parent);
            }
        }
        return returnValue;
    }
}

