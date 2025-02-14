package org.example.structuredconcurrency;

import java.time.Duration;
import java.util.concurrent.Callable;

public class LongProcess implements Callable<String> {
    private int sleepTime;
    private String result;


    public LongProcess(int sleepTime, String result) {
        this.sleepTime = sleepTime;
        this.result = result;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(Duration.ofSeconds(sleepTime));
        return result;
    }
}
