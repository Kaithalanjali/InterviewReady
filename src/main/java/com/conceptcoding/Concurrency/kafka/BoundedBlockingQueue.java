package com.conceptcoding.Concurrency.kafka;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

public class BoundedBlockingQueue {
    private Semaphore full;
    private Semaphore empty;

    private ConcurrentLinkedDeque<Integer> deque;

    public BoundedBlockingQueue(int capacity) {
        this.full = new Semaphore(0);
        this.empty = new Semaphore(capacity);
        this.deque = new ConcurrentLinkedDeque<>();
    }

    public void enqueue(int element) throws InterruptedException {
        empty.acquire();
        deque.offerLast(element);
        full.release();
    }

    public int dequeue() throws InterruptedException {
        full.acquire();
        int element = deque.pollFirst();
        empty.release();
        return element;
    }

    public int size() {
        return deque.size();
    }
}
