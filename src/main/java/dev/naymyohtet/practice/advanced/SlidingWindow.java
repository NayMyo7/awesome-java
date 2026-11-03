package dev.naymyohtet.practice.advanced;

public class SlidingWindow {
    public static void main(String[] args) {
        System.out.println("Welcome to Sliding Window!");

        System.out.println("Fixed-size sliding window: " + maxSumSubarray(new int[] { 2, 1, 5, 12, 2, 3 }, 3));

        System.out.println("Variable-size sliding window: " + minSubarrayLength(new int[] { 2, 3, 1, 2, 4, 3 }, 7));
    }

    // Fixed-size sliding window
    // Problem: Find the maximum sum of a contiguous subarray of size K.
    /**
     * Algorithm:
     * 1. Add the current element to windowSum.
     * 2. When the window reaches size K, update the maximum.
     * 3. Remove the leftmost element.
     * 4. Move the window forward.
     */
    public static int maxSumSubarray(int[] numbers, int k) {
        int maxSum = Integer.MIN_VALUE;
        int left = 0;
        int windowSum = 0;

        for (int right = 0; right < numbers.length; right++) {
            // Expand window
            windowSum += numbers[right];

            // Check if window size is k
            if (right - left + 1 == k) {

                maxSum = Math.max(maxSum, windowSum);

                // Shrink window from the left
                windowSum -= numbers[left];
                left++;
            }
        }

        return maxSum;
    }

    // Variable-size sliding window
    // Problem: Find the minimum length of a contiguous subarray whose sum is greater than or equal to K.
    /**
     * Algorithm:
     * 1. Expand the window by moving right.
     * 2. Add the new element to windowSum.
     * 3. If windowSum >= k, the window is valid.
     * 4. Update the minimum length.
     * 5. Shrink the window by moving left.
     * 6. Continue shrinking while the window remains valid.
     */
    public static int minSubarrayLength(int[] numbers, int k) {
        int windowSum = 0;
        int left = 0;
        int minLength = Integer.MAX_VALUE;

        for (int right = 0; right < numbers.length; right++) {

            // Expand the window
            windowSum += numbers[right];

            // Shrink while window is valid
            while (windowSum >= k) {
                minLength = Math.min(minLength, right - left + 1);
                windowSum -= numbers[left];
                left++;
            }
        }

        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
}
