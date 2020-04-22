package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the OverTime entity. This class is used in OverTimeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /over-times?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OverTimeCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter overTimeDate;

    private InstantFilter fromTime;

    private InstantFilter toTime;

    private StringFilter duration;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private LongFilter employeeId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getOverTimeDate() {
        return overTimeDate;
    }

    public void setOverTimeDate(LocalDateFilter overTimeDate) {
        this.overTimeDate = overTimeDate;
    }

    public InstantFilter getFromTime() {
        return fromTime;
    }

    public void setFromTime(InstantFilter fromTime) {
        this.fromTime = fromTime;
    }

    public InstantFilter getToTime() {
        return toTime;
    }

    public void setToTime(InstantFilter toTime) {
        this.toTime = toTime;
    }

    public StringFilter getDuration() {
        return duration;
    }

    public void setDuration(StringFilter duration) {
        this.duration = duration;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public InstantFilter getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(InstantFilter updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OverTimeCriteria that = (OverTimeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(overTimeDate, that.overTimeDate) &&
            Objects.equals(fromTime, that.fromTime) &&
            Objects.equals(toTime, that.toTime) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        overTimeDate,
        fromTime,
        toTime,
        duration,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        employeeId
        );
    }

    @Override
    public String toString() {
        return "OverTimeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (overTimeDate != null ? "overTimeDate=" + overTimeDate + ", " : "") +
                (fromTime != null ? "fromTime=" + fromTime + ", " : "") +
                (toTime != null ? "toTime=" + toTime + ", " : "") +
                (duration != null ? "duration=" + duration + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
