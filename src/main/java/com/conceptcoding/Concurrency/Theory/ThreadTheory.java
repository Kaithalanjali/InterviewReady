package com.conceptcoding.Concurrency.Theory;

import java.util.concurrent.*;

public class ThreadTheory {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread(); // Create thread instance
        MyThread thread2 = new MyThread(); // Create another thread instance

        thread1.start();// Start the first thread
        thread2.start(); // Start the second thread

        MyRunnable runnable = new MyRunnable(); // Create runnable instance

        Thread thread3 = new Thread(runnable); // Create thread with runnable
        Thread thread4 = new Thread(runnable); // Create another thread with same runnable

        thread3.start(); // Start the first thread
        thread4.start(); // Start the second thread

        // Create ExecutorService with a fixed thread pool
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Create Callable instances
        Callable<String> callable1 = new MyCallable("Task 1");
        Callable<String> callable2 = new MyCallable("Task 2");

        try {
            // Submit Callable tasks to the executor and get Future objects
            Future<String> future1 = executor.submit(callable1);
            Future<String> future2 = executor.submit(callable2);
            //either execute now or keep it for future execution
            executor.execute(runnable);

            // Get results from Future objects
            System.out.println("Result from first task:");
            System.out.println(future1.get()); // Blocks until the task completes

            System.out.println("Result from second task:");
            System.out.println(future2.get()); // Blocks until the task completes

        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Task execution interrupted: " + e.getMessage());
        } finally {
            // Shutdown the executor
            executor.shutdown();
        }
    }

}
