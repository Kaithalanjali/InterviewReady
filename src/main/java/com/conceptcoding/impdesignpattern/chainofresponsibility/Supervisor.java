package com.conceptcoding.impdesignpattern.chainofresponsibility;

public class Supervisor implements Approver {
    private Approver nextApprover;

    @Override
    public void setNextApprover(Approver approver) {
        this.nextApprover = approver;
    }

    @Override
    public void processLeaveRequest(int days) {
        if (days <= 3) {
            System.out.println("Supervisor approved leave for " + days + " days.");
        } else if (nextApprover != null) {
            nextApprover.processLeaveRequest(days);
        } else {
            System.out.println("Leave request for " + days + " days cannot be approved.");
        }
    }
}
