package org.example;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Task implements Runnable {
    private int id;

    public Task() {}

    public Task(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Task with ID: " + id + " is running on thread: " + Thread.currentThread().getName());
        long duration = (long) (Math.random() * 5);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}

public class SingleThreadExecutorExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 5; i++) {
            executor.execute(new Task(i));
        }

        // prevent scheduling new tasks
        executor.shutdown();

        // shutdown currently running tasks
        try {
            if (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
//                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executor.shutdownNow();
        }
    }
}
