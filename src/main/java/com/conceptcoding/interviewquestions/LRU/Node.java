package com.conceptcoding.interviewquestions.LRU;

public class Node {
    int key;
    int value;
    Node next;
    Node prev;
    Node() {
        key = value = -1;
        next = prev = null;
    }
    Node(int key, int value) {
        this.key = key;
        this.value = value;
        next = null;
        prev = null;
    }
}
