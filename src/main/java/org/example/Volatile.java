package org.example;

class VolatileWorker implements Runnable{

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }


    // will be stored in main memory
    private volatile boolean terminated;

    @Override
    public void run() {
        while (!terminated) {
            System.out.println("VolatileWorker is running...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Volatile {
    public static void main(String[] args) {
        VolatileWorker worker = new VolatileWorker();
        Thread t1 = new Thread(worker);
        t1.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        worker.setTerminated(true);
        System.out.println("Volatile worker terminated...");
    }
}
