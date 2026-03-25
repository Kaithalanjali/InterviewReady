package com.conceptcoding.uberlld;

import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/*
Design Hit Counter

Create a system that tracks the number of "clicks" received within the last 300 seconds (5 minutes). Each method will receive a timestamp parameter (represented in seconds).

You can assume that for recordClick(), timestamps are always provided in increasing order. However getRecentClicks() can be called for any timestamp. The earliest timestamp will always be 1.

Note that multiple clicks may be recorded at the same timestamp.
 */
public class ClickCounter {
    private Queue<Integer> queue;
    private ReentrantLock lock;

    public ClickCounter() {
        queue = new LinkedList<>();
        lock = new ReentrantLock();
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
    // Time Complexity: O(n) where n is total clicks in queue
    // Space Complexity: O(1)
    public int getRecentClicks(int timestamp) {
        lock.lock();
        try {
            // Remove clicks that are MORE than 300 seconds old
            // Keep clicks where: timestamp - 300 < clickTime <= timestamp
            while (!queue.isEmpty() && queue.peek() <= timestamp - 300) {
                queue.poll();
            }

            // Count only clicks that are NOT in the future
            // (clicks with clickTime <= timestamp)
            int count = 0;
            for (Integer clickTime : queue) {
                if (clickTime <= timestamp) {
                    count++;
                } else {
                    // Since recordClick() provides timestamps in increasing order,
                    // once we hit a future timestamp, all remaining are also future
                    break;
                }
            }
            return count;
        } finally {
            lock.unlock();
        }
    }
}

//So multiple clicks at same timestamp are aggregated.


/*
class ClickCounter {

    private int[] times;
    private AtomicInteger[] hits;

    public ClickCounter() {
        times = new int[300];
        hits = new AtomicInteger[300];
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
// rather than synchronizing the whole method we can use Atomic integer


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


