package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Work implements Runnable {
    private int id;

    public Work() {}

    public Work(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Work with ID: " + id + " is running on thread: " + Thread.currentThread().getId());
        long duration = (long) (Math.random() * 5);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
public class FixedThreadPoolExample {
    public static void main(String[] args) {
        ExecutorService ex = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 100; i++) {
            ex.execute(new Work(i));
        }

        // prevent scheduling new tasks
        ex.shutdown();

        // shutdown currently running tasks
        try {
            if (!ex.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                // ex.shutdownNow(); to terminate immediately. In this case, there might be a case
                // where the executorservice is idle for more than a second because the delay for
                // each Work is somewhere in the vicinity of 5 sec
                // If we comment the shutDownNow(), executor service will execute all the available tasks
                // and shutdown
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            ex.shutdownNow();
        }
    }
}
