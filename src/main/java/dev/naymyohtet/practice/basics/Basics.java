package dev.naymyohtet.practice.basics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Basics {
    public static void main(String[] args) {
        System.out.println("Basic Java");
        System.out.println("Process 5: " + process(5));
        System.out.println("Process: " + process(Arrays.asList(1, 3, 5, 7, 9, 2, 4, 6, 8, 10), 3));
         System.out.println("Process: " + batchProcess(Arrays.asList(1, 3, 5, 7, 9, 2, 4, 6, 8, 10), 3));
    }

    // Sum the number from 1 to n using recursive
    public static int process(int n) {
        if (n == 1)
            return 1;
        return n + process(n - 1);
    }

    // Sum the list of number with chunk size
    public static List<Integer> process(List<Integer> numberList, int chunk) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < numberList.size();) {
            int sum = 0;
            for (int j = 0; j < chunk && i < numberList.size(); j++) {
                sum += numberList.get(i);
                i++;
            }
            result.add(sum);
        }

        return result;
    }

    // Sum the list of number with chunk size
    public static List<Integer> batchProcess(List<Integer> numberList, int chunk) {
        List<Integer> result = new ArrayList<>();
        int size = numberList.size();
        for (int i = 0; i < size; i += chunk) {
            int sum = 0;
            for (int j = i; j < i + chunk && j < size; j++) {
                sum += numberList.get(j);
            }
            result.add(sum);
        }
        return result;
    }

}