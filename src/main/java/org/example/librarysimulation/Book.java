package org.example.librarysimulation;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Book {
    private int id;
    private Lock lock;

    public  Book(int id) {
        this.id = id;
        this.lock = new ReentrantLock();
    }

    public void read(Student student) throws InterruptedException {
        if (lock.tryLock()) {
            System.out.println(student +  " is reading " + this);
            Thread.sleep(2000);
            lock.unlock();
            System.out.println(student + " finished reading " + this);
        } else {
            System.out.println(student + " cannot read " + this + " now as it is read by another student");
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id + '}';
    }
}
