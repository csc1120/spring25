/*
 * Course: CSC-1120
 * Recursion/Binary Search
 */
package quiz8;

/**
 * Recursion question
 */
public class Quiz8 {
    public static void main(String[] args) {
        System.out.println(countHi("xxhixx"));
        System.out.println(countHi("xhixhix"));
        System.out.println(countHi("hi"));
//        System.out.println(array11(new int[] {1, 2, 11}, 0));
//        System.out.println(array11(new int[] {11, 11}, -1));
    }

    private static int countHi(String s) {
        if(s.length() <= 1) {
            return 0;
        }
        if(s.charAt(0) == 'h' && s.charAt(1) == 'i') {
            return 1 + countHi(s.substring(1));
        }
        return countHi(s.substring(1));
    }

    private static int array11(int[] nums, int index) {
        final int target = 11;
        if(index == nums.length) {
            return 0;
        }
        return (nums[index] == target ? 1 : 0) + array11(nums, index + 1);
    }
}
