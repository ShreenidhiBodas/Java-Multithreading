package org.example;

import java.util.concurrent.Exchanger;

class Exchanger1 implements Runnable {
    private int counter;
    Exchanger<Integer> exchanger;

    public Exchanger1(Exchanger<Integer> exchanger) {
        this.counter = 0;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        while (true) {
            counter++;
            System.out.println("Exchanger1 incremented counter: " + counter);

            try {
                counter = exchanger.exchange(counter);
                System.out.println("Exchanger1 got counter: " + counter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Exchanger2 implements Runnable {
    private int counter;
    Exchanger<Integer> exchanger;

    public Exchanger2(Exchanger<Integer> exchanger) {
        this.counter = 0;
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        while (true) {
            counter--;
            System.out.println("Exchanger2 decremented counter: " + counter);

            try {
                counter = exchanger.exchange(counter);
                System.out.println("Exchanger2 got counter: " + counter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ExchangerExample {
    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<>();

        new Thread(new Exchanger1(exchanger)).start();
        new Thread(new Exchanger2(exchanger)).start();
    }
}
