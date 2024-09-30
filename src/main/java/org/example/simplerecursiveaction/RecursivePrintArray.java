package org.example.simplerecursiveaction;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class RecursivePrintArray extends RecursiveAction {
    private int[] numbers;

    public RecursivePrintArray(int[] nums) {
        this.numbers = new int[nums.length];
        this.numbers = Arrays.copyOf(nums, nums.length);
    }

    @Override
    protected void compute() {
        if (numbers.length > 2) {
            // break into sub-problems
            RecursivePrintArray action1 = new RecursivePrintArray(Arrays.copyOfRange(numbers, 0, numbers.length / 2));
            RecursivePrintArray action2 = new RecursivePrintArray((Arrays.copyOfRange(numbers, numbers.length / 2, numbers.length)));

            invokeAll(action1, action2);
        } else {
            // problem small enough
            System.out.println(Arrays.toString(numbers));
        }
    }
}
