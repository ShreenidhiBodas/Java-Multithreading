package org.example.simplerecursivetask;

import java.util.concurrent.ForkJoinPool;

public class App {
    public static void main(String[] args) {
        SimpleRecursiveTask task = new SimpleRecursiveTask(200);
        ForkJoinPool pool = new ForkJoinPool();
        System.out.println(task.invoke());

        RecursiveFibonacci fibonacci = new RecursiveFibonacci(8);
        System.out.println(fibonacci.invoke());
    }
}
