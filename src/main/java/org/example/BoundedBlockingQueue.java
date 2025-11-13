package org.example;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

public class BoundedBlockingQueue<T> {
  private final ConcurrentLinkedDeque<T> deque;
  private final int maxCapacity;
  private final Semaphore writer, reader;

  public BoundedBlockingQueue(int maxCapacity) {
    this.maxCapacity = maxCapacity;
    this.deque = new ConcurrentLinkedDeque<>();
    reader = new Semaphore(0);
    writer = new Semaphore(this.maxCapacity);
  }

  public void enqueue(T value) throws InterruptedException {
    // acquire a writer permit
    writer.acquire();
    
    // enqueue to the front
    // instead of locking the entire underlying NON-THREAD-SAFE data structure,
    // we are using a thread-safe data structure
    deque.offerFirst(value);

    // as we have produced an item, consumers are ready to consume it
    reader.release(1);
  }

  public T dequeue() throws InterruptedException {
    T result = null;
    // acquire a reader permit
    reader.acquire();

    // poll last
    result = deque.pollLast();

    // as we have removed an element from the queue, a producer can produce
    writer.release(1);
    return result;
  }

  public int size() throws InterruptedException {
    return deque.size();
  }
}
