package dev.naymyohtet.grokkingalgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HashTable {
    public static void main(String[] args) {
        System.out.println("Welcome to Hash Table");

        // Hash Map
        HashMap<String, Integer> fruits = new HashMap<>();
        fruits.put("Apple", 3);
        fruits.put("Banana", 5);
        fruits.put("Orange", 1);
        System.out.println("All fruit: " + fruits);

        System.out.println("Number of Orange: " + fruits.get("Orange"));

        System.out.println("Contain Banana: " + fruits.containsKey("Banana"));
        fruits.remove("Banana");
        System.out.println("After removing Banana: " + fruits);

        // Iterating map
        for (String key : fruits.keySet()) {
            System.out.println(key + "->" + fruits.get(key));
        }

        // Hash Set
        Set<String> set = new HashSet<>();
        set.add("Apple");
        set.add("Banana");
        set.add("Orange");
        set.add(null);
        set.add("Banana");
        System.out.println("All fruits: " + set);

        set.remove(null);
        System.out.println("After removing null: " + set);
        System.out.println("Contain Banana: " + set.contains("Banana"));
        System.out.println("Total number of fruits: " + set.size());

        // Has duplicate
        int[] nums1 = { 1, 3, 5, 3, 7, 9 };
        System.out.println("Array " + Arrays.toString(nums1) + " has duplicate: " + hasDuplicate(nums1));
        int[] nums2 = { 1, 2, 3, 4, 5 };
        System.out.println("Array " + Arrays.toString(nums2) + " has duplicate: " + hasDuplicate(nums2));

        // Is Anagram
        String s = "racecar";
        String t = "carrace";
        // Using static array
        System.out.println("Is Anagram1: " + isAnagram(s, t));
        // Using dynamic map
        System.out.println("Is Anagram2: " + isAnagram2(s, t));

        // Two Sum
        int[] nums = { 2, 4, 3, 1, 8, 7 };
        int target = 8;
        System.out.println("Two sum: " + Arrays.toString(twoSum(nums, target)));

        // Group Anagram
        String[] strs = { "eat", "tea", "tan", "ate", "nat", "bat" };
        System.out.println("Group anagram: " + groupAnagram(strs));

    }

    // Has Duplicate
    public static boolean hasDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int n : nums) {
            seen.add(n);
        }
        return seen.size() < nums.length;
    }

    // Is Anagram using static Arrays
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

    // Is Anagram using Dynamic Array with Map
    public static boolean isAnagram2(String s, String t) {
        if (s.length() != t.length())
            return false;

        Map<Character, Integer> count = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            count.merge(s.charAt(i), 1, Integer::sum); // Using merge function
            // count.merge(t.charAt(i), -1, Integer::sum);
            count.put(t.charAt(i), count.getOrDefault(t.charAt(i), 0) - 1); // Using put
        }

        for (Integer value : count.values()) {
            if (value != 0)
                return false;
        }

        return true;
    }

    // Two Sum
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (seen.containsKey(complement)) {
                return new int[] { seen.get(complement), i };
            }

            seen.put(nums[i], i);
        }

        return new int[] { -1, -1 };
    }

    // Group Anagram
    public static List<List<String>> groupAnagram(String[] strs) {

        Map<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            char[] hash = new char[26];
            for (char c : s.toCharArray()) {
                hash[c - 'a']++;
            }

            String key = new String(hash);

            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(s);
        }

        System.out.println("Map: " + map);

        return new ArrayList<>(map.values());
    }

    // Top K frequent elements
    public static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();

        // Count the frequency
        for (int num : nums) {
            count.merge(num, 1, Integer::sum);
        }

        // Build an array with frequency as index
        List<Integer>[] buckets = new List[nums.length + 1];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }

        // Extract frequency from put and put to array
        for (int key : count.keySet()) {
            buckets[count.get(key)].add(key);
        }

        int[] result = new int[k];
        int index = 0;
        for (int i = buckets.length - 1; i >= 0 && index < k; i--) {
            for (int num : buckets[i]) {
                result[index++] = num;
                if (index == k)
                    return result;
            }
        }

        return result;
    }

    // Is valid Sudoku
    public static boolean isValidSudoku(char[][] board) {

        Set<String> seen = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char current = board[i][j];
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

    // Longest consecutive
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        int longestSreak = 0;
        for (int num : set) {
            if (!set.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (set.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestSreak = Math.max(longestSreak, currentStreak);
            }
        }

        return longestSreak;
    }

}
