package org.example;


class WorkerThread implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            System.out.println("");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Worker thread finished Execution after 3 seconds");
    }
}

class DaemonThread implements Runnable {

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Daemon Thread is running...");
        }
    }
}

public class WorkersAndDaemons {
    public static void main(String[] args) {
        Thread t1 = new Thread(new WorkerThread());
        Thread daemon = new Thread(new DaemonThread());
        daemon.setDaemon(true);

        daemon.start();
        t1.start();
    }
}
