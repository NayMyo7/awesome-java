package dev.naymyohtet.grokkingalgorithm.quicksort;

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {

        int[] arr = new int[] { 1, 5, 9, 7, 8, 2 };
        System.out.println("Original array" + Arrays.toString(arr));
        quicksort(arr, 0, arr.length - 1);
        System.out.println("Sorted array" + Arrays.toString(arr));

    }

    private static void quicksort(int[] arr, int low, int high) {
        // Base case
        if (low >= high) {
            return;
        }

        // Recursive case
        // Call Lomuto partition method, return the pivot position
        int pivot = partition(arr, low, high);

        // Recursively call QuickSort on left size of the pivot
        quicksort(arr, low, pivot - 1);

        // Recursively call QuickSort on right size of the pivot
        quicksort(arr, pivot, high);

    }

    private static int partition(int[] arr, int low, int high) {
        // Set the pivot as the last element in the array
        int pivot = arr[high];

        // Initialize pointer i to low - 1
        int i = low - 1;

        // Iterate through the array from low to high
        for (int j = low; j < high; j++) {
            // If current element is less than or equal to pivot
            if (arr[j] <= pivot) {
                i++; // Increase pointer i
                swap(arr, i, j); // Swap arr[i] and arr[j]
            }
        }

        // Swap pivot to the correct position
        swap(arr, i + 1, high);

        // Return the index of pivot
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
