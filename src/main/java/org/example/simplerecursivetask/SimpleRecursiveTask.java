package org.example.simplerecursivetask;

import java.util.concurrent.RecursiveTask;

public class SimpleRecursiveTask extends RecursiveTask<Integer> {

    private int num;

    public SimpleRecursiveTask(int num) {
        this.num = num;
    }
    @Override
    protected Integer compute() {
         if (num > 100) {
             System.out.println("Problem too large. Splitting into sub problems... " + num);
             // problem is large. split into two
             SimpleRecursiveTask task1 = new SimpleRecursiveTask(num / 2);
             SimpleRecursiveTask task2 = new SimpleRecursiveTask(num / 2);

             // queue to the fork join pool
             task1.fork();
             task2.fork();

             int subSolution = 0;
             // accumulate the subsolution using join
             subSolution += task1.join();
             subSolution += task2.join();

             return subSolution;
         } else {
             System.out.println("Problem too small. sequential approach can be followed " + num);
             // problem too small. sequential approach can be followed
             return num * 2;
         }
    }
}
