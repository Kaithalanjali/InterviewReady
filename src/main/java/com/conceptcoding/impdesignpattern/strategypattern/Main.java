package com.conceptcoding.impdesignpattern.strategypattern;

public class Main {
    public static void main(String[] args) {

        PaymentStrategy creditCard = new CreditCard();
        PaymentStrategy debitCard = new DebitCard();

        PaymentProcessor paymentProcessor = new PaymentProcessor(creditCard);
        paymentProcessor.processPayment(100.0);

        paymentProcessor.setPaymentStrategy(debitCard);
        paymentProcessor.processPayment(200.0);
    }
}
