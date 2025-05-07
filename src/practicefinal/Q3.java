/*
 * Course: CSC-1120
 * Stream Question
 */
package practicefinal;

import java.util.Arrays;
import java.util.List;

/**
 * Stream Question
 */
public class Q3 {
    public static void main(String[] args) {
        final List<Integer> list = Arrays.asList(7, 3, 2, 8, 9, 4, 7, 3, 1);
        System.out.println(squaresOfMultiplesOfSeven(list));
    }

    /**
     * Returns the squared values of only the numbers divisible by 7 after being squared
     * @param nums the list to process
     * @return the resulting list
     */
    public static List<Integer> squaresOfMultiplesOfSeven(List<Integer> nums) {
        final int divisor = 7;
        return nums.stream().map(i -> i * i).filter(i -> i % divisor == 0).toList();
    }
}
