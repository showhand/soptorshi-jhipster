package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.LeaveStatus;

/**
 * A LeaveApplication.
 */
@Entity
@Table(name = "leave_application")
@Document(indexName = "leaveapplication")
public class LeaveApplication implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "employee_id", nullable = false)
    private String employeeId;

    @NotNull
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @NotNull
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @NotNull
    @Column(name = "number_of_days", nullable = false)
    private Integer numberOfDays;

    @NotNull
    @Size(max = 250)
    @Column(name = "reason", length = 250, nullable = false)
    private String reason;

    @NotNull
    @Column(name = "applied_by", nullable = false)
    private String appliedBy;

    @Column(name = "applied_on")
    private Instant appliedOn;

    @NotNull
    @Column(name = "action_taken_by", nullable = false)
    private String actionTakenBy;

    @Column(name = "action_taken_on")
    private Instant actionTakenOn;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LeaveStatus status;

    @ManyToOne
    @JsonIgnoreProperties("leaveApplications")
    private LeaveType leaveTypes;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public LeaveApplication employeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LeaveApplication fromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public LeaveApplication toDate(LocalDate toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Integer getNumberOfDays() {
        return numberOfDays;
    }

    public LeaveApplication numberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
        return this;
    }

    public void setNumberOfDays(Integer numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public String getReason() {
        return reason;
    }

    public LeaveApplication reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAppliedBy() {
        return appliedBy;
    }

    public LeaveApplication appliedBy(String appliedBy) {
        this.appliedBy = appliedBy;
        return this;
    }

    public void setAppliedBy(String appliedBy) {
        this.appliedBy = appliedBy;
    }

    public Instant getAppliedOn() {
        return appliedOn;
    }

    public LeaveApplication appliedOn(Instant appliedOn) {
        this.appliedOn = appliedOn;
        return this;
    }

    public void setAppliedOn(Instant appliedOn) {
        this.appliedOn = appliedOn;
    }

    public String getActionTakenBy() {
        return actionTakenBy;
    }

    public LeaveApplication actionTakenBy(String actionTakenBy) {
        this.actionTakenBy = actionTakenBy;
        return this;
    }

    public void setActionTakenBy(String actionTakenBy) {
        this.actionTakenBy = actionTakenBy;
    }

    public Instant getActionTakenOn() {
        return actionTakenOn;
    }

    public LeaveApplication actionTakenOn(Instant actionTakenOn) {
        this.actionTakenOn = actionTakenOn;
        return this;
    }

    public void setActionTakenOn(Instant actionTakenOn) {
        this.actionTakenOn = actionTakenOn;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public LeaveApplication status(LeaveStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public LeaveType getLeaveTypes() {
        return leaveTypes;
    }

    public LeaveApplication leaveTypes(LeaveType leaveType) {
        this.leaveTypes = leaveType;
        return this;
    }

    public void setLeaveTypes(LeaveType leaveType) {
        this.leaveTypes = leaveType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LeaveApplication leaveApplication = (LeaveApplication) o;
        if (leaveApplication.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), leaveApplication.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LeaveApplication{" +
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
            "}";
    }
}
