package dev.naymyohtet.practice.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaStreamPractice {
    public static void main(String[] args) {
        System.out.println("Java Stream Practice...");

        // List all even number from a list
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        List<Integer> even1 = integers.stream()
                .filter(n -> n % 2 == 0)
                .toList();
        System.out.println("Event list 1: " + even1);

        List<Integer> even2 = integers.stream()
                .collect(Collectors.filtering(n -> n % 2 == 0, Collectors.toList()));
        System.out.println("Even list 2: " + even2);
    }
}