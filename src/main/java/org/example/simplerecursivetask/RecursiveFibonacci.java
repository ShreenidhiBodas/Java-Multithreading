package org.example.simplerecursivetask;

import java.util.concurrent.RecursiveTask;

public class RecursiveFibonacci extends RecursiveTask<Integer> {
    private int n;

    public RecursiveFibonacci(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 2) {
            return 1;
        } else {
            RecursiveFibonacci task1 = new RecursiveFibonacci(n - 1);
            RecursiveFibonacci task2 = new RecursiveFibonacci(n - 2);

            task1.fork();
            task2.fork();

            int sub = 0;
            sub += task1.join();
            sub += task2.join();

            return sub;
        }
    }
}
