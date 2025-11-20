package dev.naymyohtet.practice.dynamicprogramming;

import java.util.HashMap;
import java.util.Map;

public class FibonacciMemorization {
    private static Map<Integer, Integer> memo = new HashMap<>();

    public static int fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        int result = fibonacci(n - 1) + fibonacci(n - 2);
        memo.put(n, result);
        return result;
    }

    public static void main(String[] args) {
        int n = 50;
        System.out.println(fibonacci(n));
    }
}
