package dev.naymyohtet.grokkingalgorithm.quicksort;

import java.util.Arrays;

public class ResursiveMax {
    public static void main(String[] args) {
        System.out.println(max(new int[] { 1, 25, 6, 9, 12, 30 }));
    }

    private static int max(int[] list) {
        if (list.length == 2) {
            return list[0] > list[1] ? list[0] : list[1];
        }

        int subMax = max(Arrays.copyOfRange(list, 1, list.length));
        return list[0] > subMax ? list[0] : subMax;
    }
}
