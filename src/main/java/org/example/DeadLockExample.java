package org.example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockExample {
    private final Lock lock1 = new ReentrantLock(true);
    private final Lock lock2 = new ReentrantLock(true);

    public static void main(String[] args) {
        DeadLockExample deadlock = new DeadLockExample();

        new Thread(deadlock::worker1, "thread1").start();
        new Thread(deadlock::worker2, "thread2").start();
    }

    public void worker1() {
        lock1.lock();
        System.out.println("Worker1 acquires lock1...");

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock2.lock(); // Trying to acquire lock on the second lock
        System.out.println("Worker1 acquires lock2...");

        lock1.unlock();
        lock2.unlock();
    }

    public void worker2() {
        lock2.lock();
        // lock1.lock(); // Avoid deadlock
        System.out.println("Worker2 acquires lock2...");

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock1.lock(); // Trying to acquire lock on the second lock
        // lock2.lock();
        System.out.println("Worker2 acquires lock1...");

        lock1.unlock();
        lock2.unlock();
    }

}
