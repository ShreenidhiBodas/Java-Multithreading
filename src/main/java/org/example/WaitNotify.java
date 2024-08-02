package org.example;

class Process {

    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Running the Produce method...");
            wait();
            System.out.println("Again in the produce method...");
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(1000);
        synchronized (this) {
            System.out.println("Consume method is executed.");
            notify();
        }
    }
}

public class WaitNotify {
    public static void main(String[] args) {
        Process p = new Process();
        Thread t1 = new Thread(() -> {
            try {
                p.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                p.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }
}
