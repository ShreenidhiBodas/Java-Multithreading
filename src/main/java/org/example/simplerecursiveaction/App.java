package org.example.simplerecursiveaction;

import java.util.concurrent.ForkJoinPool;

public class App {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        SimpleRecursiveAction action = new SimpleRecursiveAction(500);
        action.invoke();

        int[] numbers = new int[10];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i;
        }

        RecursivePrintArray printAction = new RecursivePrintArray(numbers);
        printAction.invoke();
    }
}
