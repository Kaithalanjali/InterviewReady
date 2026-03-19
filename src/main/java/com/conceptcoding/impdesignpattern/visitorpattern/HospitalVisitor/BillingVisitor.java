package com.conceptcoding.impdesignpattern.visitorpattern.HospitalVisitor;

public class BillingVisitor implements Visitor {
    @Override
    public void visit(ChildPatient childPatient) {
        System.out.println("Billing for child patient: $50");
    }

    @Override
    public void visit(AdultPatient adultPatient) {
        System.out.println("Billing for adult patient: $100");
    }

    @Override
    public void visit(SeniorPatient seniorPatient) {
        System.out.println("Billing for senior patient: $70");
    }
}
