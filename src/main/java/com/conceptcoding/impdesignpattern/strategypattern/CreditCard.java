package com.conceptcoding.impdesignpattern.strategypattern;

public class CreditCard implements PaymentStrategy {
    @Override
    public void pay(double amount) {
        System.out.println("Paid " + amount + " using Credit Card.");
    }
}
