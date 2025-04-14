/*
 * Course: CSC-1120
 * Merge Sort
 */
package week12;

/**
 * A Merge Sort Implementation
 */
public class MergeSort implements SortAlgorithm {
    @Override
    public <T extends Comparable<T>> void sort(T[] table) {
        // Recursive method
        // If the array has more than one element (base case)
        if(table.length > 1) {
            // split the array in half
            int halfSize = table.length / 2;
            // Make two temp arrays out of the two halves
            T[] leftTable = (T[]) new Comparable[halfSize];
            T[] rightTable = (T[]) new Comparable[table.length - halfSize];
            // copy all the values from the original array into the temp arrays
            System.arraycopy(table, 0, leftTable, 0, halfSize);
            System.arraycopy(table, halfSize, rightTable, 0, table.length - halfSize);
            // recursive steps
            // call sort on each of the temp arrays
            sort(leftTable);
            sort(rightTable);
            // merge the resulting arrays back into the original array
            merge(table, leftTable, rightTable);
        }
    }
    private static <T extends Comparable<T>> void merge(T[] output,
                                                        T[] left,
                                                        T[] right) {
        int i = 0; // pointer for the left array
        int j = 0; // pointer for the right array
        int k = 0; // pointer for the output array
        // while there are still elements left in both sub-arrays
        while (i < left.length && k < right.length) {
            // add the smallest value between the left and right sub-arrays
            // to the output and increment the pointer of the array you
            // added from and the output array
            if (left[i].compareTo(right[j]) < 0) {
                output[k++] = left[i++];
            } else {
                output[k++] = right[j++];
            }
        }
        // At this point, only one sub-array has anything left. Add the rest
        // of that sub-array to the output.
        while (i < left.length) {
            output[k++] = left[i++];
        }
        while (j < right.length) {
            output[k++] = right[j++];
        }
    }
}
