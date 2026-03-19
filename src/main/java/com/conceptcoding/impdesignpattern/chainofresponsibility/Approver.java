package com.conceptcoding.impdesignpattern.chainofresponsibility;

public interface Approver {
    void setNextApprover(Approver nextApprover);
    void processLeaveRequest(int days);
}
