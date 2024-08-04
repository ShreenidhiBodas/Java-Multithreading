package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariables {
    public static int counter = 0;

    public static int counter2 = 0;

    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void incrementWithoutSynchronized() {
        for (int i = 0; i < 10000; i++) {
            counter++;
        }
    }

    public static synchronized void synchronizedIncrement() {
        for (int i = 0; i < 10000; i++) {
            counter2++;
        }
    }

    public static void incrementAtomicWithoutSynchronized() {
        for (int i = 0; i < 10000; i++) {
            atomicInteger.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(AtomicVariables::incrementWithoutSynchronized);
        Thread t2 = new Thread(AtomicVariables::incrementWithoutSynchronized);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Counter after incrementing without synchronized:: " + counter);
        System.out.println("Above counter increment would be inconsistent for some test runs");

        Thread t3 = new Thread(AtomicVariables::synchronizedIncrement);
        Thread t4 = new Thread(AtomicVariables::synchronizedIncrement);

        t3.start();
        t4.start();

        try {
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Synchronized increment for counter2:: " + counter2);

        Thread t5 = new Thread(AtomicVariables::incrementAtomicWithoutSynchronized);
        Thread t6 = new Thread(AtomicVariables::incrementAtomicWithoutSynchronized);

        t5.start();
        t6.start();

        try {
            t5.join();
            t6.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Increment for the AtomicInteger object without a synchronized block:: " + atomicInteger.get());
    }
}
