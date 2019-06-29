package org.soptorshi.service.dto;
import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.LeaveStatus;

/**
 * A DTO for the LeaveApplication entity.
 */
public class LeaveApplicationDTO implements Serializable {

    private Long id;

    @NotNull
    private String employeeId;

    @NotNull
    private LocalDate fromDate;

    @NotNull
    private LocalDate toDate;

    @NotNull
    private Integer numberOfDays;

    @NotNull
    @Size(max = 250)
    private String reason;

    @NotNull
    private String appliedBy;

    private Instant appliedOn;

    @NotNull
    private String actionTakenBy;

    private Instant actionTakenOn;

    @NotNull
    private LeaveStatus status;


    private Long leaveTypesId;

    private String leaveTypesName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAppliedBy() {
        return appliedBy;
    }

    public void setAppliedBy(String appliedBy) {
        this.appliedBy = appliedBy;
    }

    public Instant getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(Instant appliedOn) {
        this.appliedOn = appliedOn;
    }

    public String getActionTakenBy() {
        return actionTakenBy;
    }

    public void setActionTakenBy(String actionTakenBy) {
        this.actionTakenBy = actionTakenBy;
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
            ", employeeId='" + getEmployeeId() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", numberOfDays=" + getNumberOfDays() +
            ", reason='" + getReason() + "'" +
            ", appliedBy='" + getAppliedBy() + "'" +
            ", appliedOn='" + getAppliedOn() + "'" +
            ", actionTakenBy='" + getActionTakenBy() + "'" +
            ", actionTakenOn='" + getActionTakenOn() + "'" +
            ", status='" + getStatus() + "'" +
            ", leaveTypes=" + getLeaveTypesId() +
            ", leaveTypes='" + getLeaveTypesName() + "'" +
            "}";
    }
}
