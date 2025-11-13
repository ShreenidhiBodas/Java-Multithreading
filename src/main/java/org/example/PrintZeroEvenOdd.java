package org.example;

import java.util.concurrent.Semaphore;

class ZeroEvenOdd {
  private volatile int counter = 1;
  int n;
  private Semaphore zero = new Semaphore(1);
  private Semaphore even = new Semaphore(0), odd = new Semaphore(0);

  public ZeroEvenOdd(int n) {
    this.n = n;
  }

  public void zero() {
    try {
      while (true) {
        // acquire zero lock to print zero
        zero.acquire();
        if (counter >= n) {
          odd.release(1);
          even.release(1);
          return;
        }
        System.out.print(0);
        if (counter % 2 == 1) {
          // if the next number to print is odd,
          // release the semaphore for the odd
          odd.release(1);
        } else {
          even.release(1);
        }
        
        // we cannot release the zero lock. this has to be released by the odd
        // or the even thread
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.out.print(Thread.currentThread().getName() + "(zero) was interrupted");
    }
  }

  public void odd() {
    try {
      while (true) {
        odd.acquire();
        if (counter >= n) {
          zero.release(1);
          return;
        }
        System.out.print(counter++);
        zero.release(1);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.out.print(Thread.currentThread().getName() + "(odd) was interrupted");
    }
  }

  public void even() {
    try {
      while (true) {
        even.acquire();
        if (counter >= n) {
          zero.release(1);
          return;
        }
        System.out.print(counter++);
        zero.release(1);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.out.print(Thread.currentThread().getName() + "(even) was interrupted");
    }
  }
}

public class PrintZeroEvenOdd {
  public static void main(String[] args) throws InterruptedException {
    ZeroEvenOdd runner = new ZeroEvenOdd(7);

    Thread t1 = new Thread(() -> runner.zero());
    Thread t2 = new Thread(() -> runner.odd());
    Thread t3 = new Thread(() -> runner.even());
    t1.start();
    t2.start();
    t3.start();
  }  
}
