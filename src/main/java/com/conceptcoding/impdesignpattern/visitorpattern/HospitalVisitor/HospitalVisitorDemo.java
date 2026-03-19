package com.conceptcoding.impdesignpattern.visitorpattern.HospitalVisitor;

public class HospitalVisitorDemo {
    public static void main(String[] args){
        Patient childPatient = new ChildPatient();
        Patient adultPatient = new AdultPatient();
        Patient seniorPatient = new SeniorPatient();

        Visitor diagnosisVisitor = new DiagnosisVisitor();
        Visitor billingVisitor = new BillingVisitor();

        childPatient.accept(diagnosisVisitor);
        adultPatient.accept(diagnosisVisitor);
        seniorPatient.accept(diagnosisVisitor);

        childPatient.accept(billingVisitor);
        adultPatient.accept(billingVisitor);
        seniorPatient.accept(billingVisitor);
    }
}
