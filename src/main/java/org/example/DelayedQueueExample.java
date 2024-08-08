package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

class DelayedWorker implements Delayed {
    private long duration;
    private String message;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DelayedWorker(long duration, String  message) {
        this.duration = System.currentTimeMillis() + duration;
        this.message = message;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed other) {
        if (duration < ((DelayedWorker) other).getDuration()) {
            return -1;
        } else if (duration > ((DelayedWorker) other).getDuration()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "DelayedWorker{" +
                "message='" + message + '\'' +
                '}';
    }
}

public class DelayedQueueExample {
    public static void main(String[] args) {
        BlockingQueue<DelayedWorker> queue = new DelayQueue<>();

        try {
            // Can be inserted by multiple threads
            queue.put(new DelayedWorker(2000, "First message"));
            queue.put(new DelayedWorker(10000, "Second message"));
            queue.put(new DelayedWorker(4500, "Third message"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(!queue.isEmpty()) {
            try {
                System.out.println("Queue message : " + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
