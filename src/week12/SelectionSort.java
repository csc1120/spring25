/*
 * Course: CSC-1120
 * Selection Sort
 */
package week12;

/**
 * Selection Sort Implementation
 */
public class SelectionSort implements SortAlgorithm {
    @Override
    public <T extends Comparable<T>> void sort(T[] table) {
        // start at the beginning (index 0)
        // iterate through the array, and find the smallest value
        // swap the smallest value with the current index
        // repeat until n-1
        for(int i = 0; i < table.length - 1; ++i) {
            int min = i;
            for(int j = i + 1; j < table.length; ++j) {
                // compare
                if(table[min].compareTo(table[j]) > 0) {
                    min = j;
                }
            }
            T temp = table[i];
            table[i] = table[min];
            table[min] = temp;
        }
    }
}
