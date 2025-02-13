/*
 * Course: CSC1120
 * Quiz 4 - Q1
 */
package quiz4;

import java.util.Arrays;
import java.util.List;

/**
 * Use a Functional Interface
 */
public class FunctionalOne {
    public static void main(String[] args) {
        // Q2
        System.out.println(doStringStuff("taco", "cat", (a, b) -> concat(a,b)));
        System.out.println(doStringStuff("cat", "taco", (a, b) -> a.length() < b.length() ? b : a));
        // Q3
        final List<Integer> numbers = Arrays.asList(5, 15, 8, 15, 20, 3, 20, 25);
        final int minValue = 10;
        List<Integer> result = numbers.stream()
                .filter(n -> n >= minValue)
                .distinct()
                .map(n -> n * 2)
                .toList();
        System.out.println(result);
        awfulMethod(numbers);
    }

    private static String doStringStuff(String a, String b, StringStuff ss) {
        return ss.apply(a, b);
    }

    // Q4
    private static void awfulMethod(List<Integer> list) {
        int total = 0; // 1
        // n
        for(int i = 0; i < list.size(); ++i) { // 1
            // 4
            for(int j = 0; j < 4; ++j) { // 1
                total += list.get(i) * j; // 3
                // increment and compare 2
            }
            // increment and compare 2
        }
        System.out.println(total); // 1
        // T(n) = 2 + n(3 + 21) = 2 + 24n = O(n)
    }

    private static String concat(String a, String b) {
        return a + b;
    }

    // Q1
    @FunctionalInterface
    private interface StringStuff {
        String apply(String a, String b);
    }
}
