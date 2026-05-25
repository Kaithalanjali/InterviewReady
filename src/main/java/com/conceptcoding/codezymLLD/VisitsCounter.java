package com.conceptcoding.codezymLLD;

import java.util.concurrent.atomic.AtomicInteger;
/*
6. Design a Hit Counter/Webpage Visits Counter - Multi-Threaded

Write code for low level design of a webpage visits counter.
There are n webpages in a website numbered 0 to n-1.
Hundreds of users visit webpages of this website simultaneously.
You have to record visit count for each page and return them when required.

Note :
- For Java, code will be tested in a MULTI-THREADED environment, so use thread safe data structures and handle synchronization properly.
- For Python, code will be tested in a single threaded environment
- There will be at max 1000 webpages

Your solution should implement below methods :

Method : init(int totalPages, Helper06 helper)
- totalPages is the 'n' we discussed above i.e. total number of webpages in the website.
- Use this method to initialize your instance variables
- use helper's methods for printing logs else logs will not be visible.

Method : incrementVisitCount(int pageIndex)
- increment visit count for webpage at pageIndex by 1.

Method : getVisitCount(int pageIndex)
- return total visit count for a given page

For the same pageIndex, incrementVisitCount() and getVisitCount() will never be called concurrently.
This is done to maintain correctness and eventual consistency in the system.

Example :
init(totalPages = 2, helper = helper)

incrementVisitCount(pageIndex = 0)
incrementVisitCount(pageIndex = 1)
incrementVisitCount(pageIndex = 1)
incrementVisitCount(pageIndex = 1)
incrementVisitCount(pageIndex = 0)

getVisitCount(pageIndex = 0) : returns 2
getVisitCount(pageIndex = 1) : returns 3
 */
/**
 * Thread-safe page visits counter using AtomicInteger for lock-free concurrency
 *
 * CONCURRENCY:
 * - Uses AtomicInteger for lock-free atomic operations
 * - Multiple threads can increment/read counts safely without explicit locks
 * - Compare-and-swap (CAS) operations ensure thread safety
 *
 * PERFORMANCE:
 * - O(1) time for all operations (increment, get)
 * - No blocking - better performance than synchronized in high-contention scenarios
 */
class VisitsCounter {

    private AtomicInteger[] visitCounts;

    /**
     * Initialize the counter with specified number of pages
     * Time Complexity: O(n) where n = totalPages - creates and initializes n AtomicInteger objects
     * Space Complexity: O(n) - array of n AtomicInteger references
     */
    public void init(int totalPages) {
        visitCounts = new AtomicInteger[totalPages];

        for (int i = 0; i < totalPages; i++) {
            visitCounts[i] = new AtomicInteger(0);
        }
    }

    /**
     * Atomically increment the visit count for a specific page
     * Time Complexity: O(1) - single atomic operation
     * Space Complexity: O(1) - no additional space used
     * Thread-safe: Uses AtomicInteger.incrementAndGet() which is lock-free and atomic
     */
    public void incrementVisitCount(int pageIndex) {
        visitCounts[pageIndex].incrementAndGet();
    }

    /**
     * Get the current visit count for a specific page
     * Time Complexity: O(1) - single atomic read
     * Space Complexity: O(1) - no additional space used
     * Thread-safe: AtomicInteger.get() provides volatile read semantics
     */
    public int getVisitCount(int pageIndex) {
        return visitCounts[pageIndex].get();
    }
}

