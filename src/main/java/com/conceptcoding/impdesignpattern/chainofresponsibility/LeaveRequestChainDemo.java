package com.conceptcoding.impdesignpattern.chainofresponsibility;

public class LeaveRequestChainDemo{
public static void main(String[] args) {
    // Create handler instances
    Approver supervisor = new Supervisor();
    Approver manager = new Manager();
    Approver director = new Director();
    // Set up the chain: Supervisor -> Manager -> Director
    supervisor.setNextApprover(manager);
    manager.setNextApprover(director);
    // Process a leave request
    int leaveDays = 10;
    System.out.println("Employee requests " + leaveDays + " days of leave.");
    supervisor.processLeaveRequest(leaveDays);
}
}
