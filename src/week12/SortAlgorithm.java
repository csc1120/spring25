/*
 * Course: CSC-1120
 * Sorting Algorithm Interface
 */
package week12;

/**
 * Interface to design sorting algorithms. Enforces
 * a Comparable element type and a public sort function
 */
public interface SortAlgorithm {
    /**
     * The method will perform an in-place sort on an array
     *
     * @param table the array to sort
     * @param <T> the type stored in the array. Must implement Comparable
     */
    <T extends Comparable<T>> void sort(T[] table);
}
