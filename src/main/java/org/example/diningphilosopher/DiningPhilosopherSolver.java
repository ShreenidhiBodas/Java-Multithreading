package org.example.diningphilosopher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiningPhilosopherSolver {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = null;
        Philosopher[] philosophers = null;
        Chopstick[] chopsticks = null;

        // Aim of the simulation is to avoid thread starvation -> using executorService
        // There are no deadlocks -> used tryLock
        try {
            philosophers = new Philosopher[Constants.NUMBER_OF_PHILOSOPHERS];
            chopsticks = new Chopstick[Constants.NUMBER_OF_CHOPSTICKS];

            for (int i = 0; i < Constants.NUMBER_OF_CHOPSTICKS; i++) {
                chopsticks[i] = new Chopstick(i);
            }

            service = Executors.newFixedThreadPool(Constants.NUMBER_OF_PHILOSOPHERS);

            for (int i = 0; i < Constants.NUMBER_OF_PHILOSOPHERS; i++) {
                philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i+1)%Constants.NUMBER_OF_CHOPSTICKS]);
                service.execute(philosophers[i]);
            }

            Thread.sleep(Constants.SIMULATION_RUNNING_TIME);

            for (Philosopher p : philosophers) {
                p.setFull(true);
            }
        } finally {
            service.shutdown();

            while (!service.isTerminated()) {
                Thread.sleep(1000);
            }

            for (Philosopher p : philosophers) {
                System.out.println(p + " eat # " + p.getEatingCounter() + " times");
            }
        }
    }
}
