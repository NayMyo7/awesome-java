package dev.naymyohtet.grokkingalgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Revision {
    public static void main(String[] args) {
        System.out.println("Revision >> Arrays >> String >> HashSet >> HasMap");

        // ----------- Arrays/String -----------
        // Finding min and max numbers
        int[] nums = new int[] { 3, 1, 7, 9, 4 };
        System.out.println("Numbers: " + Arrays.toString(nums));
        int min = nums[0];
        int max = nums[0];

        for (int i = 1; i < nums.length; i++) {

            if (min > nums[i])
                min = nums[i];

            if (max < nums[i])
                max = nums[i];
        }
        System.out.println("Min: " + min + "\tMax: " + max);

        // Reverse string
        String str = "HELLO";
        StringBuilder sb = new StringBuilder(str);
        System.out.println("Reversed string: " + sb.reverse().toString());

        // Count character
        String s = "banana";
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        System.out.println("Character count: " + Arrays.toString(count));

        // Remove duplicate number
        int[] arr = new int[] { 1, 1, 2, 2, 3, 3, 3 };
        Set<Integer> set = new HashSet<>();

        for (int n : arr)
            set.add(n);

        System.out.println("Original numbers: " + Arrays.toString(arr));
        System.out.println("Removed duplicate: " + set);

        // ----------- Hash Table -----------
        // Contain duplicate using HashSet
        System.out.println("Contain duplicate: " + hasDuplicate(arr));

        // Anagram using arrays
        String m = "racecar";
        String n = "carrace";
        System.out.println("Is anagram: " + isAnagram2(m, n));

        // Two Sum using HashMap
        int[] numbers = new int[] { 2, 7, 11, 15 };
        int target = 9;
        System.out.println("Two sum: " + Arrays.toString(twoSum(numbers, target)));

        // Group Anagram
        String[] strs = new String[] { "eat", "tea", "tan", "ate", "nat", "bat" };
        System.out.println("Group Anagrams: " + groupAnagrams(strs));

        // Top K frequent
        int[] numbers2 = new int[] { 1, 1, 1, 2, 2, 3 };
        int k = 2;
        System.out.println("Top K frequent element: " + Arrays.toString(topKFrequent(numbers2, k)));

        // ----------- Two Pointers -----------
        // Reverse character array
        char[] chars = new char[] { 'h', 'e', 'l', 'l', 'o' };
        reverseString(chars);
        System.out.println("Reversed char array: " + Arrays.toString(chars));

        // Remove duplicate, count unique number
        int[] numbers3 = new int[] { 1, 1, 2, 2, 3 };
        System.out.println("Unique number: " + removeDuplicate(numbers3));

        // Palindrome
        String str2 = "A man, a plan, a canal: Panama";
        System.out.println("Is palindrome: " + isPalindrome(str2));

        // Two sum, two pointer
        System.out.println("Two sum: " + Arrays.toString(twoSum2(numbers, target)));

        // Three sum, two pointer
        int[] numbers4 = new int[] { -1, 0, 1, 2, -1, -4 };
        System.out.println("Three sum: " + threeSum(numbers4));

    }

    public static boolean hasDuplicate(int[] nums) {

        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            seen.add(num);
        }

        return seen.size() < nums.length;
    }

    public static boolean isAnagram(String s, String t) {

        if (s.length() != t.length())
            return false;

        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
            count[t.charAt(i) - 'a']--;
        }

        for (int c : count) {
            if (c != 0)
                return false;
        }

        return true;
    }

    // Anagram using HashMap
    public static boolean isAnagram2(String s, String t) {

        if (s.length() != t.length())
            return false;

        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.merge(s.charAt(i), 1, Integer::sum);
            map.put(t.charAt(i), map.getOrDefault(t.charAt(i), 0) - 1);
        }

        for (int count : map.values()) {
            if (count != 0)
                return false;
        }

        return true;
    }

    // Two Sum
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (map.containsKey(complement))
                return new int[] { map.get(complement), i };

            map.put(nums[i], i);

        }
        return new int[] {};
    }

    // Group Anagrams
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            // Create hash key
            char[] hash = new char[26];
            for (char c : s.toCharArray()) {
                hash[c - 'a']++;
            }

            String key = new String(hash);

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(s);

        }

        return new ArrayList<>(map.values());
    }

    // Top K frequent element
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freqMap = new HashMap<>();
        // Count frequencies
        for (int n : nums) {
            freqMap.merge(n, 1, Integer::sum);
        }

        // Bucket array where index => frequency, value => list of numbers with that
        // frequency
        List<Integer>[] buckets = new List[nums.length + 1];

        freqMap.forEach((num, freq) -> {
            if (buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }
            buckets[freq].add(num);
        });

        // Scan buckets from highest frequency to lowest
        int[] result = new int[k];
        int index = 0;

        for (int i = buckets.length - 1; i >= 1 && index < k; i--) {
            if (buckets[i] != null) {
                for (int num : buckets[i]) {
                    result[index++] = num;
                    if (index == k) {
                        return result;
                    }
                }
            }
        }

        return result;
    }

    // Valid Sudoku
    public static boolean isValidSudoku(int[][] board) {

        Set<String> seen = new HashSet<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int current = board[i][j];
                if (current != '.') {
                    if (!seen.add(current + " found in row " + i) ||
                            !seen.add(current + " found in col " + j) ||
                            !seen.add(current + " found in sub-box " + (i / 3) + "-" + (j / 3))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // Longest consective
    public static int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int n : nums) {
            set.add(n);
        }

        int longestStreak = 0;

        for (int n : set) {
            if (!set.contains(n - 1)) {
                int currentNum = n;
                int currentStreak = 0;

                while (set.contains(currentNum + 1)) {
                    currentStreak += 1;
                    currentNum += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }

    // Reverse String using two pointers
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

    // Remove duplicate
    public static int removeDuplicate(int[] nums) {
        if (nums.length == 0)
            return 0;

        int slow = 0;

        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[slow] != nums[fast]) {
                slow++;
                nums[slow] = nums[fast];
            }
        }

        return slow + 1;
    }

    // Is palindrome
    public static boolean isPalindrome(String str) {
        int left = 0;
        int right = str.length() - 1;

        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(str.charAt(left))) {
                left++;
            }

            while (left < right && !Character.isLetterOrDigit(str.charAt(right))) {
                right--;
            }

            if (Character.toLowerCase(str.charAt(left)) != Character.toLowerCase(str.charAt(right))) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    // Two sum, two pointers
    public static int[] twoSum2(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int sum = nums[left] + nums[right];

            if (sum == target)
                return new int[] { left, right };

            if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        return new int[] {};
    }

    // Three sum
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            int left = i + 1;
            int right = nums.length - 1;

            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];

                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;

                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }
                }
            }
        }

        return result;
    }
}
