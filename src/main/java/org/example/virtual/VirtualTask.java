package org.example.virtual;

public class VirtualTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Starting thread " + Thread.currentThread().getName());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Ending thread " + Thread.currentThread().getName());
    }
}
