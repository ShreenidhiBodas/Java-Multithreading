package org.example.futures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo {
    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newFixedThreadPool(5);) {
            CompletableFuture.supplyAsync(() -> "Async programming")
                    .thenApply(String::toUpperCase)
                    .thenApply(s-> s + "is cool!")
                    .thenAccept(System.out::println);

            CompletableFuture.supplyAsync(() -> "Hello")
                    .thenCombine(
                            CompletableFuture.supplyAsync(() -> "World"),
                                    (s1, s2) -> s1 + " " + s2)
                    .thenApply(String::toUpperCase)
                    .thenAccept(System.out::println);

        }

        try (var service = Executors.newVirtualThreadPerTaskExecutor()) {
            CompletableFuture.supplyAsync(DBQuery::run)
                    .thenCombine(CompletableFuture.supplyAsync(RestQuery::run), (res1, res2) -> res1 + "\n" + res2)
                    .thenApply(String::toUpperCase)
                    .thenAccept(System.out::println);
        }
    }
}
