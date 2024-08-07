package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class CallableProcessor implements Callable<String> {

    private int id;

    CallableProcessor(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(2000);
        return "Callable" + " id: " + id;
    }
}

public class CallableExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Future<String>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(executorService.submit(new CallableProcessor(i)));
        }

        for (Future<String> f : list) {
            try {
                System.out.println(f.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
    }
}
