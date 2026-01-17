package com.conceptcoding.interviewquestions.LRU;

public class RunLRUCache {
    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(3);

        lruCache.put(1, 1);
        lruCache.put(2, 2);
        lruCache.put(3, 3);

        System.out.println(lruCache.get(1)); // Output: One
        lruCache.put(4, 4); // Evicts key 2

        System.out.println(lruCache.get(2)); // Output: null (not found)
        lruCache.put(5, 5); // Evicts key 3

        System.out.println(lruCache.get(3)); // Output: null (not found)
        System.out.println(lruCache.get(4)); // Output: Four
        System.out.println(lruCache.get(5)); // Output: Five
    }
}
