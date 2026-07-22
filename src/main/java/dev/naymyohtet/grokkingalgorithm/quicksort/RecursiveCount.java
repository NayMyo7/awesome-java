package dev.naymyohtet.grokkingalgorithm.quicksort;

import java.util.Arrays;

public class RecursiveCount {
    public static void main(String[] args) {
        System.out.println(count(new int[] { 1, 2, 3, 4, 5 }));
    }

    private static int count(int[] list) {
        if (list.length == 0) {
            return 0;
        }

        return 1 + count(Arrays.copyOfRange(list, 1, list.length));
    }
}
