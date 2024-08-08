package org.example;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class BarrierWorker implements Runnable {
    private int id;
    private Random random;
    CyclicBarrier barrier;

    public BarrierWorker(int id, CyclicBarrier barrier) {
        this.id = id;
        this.random = new Random();
        this.barrier = barrier;
    }

    @Override
    public void run() {
        doWork();
    }

    private void doWork() {
        System.out.println("Thread with ID: " + id + " starts work...");

        try {
            Thread.sleep(random.nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println("After await() for thread: " + id);
    }
}

public class CyclicBarrierExample {
    public static void work() {
        ExecutorService service = Executors.newFixedThreadPool(5);
        CyclicBarrier barrier = new CyclicBarrier(5, () -> {
            System.out.println("All tasks are finished");
        });

        for (int i = 0; i < 5; i++) {
            service.execute(new BarrierWorker(i, barrier));
        }

        service.shutdown();
    }

    public static void main(String[] args) {
        CyclicBarrierExample.work();
    }
}
