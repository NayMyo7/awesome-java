package dev.naymyohtet.grokkingalgorithm.quicksort;

public class LoopSum {
    public static void main(String[] args) {
        System.out.println(sum(new int[] { 1, 2, 3, 4, 5 }));
    }

    private static int sum(int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum;
    }
}
