package com.conceptcoding.designpattern.behavioralpatterns.mediator;

// Colleague Interface(aka Component Interface)
public interface IColleague {
    void placeBid(double amount);

    void receiveBidNotification(double bidAmount);

    String getName();
}