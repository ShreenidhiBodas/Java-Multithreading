package org.example;

public class Synchronization {
    public static int counter = 0;

    public static synchronized void increment() {
        counter++;
    }

    public static void process() {
        Thread t1 = new Thread(() -> {
            for (int i=0;i<100;i++) {
                counter++;
                // use increment() to make the outcome of deterministic
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i=0;i<100;i++) {
                counter++;
                // use increment() to make the outcome of deterministic
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
        System.out.println("Counter variable after thread execution: " + counter);
    }
    public static void main(String[] args) {
        process();
    }
}
