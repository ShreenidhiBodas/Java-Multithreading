package org.example.diningphilosopher;

import java.util.Random;

public class Philosopher implements Runnable {
    private int id;
    private volatile boolean full;
    private Chopstick leftChopstick;
    private Chopstick rightChopstick;
    private Random random;
    private int eatingCounter;

    public Philosopher(int id, Chopstick left, Chopstick right) {
        this.id = id;
        this.leftChopstick = left;
        this.rightChopstick = right;
        this.random = new Random();
    }

    private void think() throws InterruptedException {
        System.out.println(this + " is thinking...");
        Thread.sleep(random.nextInt(1000));
    }

    private void eat() throws InterruptedException {
        System.out.println(this + " is eating...");
        eatingCounter++;
        Thread.sleep(random.nextInt(1000));
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    @Override
    public String toString() {
        return "Philosopher " + id;
    }

    @Override
    public void run() {
        try {
            while (!full) {
                think();

                // Pick up the left chopstick1
                if (leftChopstick.pickUp(this, State.LEFT)) {

                    // pick up the right chopstick
                    if (rightChopstick.pickUp(this, State.RIGHT)) {
                        eat();
                        rightChopstick.putDown(this, State.RIGHT);
                    }
                    leftChopstick.putDown(this, State.LEFT);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getEatingCounter() {
        return this.eatingCounter;
    }
}
