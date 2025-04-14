/*
 * Course: CSC-1120
 * Shell Sort
 */
package week12;

/**
 * A Shell Sort Implementation
 */
public class ShellSort implements SortAlgorithm {
    @Override
    public <T extends Comparable<T>> void sort(T[] table) {
        final double shellConstant = 2.2;
        int gap = table.length / 2; // starting gap is half the length
        while(gap > 0) { // while the gap is still 1 or greater
            for(int nextPos = gap; nextPos < table.length; ++nextPos) {
                // starting index = gap
                int nextInsert = nextPos;
                // store the current value to swap
                T nextVal = table[nextInsert];
                // while the current location - gap is still in the array
                // and the current value is less than the previous value of the subarray
                while(nextInsert > gap - 1 && nextVal.compareTo(table[nextInsert - gap]) < 0) {
                    // swap the current value with the previous value
                    table[nextInsert] = table[nextInsert - gap];
                    // current index is now where we swapped the value to (index - gap)
                    nextInsert -= gap;
                }
                // copy the current value to its proper location
                table[nextInsert] = nextVal;
                // back to the top of the for loop, increment to the next sub-array
            }
            // once a single pass on the initial sub-arrays is completed, shrink the gap
            // edge case to ensure we reach gap of 1
            if(gap == 2) {
                gap = 1;
            } else {
                // divide by the constant to get the next smaller gap
                gap = (int) (gap / shellConstant);
            }
        }
    }
}
