package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SynchronizedCollections {

    public static void unsynchronizedArrayList() {
        List<Integer> nums = new ArrayList<>();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                nums.add(i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                nums.add(i);
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ArrayList size: " + nums.size());
    }

    public static void synchronizedWithCollectionArrayList() {
        List<Integer> nums = Collections.synchronizedList(new ArrayList<>());

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                nums.add(i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                nums.add(i);
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ArrayList size: " + nums.size());
    }
    public static void main(String[] args) {
        SynchronizedCollections.unsynchronizedArrayList();
        SynchronizedCollections.synchronizedWithCollectionArrayList();
    }
}
