package com.conceptcoding.PracticeLLD.BookMyShowLLD.entities;

import com.conceptcoding.PracticeLLD.BookMyShowLLD.enums.PaymentStatus;

public class Payment {
    private final PaymentStatus paymentStatus;
    public Payment(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public PaymentStatus getStatus() {
        return paymentStatus;
    }
}
