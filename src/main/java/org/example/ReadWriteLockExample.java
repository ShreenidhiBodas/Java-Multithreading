package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
  ReadWriteLock rwLock;

  private void simulateWork(String workType, long delay) {
    try {
      System.out.println(Thread.currentThread().getName() + " is simulating " + workType + " work.");
      Thread.sleep(delay);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt(); // reset the interrupt flag
      System.out.println(Thread.currentThread().getName() + " was interrupted");
    }
  }

  public void writeValue(String taskName) {
    rwLock.writeLock().lock();
    try {
      System.out.println("Task " + taskName + "(write): Acquired write lock");
      simulateWork("write", 2000);
    } finally {
      System.out.println("Task " + taskName + " released write lock");
      rwLock.writeLock().unlock();
    }
  }

  public void readValue(String taskName) {
    rwLock.readLock().lock();
    try {
      System.out.println("Task " + taskName + "(read): Acquired read lock");
      simulateWork("read", 1000);
    } finally {
      System.out.println("Task " + taskName + " released read lock");
      rwLock.readLock().unlock();
    }
  }

  public ReadWriteLockExample() {
    this.rwLock = new ReentrantReadWriteLock();
  }

  public static void main(String[] args) {
    ReadWriteLockExample example = new ReadWriteLockExample();
    ExecutorService executor = Executors.newFixedThreadPool(4);

    // submitting 3 readers. will acquire read lock as there are no threads
    // that have acquired write lock yet.
    executor.submit(() -> example.readValue("Reader-1"));
    executor.submit(() -> example.readValue("Reader-2"));
    executor.submit(() -> example.readValue("Reader-3"));

    // submit a writer
    // this will be blocked until all the readers release their read locks
    executor.submit(() -> example.writeValue("Writer-1"));

    // submitting 2 more readers. these will wait for the writer to finish
    executor.submit(() -> example.readValue("Reader-4"));
    executor.submit(() -> example.readValue("Reader-5"));

    // submit another writer
    executor.submit(() -> example.writeValue("Writer-2"));

    // final reader
    executor.submit(() -> example.readValue("Reader-6"));

    executor.shutdown();
  }
}
