package org.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

class ProducerWorker implements Runnable {
    private BlockingQueue<Integer> queue;

    public ProducerWorker(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int counter = 0;
        while(true) {
            try {
                queue.put(counter);
                System.out.println("Putting items in the queue: " + counter);
                counter++;
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ConsumerWorker implements Runnable {
    private BlockingQueue<Integer> queue;

    public ConsumerWorker(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                int counter = queue.take();
                System.out.println("Taking items from the queue: " + counter);
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class BlockingQueueExample {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
        Thread t1 = new Thread(new ProducerWorker(queue));
        Thread t2 = new Thread(new ConsumerWorker(queue));

        t1.start();
        t2.start();
    }
}
