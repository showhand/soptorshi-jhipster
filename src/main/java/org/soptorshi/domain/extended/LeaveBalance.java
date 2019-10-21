package org.soptorshi.domain.extended;

import org.soptorshi.domain.LeaveType;

import java.io.Serializable;

public class LeaveBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    private String employeeId;

    private int totalLeaveApplicableDays;

    private int remainingDays;

    private LeaveType leaveType;

    public String getEmployeeId() {
        return employeeId;
    }

    public LeaveBalance employeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public int getTotalLeaveApplicableDay() {
        return totalLeaveApplicableDays;
    }

    public LeaveBalance TotalLeaveApplicableDay(int totalLeaveApplicableDays) {
        this.totalLeaveApplicableDays = totalLeaveApplicableDays;
        return this;
    }

    public void setTotalLeaveApplicableDay(int totalLeaveApplicableDays) {
        this.totalLeaveApplicableDays = totalLeaveApplicableDays;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public LeaveBalance remainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
        return this;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public LeaveBalance leaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
        return this;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }
}

