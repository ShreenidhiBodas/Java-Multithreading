package org.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LiveLockExample {
    private final Lock lock1 = new ReentrantLock(true);
    private final Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        LiveLockExample livelock = new LiveLockExample();

        new Thread(livelock::worker1, "worker1").start();
        new Thread(livelock::worker2, "worker2").start();
    }

    public void worker1() {
        while(true) {
            try {
                lock1.tryLock(50, TimeUnit.MILLISECONDS); // acquiring with timeout throws InteruptedException
                System.out.println("Worker1 acquired lock1");
            } catch (InterruptedException e) {
                System.out.println("Worker1 cannot acquire lock1");
                e.printStackTrace();;
            }

            System.out.println("Worker1 trying to acquire lock2");

            if (lock2.tryLock()) {
                System.out.println("Worker1 acquired lock on lock2");
                lock2.unlock();
            } else {
                System.out.println("Worker1 cannot acquire lock on lock2. retrying...");
                continue;
            }
            break;
        }
        lock1.unlock();
        lock2.unlock();
    }

    public void worker2() {
        while(true) {
            try {
                lock2.tryLock(50, TimeUnit.MILLISECONDS); // acquiring with timeout throws InteruptedException
                System.out.println("Worker2 acquired lock2");
            } catch (InterruptedException e) {
                System.out.println("Worker2 cannot acquire lock2");
                e.printStackTrace();;
            }

            System.out.println("Worker2 trying to acquire lock1...");

            if (lock1.tryLock()) {
                System.out.println("Worker2 acquired lock on lock1");
                lock1.unlock();
            } else {
                System.out.println("Worker2 cannot acquire lock on lock1. retrying...");
                continue;
            }
            break;
        }
        lock1.unlock();
        lock2.unlock();
    }
}
