package dev.naymyohtet.grokkingalgorithm;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SlidingWindow {
    public static void main(String[] args) {
        System.out.println("Sliding Window Problems");

        // Max Sum
        int[] arr = { 2, 1, 5, 1, 3, 2 };
        System.out.println("Max sum: " + maximumSumSubarray(arr, 3));

        // Max Profit
        int[] prices = { 7, 1, 5, 3, 6, 4 };
        System.out.println("Max profit: " + maxProfit(prices));

        // Max Length
        String s = "abcabcbb";
        System.out.println("Max length: " + longestSubstring(s));

        // Max Count
        String s1 = "AABABBA";
        System.out.println("Max count: " + characterReplacement(s1, 1));

        // Permutation in String
        String s2 = "ab", s4 = "eidbcaooo";
        System.out.println("Permutation in string: " + checkInclusion(s2, s4));

        // Minimum window substring
        String s5 = "ADOBECODEBANC", t = "ABC";
        System.out.println("Minimum window substring: " + minWindow(s5, t));

        // Sliding window maximum
        int[] numbers2 = new int[] { 1, 3, -1, -3, 5, 3, 6, 7 };
        System.out.println("Sliding window maximum: " + Arrays.toString(maxSlidingWindow(numbers2, 3)));

    }

    // Maximum sum sub array of size K
    public static int maximumSumSubarray(int[] nums, int k) {
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }

        int maxSum = windowSum;
        for (int right = k; right < nums.length; right++) {
            windowSum += nums[right] - nums[right - k];
            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }

    // Best time to buy and sell stock
    public static int maxProfit(int[] prices) {
        int maxProfit = 0;
        int left = 0;
        int right = 1;

        while (right < prices.length) {

            if (prices[left] < prices[right]) {
                int currentProfit = prices[right] - prices[left];
                maxProfit = Math.max(maxProfit, currentProfit);
            } else {
                left = right;
            }

            right++;
        }

        return maxProfit;
    }

    // Longest substring without repeating character
    public static int longestSubstring(String s) {
        int maxLength = 0;
        int left = 0;
        Set<Character> charSet = new HashSet<>();

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // Shrink the window by removing left char
            while (charSet.contains(c)) {
                charSet.remove(s.charAt(left));
                left++;
            }

            // Expand the window by adding new char
            charSet.add(c);

            // Calculate the window length
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;

    }

    // Character replacement
    public static int characterReplacement(String s, int k) {
        int maxCount = 0;
        int maxLength = 0;
        int left = 0;
        int[] count = new int[26];

        for (int right = 0; right < s.length(); right++) {
            // Increase the frequency of new char
            count[s.charAt(right) - 'A']++;
            // Update the char count of current window
            maxCount = Math.max(maxCount, count[s.charAt(right) - 'A']);

            // Check the window size against k
            int windowSize = right - left + 1;
            if (windowSize - maxCount > k) {
                // Shrink the window to meet k changes
                count[s.charAt(left) - 'A']--;
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);

        }

        return maxLength;
    }

    // Permutation in String
    public static boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }

        int[] s1Count = new int[26];
        int[] s2Count = new int[26];

        for (int i = 0; i < s1.length(); i++) {
            s1Count[s1.charAt(i) - 'a']++;
            s2Count[s2.charAt(i) - 'a']++;
        }

        if (Arrays.equals(s1Count, s2Count)) {
            return true;
        }

        for (int right = s1.length(); right < s2.length(); right++) {
            s2Count[s2.charAt(right) - 'a']++;
            s2Count[s2.charAt(right - s1.length()) - 'a']--;

            if (Arrays.equals(s1Count, s2Count)) {
                return true;
            }
        }

        return false;
    }

    // Minimum window substring
    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }

        // Frequency of character in t
        Map<Character, Integer> tMap = new HashMap<>();
        for (Character c : t.toCharArray()) {
            tMap.put(c, tMap.getOrDefault(c, 0) + 1);
        }

        Map<Character, Integer> windowMap = new HashMap<>();
        int left = 0;
        int need = tMap.size(); // Unique character in t
        int have = 0; // Unique character in window that matched

        int minLeft = 0;
        int minLen = Integer.MAX_VALUE;

        for (int right = 0; right < s.length(); right++) {
            Character c = s.charAt(right);

            // Add char to windowMap if include in t
            if (tMap.containsKey(c)) {
                windowMap.put(c, windowMap.getOrDefault(c, 0) + 1);
                if (tMap.get(c).equals(windowMap.get(c))) {
                    have++;
                }
            }

            while (have == need) {
                // Record the window size and min length
                int windowSize = right - left + 1;
                if (windowSize < minLen) {
                    minLen = windowSize;
                    minLeft = left;
                }

                // Remove left char from window if include in t
                Character leftChar = s.charAt(left);
                if (tMap.containsKey(leftChar)) {
                    if (tMap.get(leftChar).equals(windowMap.get(leftChar))) {
                        have--;
                    }
                    windowMap.put(leftChar, windowMap.get(leftChar) - 1);
                }

                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen);
    }

    // Sliding window maximum
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0 || k > nums.length) {
            return new int[0];
        }

        int n = nums.length;
        int[] result = new int[n - k + 1];

        // Stores indices; values at these index are strictly decreasing order
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // Remove the front index if it has slid out of the current window
            if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            // Pop the last element to maintain decreasing order if it is smaller or equal
            // current array element
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }

            // Add the current element's index to the back
            deque.offerLast(i);

            // Record the maximum once the first window is formed
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}
