package dev.naymyohtet.grokkingalgorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class StackMain {
    public static void main(String[] args) {
        System.out.println("Practicing stack problems");

        String str = "{()[]{}}";
        System.out.println("Is valid parenthesis: " + isValid(str));

        String[] tokens = { "4", "13", "5", "/", "+" };
        System.out.println("Ans: " + evalRPN(tokens));

        int[] temperatures = { 73, 74, 75, 71, 69, 72, 76, 73 };
        System.out.println("Ans: " + Arrays.toString(dailyTemperatures(temperatures)));

        int[] positions = { 10, 8, 0, 5, 3 };
        int[] speeds = { 2, 4, 1, 1, 3 };
        System.out.println("Total fleet: " + carFleet(12, positions, speeds));

        int[] heights = { 2, 1, 5, 6, 2, 3 };
        System.out.println("Largest rectangle in histogram: " + largestRectangleArea(heights));
    }

    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for (Character c : s.toCharArray()) {
            if (c.equals('(')) {
                stack.push(')');
            } else if (c.equals('{')) {
                stack.push('}');
            } else if (c.equals('[')) {
                stack.push(']');
            } else {
                if (stack.isEmpty() || !stack.pop().equals(c)) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();

        // Valid operators are '+', '-', '*', and '/'
        for (String token : tokens) {
            if (token.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (token.equals("-")) {
                int a = stack.pop();
                int b = stack.pop();
                stack.push(b - a);
            } else if (token.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (token.equals("/")) {
                int a = stack.pop();
                int b = stack.pop();
                stack.push(b / a);
            } else {
                stack.push(Integer.valueOf(token));
            }
        }

        return stack.pop();
    }

    public static int[] dailyTemperatures(int[] temperatures) {
        int[] answers = new int[temperatures.length];

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int prevIndex = stack.pop();
                answers[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }

        return answers;
    }

    public static int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;

        // Put together position and speed to reach the target
        double[][] cars = new double[n][2];
        for (int i = 0; i < n; i++) {
            cars[i][0] = position[i];
            cars[i][1] = (double) (target - position[i]) / speed[i];
        }

        // Sort cars by position
        Arrays.sort(cars, (a, b) -> Double.compare(a[0], b[0]));

        Stack<Double> stack = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            double time = cars[i][1];

            if (stack.isEmpty() || time > stack.peek()) {
                stack.push(time);
            }
        }

        return stack.size();
    }

    public static int largestRectangleArea(int[] heights) {
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i <= heights.length; i++) {
            int h = (i == heights.length) ? 0 : heights[i];

            while (!stack.isEmpty() && h < heights[stack.peek()]) {
                int heigh = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, width * heigh);
            }
            stack.push(i);
        }

        return maxArea;
    }

    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> nextGreaterMap = new HashMap<>();
        Stack<Integer> stack = new Stack<>();

        for (int num : nums2) {
            while (!stack.isEmpty() && num > stack.peek()) {
                nextGreaterMap.put(stack.pop(), num);
            }
            stack.push(num);
        }

        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = nextGreaterMap.getOrDefault(nums1[i], -1);
        }

        return result;
    }
}
