package com.conceptcoding.Concurrency;

import java.util.concurrent.Semaphore;

class DiningPhilosophers {

    private Semaphore semaphore;
    private Semaphore[] forkSemaphore;

    public DiningPhilosophers() {
        semaphore = new Semaphore(4);

        forkSemaphore = new Semaphore[5];
        for (int i = 0; i < 5; i++) {
            forkSemaphore[i] = new Semaphore(1);
        }
    }

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {

        int left = philosopher;
        int right = (philosopher + 1) % 5;

        semaphore.acquire();

        Semaphore leftForkSemaphore;
        Semaphore rightForkSemaphore;

        leftForkSemaphore = forkSemaphore[left];
        rightForkSemaphore = forkSemaphore[right];
        leftForkSemaphore.acquire();
        rightForkSemaphore.acquire();

        pickLeftFork.run();
        pickRightFork.run();

        eat.run();

        putRightFork.run();

        putLeftFork.run();
        leftForkSemaphore.release();
        rightForkSemaphore.release();

        semaphore.release();
    }
}