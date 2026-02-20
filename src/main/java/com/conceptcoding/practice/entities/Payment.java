package com.conceptcoding.practice.entities;

import com.conceptcoding.practice.enums.PaymentStatus;

public class Payment {
    private final PaymentStatus paymentStatus;
    public Payment(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentStatus getStatus() {
        return paymentStatus;
    }
}
