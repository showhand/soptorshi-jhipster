package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.LeaveStatus;
import org.soptorshi.domain.enumeration.PaidOrUnPaid;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the LeaveApplication entity.
 */
public class LeaveApplicationDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate fromDate;

    @NotNull
    private LocalDate toDate;

    @NotNull
    private Integer numberOfDays;

    @NotNull
    private PaidOrUnPaid paidLeave;

    @NotNull
    @Size(max = 250)
    private String reason;

    private Instant appliedOn;

    private Instant actionTakenOn;

    @NotNull
    private LeaveStatus status;


    private Long leaveTypesId;

    private String leaveTypesName;

    private Long employeesId;

    private String employeesFullName;

    private Long appliedByIdId;

    private String appliedByIdFullName;

    private Long actionTakenByIdId;

    private String actionTakenByIdFullName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public PaidOrUnPaid getPaidLeave() {
        return paidLeave;
    }

    public void setPaidLeave(PaidOrUnPaid paidLeave) {
        this.paidLeave = paidLeave;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Instant getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(Instant appliedOn) {
        this.appliedOn = appliedOn;
    }

    public Instant getActionTakenOn() {
        return actionTakenOn;
    }

    public void setActionTakenOn(Instant actionTakenOn) {
        this.actionTakenOn = actionTakenOn;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public Long getLeaveTypesId() {
        return leaveTypesId;
    }

    public void setLeaveTypesId(Long leaveTypeId) {
        this.leaveTypesId = leaveTypeId;
    }

    public String getLeaveTypesName() {
        return leaveTypesName;
    }

    public void setLeaveTypesName(String leaveTypeName) {
        this.leaveTypesName = leaveTypeName;
    }

    public Long getEmployeesId() {
        return employeesId;
    }

    public void setEmployeesId(Long employeeId) {
        this.employeesId = employeeId;
    }

    public String getEmployeesFullName() {
        return employeesFullName;
    }

    public void setEmployeesFullName(String employeeFullName) {
        this.employeesFullName = employeeFullName;
    }

    public Long getAppliedByIdId() {
        return appliedByIdId;
    }

    public void setAppliedByIdId(Long employeeId) {
        this.appliedByIdId = employeeId;
    }

    public String getAppliedByIdFullName() {
        return appliedByIdFullName;
    }

    public void setAppliedByIdFullName(String employeeFullName) {
        this.appliedByIdFullName = employeeFullName;
    }

    public Long getActionTakenByIdId() {
        return actionTakenByIdId;
    }

    public void setActionTakenByIdId(Long employeeId) {
        this.actionTakenByIdId = employeeId;
    }

    public String getActionTakenByIdFullName() {
        return actionTakenByIdFullName;
    }

    public void setActionTakenByIdFullName(String employeeFullName) {
        this.actionTakenByIdFullName = employeeFullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LeaveApplicationDTO leaveApplicationDTO = (LeaveApplicationDTO) o;
        if (leaveApplicationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leaveApplicationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LeaveApplicationDTO{" +
            "id=" + getId() +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", numberOfDays=" + getNumberOfDays() +
            ", paidLeave='" + getPaidLeave() + "'" +
            ", reason='" + getReason() + "'" +
            ", appliedOn='" + getAppliedOn() + "'" +
            ", actionTakenOn='" + getActionTakenOn() + "'" +
            ", status='" + getStatus() + "'" +
            ", leaveTypes=" + getLeaveTypesId() +
            ", leaveTypes='" + getLeaveTypesName() + "'" +
            ", employees=" + getEmployeesId() +
            ", employees='" + getEmployeesFullName() + "'" +
            ", appliedById=" + getAppliedByIdId() +
            ", appliedById='" + getAppliedByIdFullName() + "'" +
            ", actionTakenById=" + getActionTakenByIdId() +
            ", actionTakenById='" + getActionTakenByIdFullName() + "'" +
            "}";
    }
}
