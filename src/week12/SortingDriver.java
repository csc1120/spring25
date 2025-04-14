/*
 * Course: CSC-1120
 */
package week12;

import java.util.StringJoiner;

/**
 * A simple driver to run sorting algorithms
 */
public class SortingDriver {
    public static void main(String[] args) {
        SelectionSort selection = new SelectionSort();
        InsertionSort insertion = new InsertionSort();
        ShellSort shell = new ShellSort();
        MergeSort merge = new MergeSort();
        final Integer[] unsorted = {8, 3, 5, 4, 1, 7, 9, 6, 2};
        Integer[] param = new Integer[unsorted.length];

        System.arraycopy(unsorted, 0, param, 0, unsorted.length);
        shell.sort(param);
        printArray(param);
    }

    private static void printArray(Integer[] arr) {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for(Integer i: arr) {
            sj.add(i.toString());
        }
        System.out.println(sj);
    }
}
