package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

class PriorityProducerWorker implements Runnable {
    private PriorityBlockingQueue<String> queue;

    public PriorityProducerWorker(PriorityBlockingQueue<String> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        try {
            queue.put("B");
            queue.put("H");
            queue.put("F");
            Thread.sleep(2000);
            queue.put("A");
            Thread.sleep(2000);
            queue.put("Z");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class PriorityConsumerWorker implements Runnable {
    private PriorityBlockingQueue<String> queue;

    public PriorityConsumerWorker(PriorityBlockingQueue<String> queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        System.out.println("Taking items from queue...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!queue.isEmpty()) {
            try {
                System.out.println("Item: " + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class PriorityBlockingQueueExample {
    public static void main(String[] args) {
        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
        new Thread(new PriorityProducerWorker(queue), "producer-thread").start();
        new Thread(new PriorityConsumerWorker(queue), "consumer-thread").start();
    }
}
