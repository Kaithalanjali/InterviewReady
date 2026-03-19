package com.conceptcoding.impdesignpattern.visitorpattern.HospitalVisitor;

public class DiagnosisVisitor implements Visitor {
    @Override
    public void visit(ChildPatient childPatient) {
        System.out.println("Diagnosing child patient...");
        // Perform diagnosis specific to child patients
    }

    @Override
    public void visit(AdultPatient adultPatient) {
        System.out.println("Diagnosing adult patient...");
        // Perform diagnosis specific to adult patients
    }

    @Override
    public void visit(SeniorPatient seniorPatient) {
        System.out.println("Diagnosing senior patient...");
        // Perform diagnosis specific to senior patients
    }
}
