package org.example.virtual;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadDemo {
    public static void createVirtualThreads () throws InterruptedException {
        // Approach 1 to create virtual threads
        // create builder
        /*
        *var builder = Thread.ofVirtual().name("virtual-", 0);

        Thread t1 = builder.start(new VirtualTask());
        Thread t2 = builder.start(new VirtualTask());

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        * */

        // Approach 2. Create a factory
        var factory = Thread.ofVirtual().name("virtual-", 0).factory();

        Thread t1 = factory.newThread(new VirtualTask());
        Thread t2 = factory.newThread(new VirtualTask());

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    public static void main(String[] args) throws InterruptedException {
        // Basic Virtual Threads
        createVirtualThreads();

        // Create Virtual Threads using ExecutorService
        try (var service = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10000; i++) {
                service.submit(() -> {
                    try {
                        System.out.println("Thread " + Thread.currentThread());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
