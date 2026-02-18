package com.conceptcoding.Concurrency.Theory;

public class WaitNotifyExample {
    public static void main(String[] args) {
        SharedResource shared = new SharedResource();


        // Thread 1 (Waits)
        Thread t1 = new Thread(() -> shared.waitExample(), "Thread-1");

        // Thread 2 (Notifies after 2 seconds)
        Thread t2 = new Thread(() -> {
            try {
                Thread.sleep(2000); // Ensure Thread-1 goes to wait state
                shared.notifyExample();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread-2");


        t1.start();
        t2.start();
    }
}
