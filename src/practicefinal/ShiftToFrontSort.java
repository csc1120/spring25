/*
 * Course: CSC-1120
 * ShiftToFrontSort
 */
package practicefinal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sorting algorithm that inserts any element less than the element at index 0 at index 0
 */
public class ShiftToFrontSort {
    public static void main(String[] args) {
        final Integer[] arr = {5, 2, 4, 6, 1, 3};
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(arr));
        shiftToFrontSort(list);
        System.out.println(list);
    }

    private static void shiftToFrontSort(List<Integer> list) {
        for (int i = 0; i < list.size(); ++i) {
            for (int j = i + 1; j < list.size(); ++j) {
                if (list.get(j) < list.get(i)) {
                    list.add(i, list.remove(j));
                }
            }
            System.out.println(list);
        }
    }
}