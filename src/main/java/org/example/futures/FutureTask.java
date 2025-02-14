package org.example.futures;

import java.util.concurrent.Callable;

public class FutureTask implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("Starting callable...");
        Thread.sleep(2000);
        System.out.println("Finishing callable...");
        return "Callable result";
    }
}
