package dev.naymyohtet.grokkingalgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwoPointers {
    public static void main(String[] args) {
        System.out.println("Welcome from Two Pointer techniques!");

        char[] s = { 'h', 'e', 'l', 'l', 'o' };
        System.out.println("Original: " + Arrays.toString(s));
        reverseString(s);
        System.out.println("Reversed: " + Arrays.toString(s));

        // Three Sum
        int[] nums = new int[] { -1, 0, 1, 2, -1, -4 };
        System.out.println("Three sum of " + Arrays.toString(nums) + ": " + threeSum(nums));

        // Max Area
        int[] height = new int[] { 1, 8, 6, 2, 5, 4, 8, 3, 7 };
        System.out.println("Max area: " + maxArea(height));

        // Trapping rain water
        height = new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
        System.out.println("Trap Water: " + trap(height));
    }

    // Reverse String
    public static void reverseString(char[] s) {
        int left = 0;
        int right = s.length - 1;

        while (left < right) {
            char temp = s[left];
            s[left] = s[right];
            s[right] = temp;
            left++;
            right--;
        }
    }

    // Remove duplicate from sorted array
    /**
     * Two pointers (left = slow = tracker, right = fast = explorer)
     * 1 1 2 2 3
     * ^ ^
     * l r
     * 
     * 1 1 2 2 3
     * ^ ^
     * l r
     * 
     * 1 1 2 2 3 => 1 2 2 2 3
     * ^ ^ ^ ^
     * l r l r
     * 
     * 1 2 2 2 3
     * ^ ^
     * l r
     * 
     * 1 2 2 2 3
     * ^ ^
     * l r
     * 
     * 1 2 2 2 3 => 1 2 3 2 3
     * ^ ^ ^ ^
     * l r l r
     */
    public static int removeDuplicate(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int left = 0;
        for (int right = 1; right < nums.length; right++) {
            if (nums[left] != nums[right]) {
                left++;
                nums[left] = nums[right];
            }
        }

        return left + 1;
    }

    // Is Palindrome
    public static boolean isPalindrome(String s) {

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {

            // Skip the char that is not letter or digit form left
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }

            // Skip the char that is not letter or digit from right
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right++;
            }

            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    // Two sum
    public static int[] twoSum(int[] nums, int target) {

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int currentSum = nums[left] + nums[right];

            if (currentSum == target) {
                return new int[] { left + 1, right + 1 };
            }

            if (currentSum < target) {
                left++;
            } else {
                right--;
            }
        }

        return new int[] {};
    }

    // Three sum
    /**
     * -1 0 1 2 -1 -4 <= Original
     * -4 -1 -1 0 1 2 <= Sorted
     * 0 1 2 3 4 5 <= Index
     * i=0, left=i+1=1, right=size=5
     *
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {

            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;

                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }
                }
            }
        }

        return res;
    }

    // Max Area
    public static int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;

        while (left < right) {
            int minHeight = Math.min(height[left], height[right]);
            int width = right - left;
            int area = width * minHeight;
            maxArea = Math.max(maxArea, area);

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    // Trapping Rain Water
    public static int trap(int[] height) {
        int totalWater = 0;

        int left = 0;
        int right = height.length - 1;
        int maxLeft = height[left];
        int maxRight = height[right];

        while (left < right) {
            if (maxLeft < maxRight) {
                left++;
                maxLeft = Math.max(maxLeft, height[left]);
                totalWater += maxLeft - height[left];
            } else {
                right--;
                maxRight = Math.max(maxRight, height[right]);
                totalWater += maxRight - height[right];
            }
        }

        return totalWater;
    }

}