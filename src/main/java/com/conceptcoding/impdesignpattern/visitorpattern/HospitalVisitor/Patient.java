package com.conceptcoding.impdesignpattern.visitorpattern.HospitalVisitor;

public interface Patient {
    void accept(Visitor visitor);
}
