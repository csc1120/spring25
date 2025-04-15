/*
 * Course: CSC-1120
 * Heap
 */
package week12;

/**
 * A simple heap using a binary tree structure
 *
 * @param <E> the type of element stored in the heap
 */
public class Heap<E extends Comparable<E>> {


    /**
     * Reports the size of the heap
     *
     * @return the size of the heap
     */
    public int size() {
        return this.size;
    }



//    @Override
//    public String toString() {
//        return buildString(this.root, "", true);
//    }
//
//    private String buildString(Node<E> node, String prefix, boolean isTail) {
//        StringBuilder sb = new StringBuilder();
//        if (node != null) {
//            if (node.right != null) {
//                sb.append(buildString(node.right, prefix + (isTail ? "│   " : "    "), false));
//            }
//
//            sb.append(prefix)
//                    .append(isTail ? "└── " : "┌── ")
//                    .append(node.data)
//                    .append("\n");
//
//            if (node.left != null) {
//                sb.append(buildString(node.left, prefix + (isTail ? "    " : "│   "), true));
//            }
//        }
//        return sb.toString();
//    }
}
