package org.example;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class StockMarketUpdater implements Runnable {

    @Override
    public void run() {
        System.out.println("Fetching data for tick...");
    }
}

public class ScheduledExecutorPool {
    public static void main(String[] args) {
        ScheduledExecutorService sxs = Executors.newScheduledThreadPool(1);
        sxs.scheduleAtFixedRate(new StockMarketUpdater(), 1000, 2000, TimeUnit.MILLISECONDS);

        sxs.shutdown();
    }
}
