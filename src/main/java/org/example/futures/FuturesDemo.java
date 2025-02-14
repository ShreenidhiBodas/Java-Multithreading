package org.example.futures;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FuturesDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(ExecutorService service = Executors.newFixedThreadPool(5)) {
            Future<String> result = service.submit(new FutureTask());

            while(!result.isDone()) {
                System.out.println("Main thread is waiting for the result");
            }

            String res = result.get(); // blocks the main thread.
            System.out.println(res);
        }
    }
}
