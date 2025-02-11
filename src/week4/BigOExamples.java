/*
 * Course: CSC1120
 * Big-O Calculation examples
 */
package week4;

import java.util.List;

/**
 * Examples of Big-O calculation
 */
public class BigOExamples {
    public static void main(String[] args) {

    }

    private static int count(List<String> list) {
        int count = 0; // 1
        // n times
        for(int i = 0; i < list.size(); ++i) { // 1 for initialize,
            count += list.get(i).length(); // 3 things, get, get length, add to count
            // increment i, check exit condition, 2
        } // 5 * n = 5n
        return count; // 1
        // total 5n + 3 = T(n)
        // Big-O drop constant and any coefficient -> O(n)
    }

    private static int wordLength(int index, List<String> list) {
        return list.get(index).length(); // 3
        // Constant time -> O(1)
    }

    private static long terribleMethod(List<String> list) {
        long total = 0L; // 1
        // n
        for (int i = 0; i < list.size(); i++) { // 1
            // n
            for(int j = 0; j < list.size(); ++j) { // 1
                total += list.get(i).length() + list.get(j).length(); // 5
                // increment & exit 2
            } // 7n + 1
            // increment & exit 2
        } // n * (7n + 1)
        return total; // 1
        // T(n) = n(7n + 1) + 3 = 7n^2 + n + 3 -> O(n^2) -> largest exponent of n
    }

    private static void evenWorseMethod(List<String> list) {
        long count = 0L; // 1
        // n
        for (int i = 0; i < list.size(); i++) { // 1
            count += count(list); // 1 + count = O(n) -> n + 1
            // increment and  compare 2
        } // n(n + 3) -> n^2 + n
        System.out.println(count); // 1
        // T(n) = n^2 + n + 3 -> O(n^2)
    }
}
