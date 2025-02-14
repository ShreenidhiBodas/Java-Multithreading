package org.example.structuredconcurrency;

import java.time.Duration;
import java.util.concurrent.Callable;

public class LongProcessFail implements Callable<String> {
    private int sleepTime;
    private String result;
    private boolean fail;


    public LongProcessFail(int sleepTime, String result, boolean fail) {
        this.sleepTime = sleepTime;
        this.result = result;
        this.fail = fail;
    }

    @Override
    public String call() throws Exception {
        System.out.println("Starting  LongProcess " + result);
        Thread.sleep(Duration.ofSeconds(sleepTime));

        if (fail) {
            System.out.println("LongProcess " + result + " failed");
            throw new RuntimeException("Error in LongProcess"  + result);
        }
        System.out.println("LongProcess finished " + result);
        return result;
    }
}
