package com.conceptcoding.impdesignpattern.chainofresponsibility;

public class Director implements Approver {
    private Approver nextApprover;

    @Override
    public void setNextApprover(Approver nextApprover) {
        this.nextApprover = nextApprover;
    }

    @Override
    public void processLeaveRequest(int days){
        if (days <= 10) {
            System.out.println("Director approved leave for " + days + " days.");
        } else if (nextApprover != null) {
            nextApprover.processLeaveRequest(days);
        } else {
            System.out.println("Leave request for " + days + " days cannot be processed.");
        }
    }
}
