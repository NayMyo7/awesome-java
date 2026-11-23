package dev.naymyohtet.grokkingalgorithm;

public class BinarySearch {
    public static void main(String[] args) {
        System.out.println("Welcome from Algorithm!");

        int[] array = { 1, 5, 7, 9, 12, 14, 16, 18, 20 };
        int target = 7;
        System.out.println("Binary Search: " + binarySearch(array, target));
    }

    public static int binarySearch(int[] array, int target) {
        int left = 0;
        int right = array.length;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) {
                return mid;
            }

            if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;
    }
}
