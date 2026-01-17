package com.conceptcoding.interviewquestions.LRU;

import java.util.concurrent.ConcurrentHashMap;

public class LRUCache {
        private final Integer capacity;
        private ConcurrentHashMap <Integer, Node> mapp = new ConcurrentHashMap<>();
        private Node head;
        private Node tail;
            void deleteNode(Node node) {
                Node prevNode = node.prev;
                Node nextNode = node.next;
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
            }
            void insertAfterHead(Node node) {
                Node headNext = head.next;
                head.next = node;
                headNext.prev = node;
                node.prev = head;
                node.next = headNext;
            }
        public
        LRUCache(Integer capacity) {
            this.capacity = capacity;
            head = new Node();
            tail = new Node();
            head.next = tail;
            tail.prev = head;
        }

        Integer get(Integer key) {
            if (!mapp.containsKey(key)) {
                return -1;
            }
            Node node = mapp.get(key);
            deleteNode(node);
            insertAfterHead(node);
            return node.value;
        }

        void put(Integer key, Integer value) {
            if (mapp.containsKey(key)) {
                Node node = mapp.get(key);
                node.value = value;
                deleteNode(node);
                insertAfterHead(node);
            } else {
                if (mapp.size() == capacity) {
                    Node delNode = tail.prev;
                    mapp.remove(delNode.key);
                    deleteNode(delNode);
                }

                Node node = new Node(key, value);
                mapp.put(key, node);
                insertAfterHead(node);
            }
        }
    }
