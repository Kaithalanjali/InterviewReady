package com.conceptcoding.designpattern.behavioralpatterns.iterator.library;

// Aggregate interface
public interface BookCollection {
    Iterator<Book> createIterator();

    Iterator<Book> createReverseIterator();
}
