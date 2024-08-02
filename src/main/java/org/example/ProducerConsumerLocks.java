package org.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LockWorker {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void produce() throws InterruptedException {
        lock.lock();
        System.out.println("Producer method...");
        // wait()
        condition.await();
        System.out.println("Again in the producer method...");
        lock.unlock();
    }

    public void conume() throws InterruptedException {
        // make sure that we start with the producer. producer should acquire the lock first
        Thread.sleep(2000);
        lock.lock();
        System.out.println("Consumer method...");
        Thread.sleep(3000);
        // notify()
        condition.signal();
        lock.unlock();
    }

}

public class ProducerConsumerLocks {

    public static void main(String[] args) {
        LockWorker w = new LockWorker();
        Thread t1 = new Thread(() -> {
            try {
                w.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                w.conume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
