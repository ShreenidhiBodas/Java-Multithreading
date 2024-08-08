package org.example;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class MapProducerWorker implements Runnable {

    ConcurrentMap<String, Integer> map;

    public MapProducerWorker(ConcurrentMap<String, Integer> map) {
        this.map = map;
    }

    @Override
    public void run() {
        try {
            map.put("A", 12);
            Thread.sleep(1000);
            map.put("C", 120);
            Thread.sleep(1000);
            map.put("Z", 25);
            map.put("M", 20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MapConsumerWorker implements Runnable {

    ConcurrentMap<String, Integer> map;

    public MapConsumerWorker(ConcurrentMap<String, Integer> map) {
        this.map = map;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println(map.get("A"));
            Thread.sleep(1000);
            System.out.println(map.get("C"));
            Thread.sleep(1000);
            System.out.println(map.get("Z"));
            System.out.println(map.get("M"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ConcurrentMapExample {
    public static void main(String[] args) {
        ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();

        new Thread(new MapProducerWorker(map)).start();
        new Thread(new MapConsumerWorker(map)).start();

    }
}
