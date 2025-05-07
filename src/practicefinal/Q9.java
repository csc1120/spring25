/*
 * Course: CSC-1120
 * Recursion Question
 */
package practicefinal;

import java.util.List;

/**
 * Recursion
 */
public class Q9 {
    public static void main(String[] args) {
        final int[] nums1 = {1, 2, 4};
        final int[] nums2 = {2, 4, 6};
        final int[] nums3 = {1, 3, 5, 7, 9};
        final int[] nums4 = {0, 2, 4, 6, 8};
        System.out.println(canSplitEvenOdd(nums1));
        System.out.println(canSplitEvenOdd(nums2));
        System.out.println(canSplitEvenOdd(nums3));
        System.out.println(canSplitEvenOdd(nums4));
    }

    private static boolean canSplitEvenOdd(int[] nums) {
        return canSplitEvenOdd(nums, 0, 0, 0);
    }

    private static boolean canSplitEvenOdd(int[]nums, int even, int odd, int index) {
        if(index == nums.length) {
            return even % 2 == 0 && odd % 2 == 1;
        }
        return canSplitEvenOdd(nums, even + nums[index], odd, index + 1) ||
                canSplitEvenOdd(nums, even, odd + nums[index], index + 1);
    }
}
