package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.LeaveStatus;

import javax.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

public class LeaveBalanceDTO implements Serializable {

    private String employeeId;

    private int totalLeaveApplicableDays;

    private int remainingDays;

    private Long leaveTypeId;

    private String leaveTypeName;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public int getTotalLeaveApplicableDays() {
        return totalLeaveApplicableDays;
    }

    public void setTotalLeaveApplicableDays(int totalLeaveApplicableDays) {
        this.totalLeaveApplicableDays = totalLeaveApplicableDays;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    public Long getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(Long leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }
}
