package dev.naymyohtet.grokkingalgorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ArrayMinMax {
    public static void main(String[] args) {
        System.out.println("Welcome Min/Max");

        // Find Min/Max
        int[] arr = { 1, 4, 2, 6, 8, 9, 3, 5, 7 };
        int min = arr[0];
        int max = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max)
                max = arr[i];
            if (arr[i] < min)
                min = arr[i];
        }
        System.out.println("Min: " + min + "\tMax: " + max);

        // Reverse String
        String s = "hello";
        StringBuilder sb = new StringBuilder(s);
        System.out.println("Reverse String: " + sb.reverse().toString());

        // Count character
        s = "banana";
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        System.out.println("Character count: " + Arrays.toString(count));

        char b = 'b';
        System.out.println("Character Index: " + (b - 'a'));

        // Remove duplicate
        int[] nums = { 1, 2, 1, 3, 4, 2, 5 };
        Set<Integer> set = new HashSet<>();
        for (int n : nums)
            set.add(n);
        System.out.println("Numbers: " + set);
    }

}
