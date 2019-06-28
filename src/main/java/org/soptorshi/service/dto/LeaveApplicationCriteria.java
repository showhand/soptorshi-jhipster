package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.LeaveStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

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
     * Class for filtering LeaveStatus
     */
    public static class LeaveStatusFilter extends Filter<LeaveStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter employeeId;

    private LocalDateFilter fromDate;

    private LocalDateFilter toDate;

    private IntegerFilter numberOfDays;

    private StringFilter reason;

    private StringFilter appliedBy;

    private InstantFilter appliedOn;

    private StringFilter actionTakenBy;

    private InstantFilter actionTakenOn;

    private LeaveStatusFilter status;

    private LongFilter leaveTypesId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(StringFilter employeeId) {
        this.employeeId = employeeId;
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

    public StringFilter getReason() {
        return reason;
    }

    public void setReason(StringFilter reason) {
        this.reason = reason;
    }

    public StringFilter getAppliedBy() {
        return appliedBy;
    }

    public void setAppliedBy(StringFilter appliedBy) {
        this.appliedBy = appliedBy;
    }

    public InstantFilter getAppliedOn() {
        return appliedOn;
    }

    public void setAppliedOn(InstantFilter appliedOn) {
        this.appliedOn = appliedOn;
    }

    public StringFilter getActionTakenBy() {
        return actionTakenBy;
    }

    public void setActionTakenBy(StringFilter actionTakenBy) {
        this.actionTakenBy = actionTakenBy;
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
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(toDate, that.toDate) &&
            Objects.equals(numberOfDays, that.numberOfDays) &&
            Objects.equals(reason, that.reason) &&
            Objects.equals(appliedBy, that.appliedBy) &&
            Objects.equals(appliedOn, that.appliedOn) &&
            Objects.equals(actionTakenBy, that.actionTakenBy) &&
            Objects.equals(actionTakenOn, that.actionTakenOn) &&
            Objects.equals(status, that.status) &&
            Objects.equals(leaveTypesId, that.leaveTypesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        employeeId,
        fromDate,
        toDate,
        numberOfDays,
        reason,
        appliedBy,
        appliedOn,
        actionTakenBy,
        actionTakenOn,
        status,
        leaveTypesId
        );
    }

    @Override
    public String toString() {
        return "LeaveApplicationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (toDate != null ? "toDate=" + toDate + ", " : "") +
                (numberOfDays != null ? "numberOfDays=" + numberOfDays + ", " : "") +
                (reason != null ? "reason=" + reason + ", " : "") +
                (appliedBy != null ? "appliedBy=" + appliedBy + ", " : "") +
                (appliedOn != null ? "appliedOn=" + appliedOn + ", " : "") +
                (actionTakenBy != null ? "actionTakenBy=" + actionTakenBy + ", " : "") +
                (actionTakenOn != null ? "actionTakenOn=" + actionTakenOn + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (leaveTypesId != null ? "leaveTypesId=" + leaveTypesId + ", " : "") +
            "}";
    }

}
