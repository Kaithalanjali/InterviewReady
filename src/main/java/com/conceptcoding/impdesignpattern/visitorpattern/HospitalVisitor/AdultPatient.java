package com.conceptcoding.impdesignpattern.visitorpattern.HospitalVisitor;

public class AdultPatient implements Patient {
    @Override
    public void accept(Visitor visitor){
        visitor.visit(this);
    }
}
