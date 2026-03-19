package com.conceptcoding.impdesignpattern.strategypattern;

public class DebitCard implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Debit Card.");
    }
}
