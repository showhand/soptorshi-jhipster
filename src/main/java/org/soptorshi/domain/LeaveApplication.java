package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.soptorshi.domain.enumeration.LeaveStatus;
import org.soptorshi.domain.enumeration.PaidOrUnPaid;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

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
    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @NotNull
    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @NotNull
    @Column(name = "number_of_days", nullable = false)
    private Integer numberOfDays;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "paid_leave", nullable = false)
    private PaidOrUnPaid paidLeave;

    @NotNull
    @Size(max = 250)
    @Column(name = "reason", length = 250, nullable = false)
    private String reason;

    @Column(name = "applied_on")
    private Instant appliedOn;

    @Column(name = "action_taken_on")
    private Instant actionTakenOn;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private LeaveStatus status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("leaveApplications")
    private LeaveType leaveTypes;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("leaveApplications")
    private Employee employees;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("leaveApplications")
    private Employee appliedById;

    @ManyToOne
    @JsonIgnoreProperties("leaveApplications")
    private Employee actionTakenById;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public PaidOrUnPaid getPaidLeave() {
        return paidLeave;
    }

    public LeaveApplication paidLeave(PaidOrUnPaid paidLeave) {
        this.paidLeave = paidLeave;
        return this;
    }

    public void setPaidLeave(PaidOrUnPaid paidLeave) {
        this.paidLeave = paidLeave;
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

    public Employee getEmployees() {
        return employees;
    }

    public LeaveApplication employees(Employee employee) {
        this.employees = employee;
        return this;
    }

    public void setEmployees(Employee employee) {
        this.employees = employee;
    }

    public Employee getAppliedById() {
        return appliedById;
    }

    public LeaveApplication appliedById(Employee employee) {
        this.appliedById = employee;
        return this;
    }

    public void setAppliedById(Employee employee) {
        this.appliedById = employee;
    }

    public Employee getActionTakenById() {
        return actionTakenById;
    }

    public LeaveApplication actionTakenById(Employee employee) {
        this.actionTakenById = employee;
        return this;
    }

    public void setActionTakenById(Employee employee) {
        this.actionTakenById = employee;
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
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", numberOfDays=" + getNumberOfDays() +
            ", paidLeave='" + getPaidLeave() + "'" +
            ", reason='" + getReason() + "'" +
            ", appliedOn='" + getAppliedOn() + "'" +
            ", actionTakenOn='" + getActionTakenOn() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
