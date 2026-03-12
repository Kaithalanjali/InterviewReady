package com.conceptcoding.uberlld;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;


public class HitCounter {
    private Queue<Integer> queue;
    private ReentrantLock lock;

    public HitCounter() {
        queue = new LinkedList<>();
//        var timestamp = System.currentTimeMillis()  // current time in seconds // current time in seconds
    }

    // record a click
    public void recordClick(int timestamp) {
        lock.lock();
        try {
            queue.offer(timestamp);
        } finally {
            lock.unlock();
        }
    }

    // get clicks in last 300 seconds
    public int getRecentClicks(int timestamp) {
        lock.lock();
        try {
            while (!queue.isEmpty() && queue.peek() <= timestamp - 300) {
                queue.poll();
            }
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}

//So multiple clicks at same timestamp are aggregated.


/*
class ClickCounter {

    private int[] times;
    private int[] hits;

    public ClickCounter() {
        times = new int[300];
        hits = new int[300];
    }

    public synchronized void recordClick(int timestamp) {
        int index = timestamp % 300;

        if (times[index] != timestamp) { //imp
            times[index] = timestamp;
            hits[index] = 1;
        } else {
            hits[index]++;
        }
    }

    public synchronized int getRecentClicks(int timestamp) {
        int total = 0;

        for (int i = 0; i < 300; i++) {
            if (timestamp - times[i] < 300) {
                total += hits[i];
            }
        }

        return total;
    }
}
 */

//millions of clicks
/*
import java.util.concurrent.atomic.AtomicInteger;

public class ClickCounter {

    private static final int WINDOW = 300;
    private static final int SHARDS = 16;

    private final Shard[] shards;

    public ClickCounter() {
        shards = new Shard[SHARDS];
        for (int i = 0; i < SHARDS; i++) {
            shards[i] = new Shard();
        }
    }

    public void recordClick(int timestamp) {
        int shardIndex = (int)(Thread.currentThread().getId() % SHARDS);
        shards[shardIndex].recordClick(timestamp);
    }

    public int getRecentClicks(int timestamp) {
        int total = 0;

        for (Shard shard : shards) {
            total += shard.getRecentClicks(timestamp);
        }

        return total;
    }

    static class Shard {

        private int[] times = new int[WINDOW];
        private AtomicInteger[] hits = new AtomicInteger[WINDOW];

        public Shard() {
            for (int i = 0; i < WINDOW; i++) {
                hits[i] = new AtomicInteger(0);
            }
        }

        public void recordClick(int timestamp) {
            int index = timestamp % WINDOW;

            synchronized (this) {
                if (times[index] != timestamp) {
                    times[index] = timestamp;
                    hits[index].set(1);
                } else {
                    hits[index].incrementAndGet();
                }
            }
        }

        public int getRecentClicks(int timestamp) {
            int total = 0;

            for (int i = 0; i < WINDOW; i++) {
                if (timestamp - times[i] < WINDOW) {
                    total += hits[i].get();
                }
            }

            return total;
        }
    }
}
 */

//redis solution
/*
key = "clicks:" + timestamp
Redis INCR key
Redis EXPIRE key 300


currentTime = t

sum keys:
clicks:t
clicks:t-1
clicks:t-2
...
clicks:t-299


clicks:timestamp:shard1
clicks:timestamp:shard2
clicks:timestamp:shard3

sum(shard0 + shard1 + shard2 + shard3)

Users
  │
  ▼
CDN / Edge
  │
  ▼
Load Balancer
  │
  ▼
Stateless API Servers
  │
  ▼
Kafka (optional)
  │
  ▼
Redis Cluster (time bucket counters)
  │
  ▼
Analytics DB
 */


