package org.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CountDownWorker implements Runnable {
    private int id;
    private final CountDownLatch latch;

    CountDownWorker(int id, CountDownLatch latch) {
        this.id = id;
        this.latch = latch;
    }

    @Override
    public void run() {
        doWork();
        latch.countDown();
    }

    private void doWork() {
        try {
            System.out.println("Thread with ID: " + this.id + " starts working..." + "Count for the latch: " + this.latch.getCount());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Latch {

    public static void work() {
        CountDownLatch latch = new CountDownLatch(5);
        ExecutorService service = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 5; i++) {
            service.execute(new CountDownWorker(i, latch));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All the 5 threads are complete now...");
        service.shutdown();
    }
    public static void main(String[] args) {
        new Thread(Latch::work, "worker-thread").start();
        System.out.println("Some other operation in the main thread while the worker thread will wait for the latch to complete");
    }
}
