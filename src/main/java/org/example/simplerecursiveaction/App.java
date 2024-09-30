package org.example.simplerecursiveaction;

import java.util.concurrent.ForkJoinPool;

public class App {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        SimpleRecursiveAction action = new SimpleRecursiveAction(500);
        action.invoke();
    }
}
