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
        System.out.println(doStringStuff("taco", "cat", (a, b) -> a + b));
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
        int total = 0;
        for(int i = 0; i < list.size(); ++i) {
            for(int j = 0; j < 4; ++j) {
                total += list.get(i) * j;
            }
        }
        System.out.println(total);
    }

    // Q1
    @FunctionalInterface
    private interface StringStuff {
        String apply(String a, String b);
    }
}
