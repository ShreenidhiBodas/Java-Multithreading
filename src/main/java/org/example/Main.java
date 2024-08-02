package org.example;

import javax.swing.table.JTableHeader;

class Runner1 implements Runnable {

    public void run() {
        for (int i=0;i<10;i++) {
            System.out.println("Runner1: " + i);
        }
    }
    public void execute() {
        for (int i=0;i<10;i++) {
            System.out.println("Runner1: " + i);
        }
    }
}

class Runner2 implements Runnable {
    @Override
    public void run() {
        for (int i=0;i<10;i++) {
            System.out.println("Runner2: " + i);
        }
    }

    public void execute() {
        for (int i=0;i<10;i++) {
            System.out.println("Runner2: " + i);
        }
    }
}

class ExtendedThread1 extends Thread {
    public void run() {
        for (int i=0;i<10;i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("ExtendedThread1: " + i);
        }
    }
}

class ExtendedThread2 extends Thread {
    public void run() {
        for (int i=0;i<10;i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("ExtendedThread2: " + i);
        }
    }
}
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

//        Thread t1 = new Thread(new Runner1());
//        Thread t2 = new Thread(new Runner2());

//        t1.start();
//        t2.start();

        Thread thread3 = new ExtendedThread1();
        Thread thread4 = new ExtendedThread2();

        thread3.start();
        thread4.start();

        try {
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Finished executing all threads");
    }
}