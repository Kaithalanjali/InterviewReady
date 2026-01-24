package com.conceptcoding.interviewquestions.kafka;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Kafka Interview Questions Demo");
        BoundedBlockingQueue queue = new BoundedBlockingQueue(5);
        queue.enqueue(2);
        queue.enqueue(1);
        System.out.println("Current Queue Size: " + queue.size());
        System.out.println("Dequeued Element: " + queue.dequeue());
        System.out.println("Current Queue Size: " + queue.size());
        System.out.println("Dequeued Element: " + queue.dequeue());
        System.out.println("Current Queue Size: " + queue.size());
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        System.out.println("Dequeued Element: " + queue.dequeue());
        System.out.println("Current Queue Size: " + queue.size());
    }
}
