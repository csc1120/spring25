/*
 * Course: CSC-1120
 */

package week12;

import java.util.ArrayList;
import java.util.List;

/**
 * TimSort
 */
public class TimSort implements SortAlgorithm {
    /**
     * Holds a run of values given a start location and the length of the run
     * @param startIndex start position of run
     * @param length length of run
     */
    private record Run(int startIndex, int length) {
    }

    private List<Run> runStack;

    /**
     * Sorts the array using the Timsort algorithm.
     * pre: table contains Comparable objects.
     * post: table is sorted.
     *
     * @param table The array to be sorted
     * @param <T> a Comparable type
     */
    public <T extends Comparable<T>> void sort(T[] table) {
        runStack = new ArrayList<>();
        int nRemaining = table.length;
        if (nRemaining > 1) {
            int lo = 0;
            do {
                int runLength = nextRun(table, lo);
                runStack.add(new Run(lo, runLength));
                mergeCollapse(table);
                lo += runLength;
                nRemaining = nRemaining - runLength;
            } while (nRemaining != 0);
            mergeForce(table);
        }
    }

    /**
     * Method to find the length of the next run.
     * A run is a sequence of ascending items such that
     * a[i] <= a[i+1] or descending items such that
     * a[i] >= a[i+1]. If a descending sequence is
     * found, it is turned into an ascending sequence.
     *
     * @param table The array to be sorted
     * @param lo    The index where the sequence starts
     * @return the length of the sequence.
     */
    private <T extends Comparable<T>> int nextRun(T[] table, int lo) {
        if (lo == table.length - 1) {
            return 1;
        }
        int hi = lo + 1;
        if (table[hi - 1].compareTo(table[hi]) <= 0) {
            while (hi < table.length && table[hi - 1].compareTo(table[hi]) <= 0) {
                ++hi;
            }
        } else {
            while (hi < table.length && table[hi - 1].compareTo(table[hi]) > 0) {
                ++hi;
            }
            swapRun(table, lo, hi - 1);
        }
        return hi - lo;
    }

    /**
     * Convert a descending run into an ascending run.
     *
     * @param table The array to be sorted
     * @param lo The start index
     * @param hi The end index
     */
    private <T extends Comparable<T>> void swapRun(T[] table, int lo, int hi) {
        while (lo < hi) {
            swap(table, lo, hi);
            ++lo;
            --hi;
        }
    }

    /**
     * Swap the items in table[i] and table[j].
     *
     * @param table The array to be sorted
     * @param i The index of one item
     * @param j The index of the other item
     */
    private <T extends Comparable<T>> void swap(T[] table, int i, int j) {
        T temp = table[i];
        table[i] = table[j];
        table[j] = temp;
    }

    /**
     * Merge adjacent runs until the invariant
     * below is satisfied.
     * 1. runLength[top - 2] >
     * runLength[top - 1] + runLength[top]
     * 2. runLength[top - 1] > runLength[top]
     * Called each time a new run is added to the stack.
     * Invariant is true before a new run is
     * added to the stack.
     *
     * @param table The array to be sorted
     */
    private <T extends Comparable<T>> void mergeCollapse(T[] table) {
        boolean done = false;
        while (runStack.size() > 1 && !done) {
            int top = runStack.size() - 1;
            if (top > 1 && runStack.get(top - 2).length <=
                    runStack.get(top - 1).length + runStack.get(top).length) {
                if (runStack.get(top - 2).length < runStack.get(top).length) {
                    mergeAt(table, top - 2); // merge i-2, i-1
                } else {
                    mergeAt(table, top - 1); // merge i-1, i
                }
            } else if (runStack.get(top - 1).length <= runStack.get(top).length) {
                mergeAt(table, top - 1); // merge runs i-1, i
            } else {
                done = true;  // invariant is satisfied
            }
        }
    }

    /**
     * Merge all remaining runs to complete the sort.
     * Merge runs at top - 1 and top until only 1 run on stack
     *
     * @param table The array to be sorted
     */
    private <T extends Comparable<T>> void mergeForce(T[] table) {
        while (runStack.size() > 1) {
            int top = runStack.size() - 1;
            mergeAt(table, top - 1);
        }
    }

    /**
     * Merges stack elements at i and i + 1
     * @param table The array to be sorted
     * @param i The stack index of a run to be merged
     */
    @SuppressWarnings({"unchecked"})
    private <T extends Comparable<T>> void mergeAt(T[] table, int i) {
        int base1 = runStack.get(i).startIndex;
        int len1 = runStack.get(i).length;
        int base2 = runStack.get(i + 1).startIndex;
        int len2 = runStack.get(i + 1).length;
        runStack.set(i, new Run(base1, len1 + len2));
        if (i == runStack.size() - 3) {
            runStack.set(i + 1, runStack.get(i + 2));
        }
        runStack.removeLast();
        if ((table[base1 + len1 - 1]).compareTo(table[base2]) > 0) {
            T[] run1 = (T[]) (new Comparable[len1]);
            T[] run2 = (T[]) (new Comparable[len2]);
            System.arraycopy(table, base1, run1, 0, len1);
            System.arraycopy(table, base2, run2, 0, len2);
            merge(table, base1, run1, run2);
        }
    }

    // replace this with insert merge in Listing 8.4
    private <T extends Comparable<T>> void merge(T[] output,
                                                 int destination,
                                                 T[] left,
                                                 T[] right) {
        int i = 0; // Index into the left input sequence.
        int j = 0; // Index into the right input sequence.
        int k = destination; // Index into the output sequence.

        // While there is data in both input sequences
        while (i < left.length && j < right.length) {
            // Find the smaller and
            // insert it into the output sequence.
            if (left[i].compareTo(right[j]) < 0) {
                output[k++] = left[i++];
            } else {
                output[k++] = right[j++];
            }
        }
        // assert: one of the sequences has more items to copy.
        // Copy remaining input from left sequence into the output.
        while (i < left.length) {
            output[k++] = left[i++];
        }
        // Copy remaining input from right sequence into output.
        while (j < right.length) {
            output[k++] = right[j++];
        }
    }
}