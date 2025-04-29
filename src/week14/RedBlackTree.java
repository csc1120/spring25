/*
 * Course: CSC-1120
 * Red-Black Tree
 */
package week14;

import java.util.Objects;

/**
 * A partial implementation of a Red-Black Tree
 *
 * @param <E> the element type stored in the tree
 */
public class RedBlackTree<E extends Comparable<E>> extends BinarySearchTreeWithRotate<E> {
    private static class RedBlackNode<E> extends Node<E> {
        private boolean red;

        private RedBlackNode(E item) {
            super(item);
            this.red = true;
        }

        @Override
        public String toString() {
            return (this.red ? "Red: " : "Black: ") + super.toString();
        }
    }

    private boolean blackReduced;

    @Override
    public boolean add(E item) {
        if (this.root == null) {
            this.root = new RedBlackNode<>(item);
            ((RedBlackNode<E>) this.root).red = false;
            this.addResult = true;
        } else {
            this.root = add((RedBlackNode<E>) this.root, item);
            ((RedBlackNode<E>) Objects.requireNonNull(this.root,
                    "Node must not be null")).red = false;
        }
        return this.addResult;
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
                if (((RedBlackNode<E>) Objects.requireNonNull(localRoot.left,
                        "Node must not be null")).red) {
                    if (localRoot.left.left != null &&
                            ((RedBlackNode<E>) localRoot.left.left).red) {
                        // rotate and change colors
                        ((RedBlackNode<E>) localRoot.left).red = false;
                        localRoot.red = true;
                        localRoot = (RedBlackNode<E>) rotateRight(localRoot);
                        // check for case 3 (L-R and both are red)
                    } else if (localRoot.left.right != null &&
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

    @Override
    public E delete(E item) {
        E oldValue = null;
        this.blackReduced = false;
        if (this.root != null) {
            int comp = item.compareTo(this.root.data);
            if (comp == 0) {
                oldValue = this.root.data;
                this.root = findReplacement((RedBlackNode<E>) this.root);
                if (this.blackReduced) {
                    this.root = fixUpLeft((RedBlackNode<E>) this.root);
                }
            } else if (comp < 0) {
                if (this.root.left != null) {
                    oldValue = removeFromLeft((RedBlackNode<E>) this.root, item);
                    if (this.blackReduced) {
                        this.root = fixUpLeft((RedBlackNode<E>) this.root);
                    }
                }
            } else {
                if (this.root.right != null) {
                    oldValue = removeFromRight((RedBlackNode<E>) this.root, item);
                    if (this.blackReduced) {
                        this.root = fixUpRight((RedBlackNode<E>) this.root);
                    }
                }
            }
        }
        return oldValue;
    }

    private RedBlackNode<E> findReplacement(RedBlackNode<E> node) {
        RedBlackNode<E> result = null;
        if (node.left == null) {
            if (node.red) {
                result = (RedBlackNode<E>) node.right;
            } else if (node.right == null) {
                blackReduced = true;
            } else if (((RedBlackNode<E>) node.right).red) {
                ((RedBlackNode<E>) node.right).red = false;
                result = (RedBlackNode<E>) node.right;
            } else {
                throw new IllegalStateException("Invalid Red-Black "
                        + "Tree Structure");
            }
        } else if (node.right == null) {
            if (node.red) {
                result = (RedBlackNode<E>) node.left;
            } else if (((RedBlackNode<E>) node.left).red) {
                ((RedBlackNode<E>) node.left).red = false;
                result = (RedBlackNode<E>) node.left;
            } else {
                throw new IllegalStateException("Invalid Red-Black "
                        + "Tree structure");
            }
        } else {
            if (node.left.right == null) {
                node.data = node.left.data;
                if (((RedBlackNode<E>) node.left).red) {
                    node.left = node.left.left;
                } else if (node.left.left == null) {
                    blackReduced = true;
                    node.left = null;
                } else if (((RedBlackNode<E>) node.left.left).red) {
                    ((RedBlackNode<E>) node.left.left).red = false;
                    node.left = node.left.left;
                } else {
                    throw new IllegalStateException("Invalid Red-Black "
                            + "Tree structure");
                }
                result = node;
            } else {
                node.data = findLargestChild((RedBlackNode<E>) node.left);
                if (blackReduced) {
                    node.left = fixUpRight((RedBlackNode<E>) node.left);
                }
                if (blackReduced) {
                    result = fixUpLeft(node);
                } else {
                    result = node;
                }
            }
        }
        return result;
    }

    private E findLargestChild(RedBlackNode<E> parent) {
        E returnValue;
        if (parent.right.right == null) {
            returnValue = parent.right.data;
            if (((RedBlackNode<E>) parent.right).red) {
                parent.right = parent.right.left;
            } else if (parent.right.left == null) {
                blackReduced = true;
                parent.right = null;
            } else if (((RedBlackNode<E>) parent.right.left).red) {
                ((RedBlackNode<E>) parent.right.left).red = false;
                parent.right = parent.right.left;
            } else {
                throw new IllegalStateException("Invalid Red-Black "
                        + "Tree structure");
            }
        } else {
            returnValue = findLargestChild((RedBlackNode<E>) parent.right);
            if (blackReduced) {
                parent.right = fixUpRight((RedBlackNode<E>) parent.right);
            }
        }
        return returnValue;
    }

    private E removeFromLeft(RedBlackNode<E> parent, E item) {
        E oldValue;
        if (item.compareTo(parent.left.data) < 0) {
            if (parent.left.left == null) {
                return null;
            } else {
                oldValue = removeFromLeft((RedBlackNode<E>) parent.left, item);
                if (blackReduced) {
                    parent.left = fixUpLeft((RedBlackNode<E>) parent.left);
                }
            }
        } else if (item.compareTo(parent.left.data) > 0) {
            if (parent.left.right == null) {
                return null;
            } else {
                oldValue = removeFromRight((RedBlackNode<E>) parent.left, item);
                if (blackReduced) {
                    parent.left = fixUpRight((RedBlackNode<E>) parent.left);
                }
            }
        } else {
            oldValue = parent.left.data;
            parent.left = findReplacement((RedBlackNode<E>) parent.left);
        }
        return oldValue;
    }

    private E removeFromRight(RedBlackNode<E> parent, E item) {
        E oldValue;
        if (item.compareTo(parent.right.data) < 0) {
            if (parent.right.left == null) {
                return null;
            } else {
                oldValue = removeFromLeft((RedBlackNode<E>) parent.right, item);
                if (blackReduced) {
                    parent.right = fixUpLeft((RedBlackNode<E>) parent.right);
                }
            }
        } else if (item.compareTo(parent.right.data) > 0) {
            if (parent.right.right == null) {
                return null;
            } else {
                oldValue = removeFromRight((RedBlackNode<E>) parent.right, item);
                if (blackReduced) {
                    parent.right = fixUpRight((RedBlackNode<E>) parent.right);
                }
            }
        } else {
            oldValue = parent.right.data;
            parent.right = findReplacement((RedBlackNode<E>) parent.right);
        }
        return oldValue;
    }

    private RedBlackNode<E> fixUpRight(RedBlackNode<E> localRoot) {
        RedBlackNode<E> result;
        if (localRoot.right != null
                && ((RedBlackNode<E>) localRoot.right).red) {
            ((RedBlackNode<E>) localRoot.right).red = false;
            blackReduced = false;
            return localRoot;
        }
        RedBlackNode<E> s = (RedBlackNode<E>) localRoot.left;
        if (s.red) {
            s.red = false;
            localRoot.red = true;
            RedBlackNode<E> returnValue = (RedBlackNode<E>) rotateRight(localRoot);
            returnValue.right = fixUpRight((RedBlackNode<E>) returnValue.right);
            if (blackReduced) {
                result = fixUpRight(returnValue);
            } else {
                result = returnValue;
            }
        } else {
            if (s.left == null && s.right == null ||
                    s.left != null && !((RedBlackNode<E>) s.left).red
                            && s.right != null && !((RedBlackNode<E>) s.right).red) {
                s.red = true;
                result = localRoot;
            } else {
                if (s.right != null && ((RedBlackNode<E>) s.right).red) {
                    s.red = true;
                    ((RedBlackNode<E>) s.right).red = false;
                    localRoot.left = rotateLeft(s);
                    s = (RedBlackNode<E>) localRoot.left;
                }
                s.red = localRoot.red;
                assert s.left != null;
                ((RedBlackNode<E>) s.left).red = false;
                localRoot.red = false;
                blackReduced = false;
                result = (RedBlackNode<E>) rotateRight(localRoot);
            }
        }
        return result;
    }

    private RedBlackNode<E> fixUpLeft(RedBlackNode<E> localRoot) {
        RedBlackNode<E> result;
        if (localRoot.left != null
                && ((RedBlackNode<E>) localRoot.left).red) {
            ((RedBlackNode<E>) localRoot.left).red = false;
            blackReduced = false;
            return localRoot;
        }
        RedBlackNode<E> s = (RedBlackNode<E>) localRoot.right;
        if (s.red) {
            s.red = false;
            localRoot.red = true;
            Node<E> returnValue = rotateLeft(localRoot);
            returnValue.left = fixUpLeft((RedBlackNode<E>) returnValue.left);
            if (blackReduced) {
                result = fixUpLeft((RedBlackNode<E>) returnValue);
            } else {
                result = (RedBlackNode<E>) returnValue;
            }
        } else {
            if (s.right == null && s.left == null ||
                    s.right != null && !((RedBlackNode<E>) s.right).red
                            && s.left != null && !((RedBlackNode<E>) s.left).red) {
                s.red = true;
                result = localRoot;
            } else {
                if (s.left != null && ((RedBlackNode<E>) s.left).red) {
                    s.red = true;
                    ((RedBlackNode<E>) s.left).red = false;
                    localRoot.right = rotateRight(s);
                    s = (RedBlackNode<E>) localRoot.right;
                }
                s.red = localRoot.red;
                assert s.right != null;
                ((RedBlackNode<E>) s.right).red = false;
                localRoot.red = false;
                blackReduced = false;
                result = (RedBlackNode<E>) rotateLeft(localRoot);
            }
        }
        return result;
    }
}

