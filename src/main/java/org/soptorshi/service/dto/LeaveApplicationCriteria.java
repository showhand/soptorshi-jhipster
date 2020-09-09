package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.LeaveStatus;
import org.soptorshi.domain.enumeration.PaidOrUnPaid;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the LeaveApplication entity. This class is used in LeaveApplicationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /leave-applications?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LeaveApplicationCriteria implements Serializable {
    /**
     * Class for filtering PaidOrUnPaid
     */
    public static class PaidOrUnPaidFilter extends Filter<PaidOrUnPaid> {
    }
    /**
     * Class for filtering LeaveStatus
     */
    public static class LeaveStatusFilter extends Filter<LeaveStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter fromDate;

    private LocalDateFilter toDate;

    private IntegerFilter numberOfDays;

    private PaidOrUnPaidFilter paidLeave;

    private StringFilter reason;

    private InstantFilter appliedOn;

    private InstantFilter actionTakenOn;

    private LeaveStatusFilter status;

    private LongFilter leaveTypesId;

    private LongFilter employeesId;

    private LongFilter appliedByIdId;

    private LongFilter actionTakenByIdId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateFilter fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateFilter getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateFilter toDate) {
        this.toDate = toDate;
    }

    public IntegerFilter getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(IntegerFilter numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public PaidOrUnPaidFilter getPaidLeave() {
        return paidLeave;
    }

    public void setPaidLeave(PaidOrUnPaidFilter paidLeave) {
        this.paidLeave = paidLeave;
    }

    public StringFilter getReason() {
        return reason;
    }

    public void setReason(StringFilter reason) {
        this.reason = reason;
    }

    public InstantFilter getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(InstantFilter appliedOn) {
        this.appliedOn = appliedOn;
    }

    public InstantFilter getActionTakenOn() {
        return actionTakenOn;
    }

    public void setActionTakenOn(InstantFilter actionTakenOn) {
        this.actionTakenOn = actionTakenOn;
    }

    public LeaveStatusFilter getStatus() {
        return status;
    }

    public void setStatus(LeaveStatusFilter status) {
        this.status = status;
    }

    public LongFilter getLeaveTypesId() {
        return leaveTypesId;
    }

    public void setLeaveTypesId(LongFilter leaveTypesId) {
        this.leaveTypesId = leaveTypesId;
    }

    public LongFilter getEmployeesId() {
        return employeesId;
    }

    public void setEmployeesId(LongFilter employeesId) {
        this.employeesId = employeesId;
    }

    public LongFilter getAppliedByIdId() {
        return appliedByIdId;
    }

    public void setAppliedByIdId(LongFilter appliedByIdId) {
        this.appliedByIdId = appliedByIdId;
    }

    public LongFilter getActionTakenByIdId() {
        return actionTakenByIdId;
    }

    public void setActionTakenByIdId(LongFilter actionTakenByIdId) {
        this.actionTakenByIdId = actionTakenByIdId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LeaveApplicationCriteria that = (LeaveApplicationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(toDate, that.toDate) &&
            Objects.equals(numberOfDays, that.numberOfDays) &&
            Objects.equals(paidLeave, that.paidLeave) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(appliedOn, that.appliedOn) &&
            Objects.equals(actionTakenOn, that.actionTakenOn) &&
            Objects.equals(status, that.status) &&
            Objects.equals(leaveTypesId, that.leaveTypesId) &&
            Objects.equals(employeesId, that.employeesId) &&
            Objects.equals(appliedByIdId, that.appliedByIdId) &&
            Objects.equals(actionTakenByIdId, that.actionTakenByIdId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fromDate,
        toDate,
        numberOfDays,
        paidLeave,
        reason,
        appliedOn,
        actionTakenOn,
        status,
        leaveTypesId,
        employeesId,
        appliedByIdId,
        actionTakenByIdId
        );
    }

    @Override
    public String toString() {
        return "LeaveApplicationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (toDate != null ? "toDate=" + toDate + ", " : "") +
                (numberOfDays != null ? "numberOfDays=" + numberOfDays + ", " : "") +
                (paidLeave != null ? "paidLeave=" + paidLeave + ", " : "") +
                (reason != null ? "reason=" + reason + ", " : "") +
                (appliedOn != null ? "appliedOn=" + appliedOn + ", " : "") +
                (actionTakenOn != null ? "actionTakenOn=" + actionTakenOn + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (leaveTypesId != null ? "leaveTypesId=" + leaveTypesId + ", " : "") +
                (employeesId != null ? "employeesId=" + employeesId + ", " : "") +
                (appliedByIdId != null ? "appliedByIdId=" + appliedByIdId + ", " : "") +
                (actionTakenByIdId != null ? "actionTakenByIdId=" + actionTakenByIdId + ", " : "") +
            "}";
    }

}
