package org.example;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class CustomBarrier {
  private final Semaphore mutex = new Semaphore(1);

  /**
   * Barrier is initialized to 0
   * This causes all the threads that try to acquire the barrier to be
   * blocked and go into the waiting state
   * When the last thread releases the barrier (with the count = remaining
   * threads)
   * then all the waiting threads will acquire the barrier thereby making it 0
   * again
   *
   */
  private final Semaphore barrier = new Semaphore(0);
  private int count;
  private int parties;

  public CustomBarrier(int count) {
    this.count = count;
    this.parties = count;
  }

  public void await() throws InterruptedException {
    // Each thread that calls await() will first acquire the mutex in order to
    // decrement the count
    mutex.acquire();
    count--;
    if (count == 0) {
      // this means that the last thread called the await().
      // we now need to release all the other waiting threads
      System.out.println(Thread.currentThread().getName() + " is the last thread. Opening the barrier...");
      barrier.release(parties - 1); // parties - 1 because this current thread is not waiting on the barrier.
      count = parties;
      mutex.release();
    } else {
      // an intermediate thread is calling await(). This thread should then wait for
      // all the other threads to finish.
      // to acheive this, it will wait on the barrier.
      mutex.release(); // need to do this first!! as we need to free the mutex for other threads to
                       // enter this critical section
      barrier.acquire();
    }
  }

  public static void main(String[] args) {
    final int NUM_OF_PARTIES = 5;
    CustomBarrier barrier = new CustomBarrier(NUM_OF_PARTIES);
    ExecutorService service = Executors.newFixedThreadPool(NUM_OF_PARTIES, new ThreadFactory() {
      int counter = 0;

      @Override
      public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "Thread-" + counter);
        counter++;
        return t;
      }
    });

    for (int i = 0; i < NUM_OF_PARTIES; i++) {
      service.submit(() -> {
        try {
          // Phase 1
          // All the threads should be able to run this phase parallely
          System.out.println(Thread.currentThread().getName() + " is executing Phase 1");
          Thread.sleep((long) Math.random() * 1000);
          System.out.println(Thread.currentThread().getName() + " completed Phase 1");

          barrier.await(); // will wait for all the threads to finish Phase 1.

          // Phase 2
          System.out.println(Thread.currentThread().getName() + " is executing Phase 2");
          Thread.sleep((long) Math.random() * 1000);
          System.out.println(Thread.currentThread().getName() + " completed Phase 2");

          barrier.await();

          // Phase 3
          System.out.println(Thread.currentThread().getName() + " is starting Phase 3");

        } catch (InterruptedException e) {
          System.out.println(Thread.currentThread().getName() + " was interrupted.");
          Thread.currentThread().interrupt(); // resetting the interrupt flag.
        }
      });
    }

    try {
      service.shutdown();
      service.awaitTermination(5, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      service.shutdownNow();
    }
  }
}
