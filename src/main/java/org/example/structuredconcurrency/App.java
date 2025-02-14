package org.example.structuredconcurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

public class App {
    public static void structuredScope() throws InterruptedException {
//         Start a scope
//         Will create virtual threads for tasks here
        try (var scope = new StructuredTaskScope<String>()) {
            var process1 = new LongProcess(3, "Process 1");
            var process2 = new LongProcess(7, "Process 2");
            System.out.println("Created 2 LongProcesses...");

            // submit the process for execution
            // new virtual thread will be created for each fork call
            StructuredTaskScope.Subtask<String> res1 = scope.fork(process1);
            StructuredTaskScope.Subtask<String> res2 = scope.fork(process2);
            System.out.println("Submitted process for execution...");

            // wait for the results
            scope.join();

            // get the results
            System.out.println(res1.get() + " - " + res2.get());
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        structuredScope();

        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var process1 = new LongProcessFail(3, "Process 1", true);
            var process2 = new LongProcessFail(7, "Process 2", false);
            System.out.println("Created 2 LongProcesses...");

            // submit the process for execution
            // new virtual thread will be created for each fork call
            StructuredTaskScope.Subtask<String> res1 = scope.fork(process1);
            StructuredTaskScope.Subtask<String> res2 = scope.fork(process2);
            System.out.println("Submitted process for execution...");

            // wait for the results
            scope.join();

            try {
                scope.throwIfFailed();
            } catch (ExecutionException e) {
                System.out.println("Some of the child threads threw an exception. Stopping all the other threads and the scope");
            }

            if (res1.state() == StructuredTaskScope.Subtask.State.SUCCESS) {
                System.out.println(res1.get());
            }

            if (res2.state() == StructuredTaskScope.Subtask.State.SUCCESS) {
                System.out.println(res2.get());
            }
        }
    }
}
