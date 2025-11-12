package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;



enum Downloader {

    value1,
    value2,
    TEST_INSTANCE;


    private Semaphore semaphore = new Semaphore(3, true);

    public void download() {
        try {
            semaphore.acquire();
            downloadData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    private void downloadData() {
        try {
            System.out.println("Downloading data from the web...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Worker {
    private final Semaphore mutex = new Semaphore(1);

    public void accessCriticalSection(String threadName) {
        try {
            System.out.println("Thread " + threadName + " trying to acquire mutex");
            mutex.acquire();
            System.out.println("Thread: " + threadName + ": Mutex acquired.");
            Thread.sleep(2000); // simulating work
            System.out.println("Thread: " + threadName + " completed execution");
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " was interrupted");
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Thread " + threadName + " released mutex");
            mutex.release();
        }
    }
}

public class Semaphores {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 12; i++) {
            service.execute(Downloader.TEST_INSTANCE::download);
        }
        service.shutdown();

        // Using semaphores as mutex by setting the permit to 1
        // Worker w = new Worker();
        // Thread t1 = new Thread(() -> w.accessCriticalSection("Thread-1"));
        // Thread t2 = new Thread(() -> w.accessCriticalSection("Thread-2"));

        // t1.start();
        // t2.start();

        // try {
        //     t1.join();
        //     t2.join();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
    }
}
