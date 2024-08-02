package org.example;

import java.util.ArrayList;
import java.util.List;

class Processor {
    private List<Integer> list = new ArrayList<>();
    private static final int UPPER_LIMIT = 5;
    private static final int LOWER_LIMIT = 0;

    private final Object lock = new Object();

    private int value = 0;

    public void producer() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == Processor.UPPER_LIMIT) {
                    System.out.println("Queue full. Waiting for consumer to consume...");
                    lock.wait();
                } else {
                    System.out.println("Adding value to list...");
                    list.add(++value);
                    lock.notify();
                }
                Thread.sleep(500);
            }
        }
    }

    public void consumer() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if (list.size() == Processor.LOWER_LIMIT) {
                    System.out.println("Queue empty. Waiting for producer to produce...");
                    lock.wait();
                } else {
                    System.out.println("Consuming value from list...");
                    list.remove(list.size() - 1);
                    lock.notify();

                    // Case: When the consumer thread is running.
                    // After consuming the item (in this case, decrementing the value),
                    // it calls the notify method again for the lock.
                    // We would think that this would wake the producer thread and it would start
                    // and the value would oscillate by a factor of 1.
                    // But, notify does not immediately gives back the control of the other thread
                    // This thread will consume all the items (value = 0)
                    // It, itself will go into the wait state
                    // Then, the previously called notify calls will wake the producer thread
                }
                Thread.sleep(500);
            }
        }
    }
}

public class ProducerConsumer {
    public static void main(String[] args) {
        Processor processor = new Processor();
        Thread t1 = new Thread(() -> {
            try {
                processor.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                processor.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
    }
}
