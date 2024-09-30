package org.example.simplerecursiveaction;

import java.util.concurrent.RecursiveAction;

public class SimpleRecursiveAction extends RecursiveAction {
    private int simulatedWork;

    public SimpleRecursiveAction(int simulatedWork) {
        this.simulatedWork = simulatedWork;
    }

    @Override
    protected void compute() {
        if (simulatedWork > 100) {
            // splitting the work into subtasks
            System.out.println("Parallel execution for work. Splitting into subtasks... " + simulatedWork);
            SimpleRecursiveAction action1 = new SimpleRecursiveAction(simulatedWork / 2);
            SimpleRecursiveAction action2 = new SimpleRecursiveAction(simulatedWork / 2);

            /**
             * Can use fork and join here like:
             *
             * action1.fork();
             * action2.fork();
             *
             * action1.join();
             * action2.join();
             * */

            // adds the tasks to the pool so that the ForkJoinPool will pick these up
            invokeAll(action1, action2);
        } else {
            System.out.println("Small work. Sequential execution possible... " + simulatedWork);
        }
    }
}
