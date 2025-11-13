package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;

public class PrintFizzBuzz {
  private final int n;
  private Semaphore number, fizz, buzz, fizzbuzz;

  public PrintFizzBuzz(int n) {
    this.n = n;
    number = new Semaphore(1);
    fizz = new Semaphore(0);
    buzz = new Semaphore(0);
    fizzbuzz = new Semaphore(0);
  }

  private void printFizz() {
    System.out.print("fizz ");
  }

  private void printBuzz() {
    System.out.print("buzz ");
  }

  private void printFizzBuzz() {
    System.out.print("fizzbuzz ");
  }

  private void printNumber(int n) {
    System.out.print(n + " ");
  }

  public void safeNumber() throws InterruptedException {
    // we want to loop till n starting with 1
    for (int i = 1; i <= n; i++) {
      number.acquire();
      if (i % 3 == 0 && i % 5 != 0) {
        // need to print fizz instead of i.
        // release the fizz permit
        fizz.release(1);
      } else if (i % 5 == 0 && i % 3 != 0) {
        // release buzz permit
        buzz.release(1);
      } else if (i % 3 == 0 && i % 5 == 0) {
        fizzbuzz.release(1);
      } else {
        // next number is to be printed
        // release the number permit
        printNumber(i);
        number.release();
      }
    }
  }

  public void safeFizz() throws InterruptedException {
    for (int i = 1; i <= n; i++) {
      if (i % 3 == 0 && i % 5 != 0) {
        fizz.acquire();
        printFizz();
        number.release();
      }
    }
  }

  public void safeBuzz() throws InterruptedException {
    for (int i = 1; i <= n; i++) {
      if (i % 5 == 0 && i % 3 != 0) {
        buzz.acquire();
        printBuzz();
        number.release();
      }
    }
  }

  public void safeFizzBuzz() throws InterruptedException {
    for (int i = 1; i <= n; i++) {
      if (i % 3 == 0 && i % 5 == 0) {
        fizzbuzz.acquire();
        printFizzBuzz();
        number.release();
      }
    }
  }

  public static void main(String[] args) {
    PrintFizzBuzz runner = new PrintFizzBuzz(15);

    ExecutorService executor = Executors.newFixedThreadPool(4, new ThreadFactory() {
      int count = 0;
      @Override
      public Thread newThread(Runnable r) {
        return new Thread(r, "Thread-" + count++);
      }
    });

    executor.submit(() -> {
      try {
        runner.safeNumber();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().getName() + " was interrupted");
      }
    });

    executor.submit(() -> {
      try {
        runner.safeFizz();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().getName() + " was interrupted");
      }
    });

    executor.submit(() -> {
      try {
        runner.safeBuzz();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().getName() + " was interrupted");
      }
    });

    executor.submit(() -> {
      try {
        runner.safeFizzBuzz();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().getName() + " was interrupted");
      }
    });

    executor.shutdown();
  }
  
}
