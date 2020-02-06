package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.DayOfWeek;
import org.soptorshi.domain.enumeration.WeekendStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the Weekend entity. This class is used in WeekendResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /weekends?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WeekendCriteria implements Serializable {
    /**
     * Class for filtering DayOfWeek
     */
    public static class DayOfWeekFilter extends Filter<DayOfWeek> {
    }
    /**
     * Class for filtering WeekendStatus
     */
    public static class WeekendStatusFilter extends Filter<WeekendStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter numberOfDays;

    private LocalDateFilter activeFrom;

    private LocalDateFilter activeTo;

    private DayOfWeekFilter day1;

    private DayOfWeekFilter day2;

    private DayOfWeekFilter day3;

    private WeekendStatusFilter status;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(IntegerFilter numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public LocalDateFilter getActiveFrom() {
        return activeFrom;
    }

    public void setActiveFrom(LocalDateFilter activeFrom) {
        this.activeFrom = activeFrom;
    }

    public LocalDateFilter getActiveTo() {
        return activeTo;
    }

    public void setActiveTo(LocalDateFilter activeTo) {
        this.activeTo = activeTo;
    }

    public DayOfWeekFilter getDay1() {
        return day1;
    }

    public void setDay1(DayOfWeekFilter day1) {
        this.day1 = day1;
    }

    public DayOfWeekFilter getDay2() {
        return day2;
    }

    public void setDay2(DayOfWeekFilter day2) {
        this.day2 = day2;
    }

    public DayOfWeekFilter getDay3() {
        return day3;
    }

    public void setDay3(DayOfWeekFilter day3) {
        this.day3 = day3;
    }

    public WeekendStatusFilter getStatus() {
        return status;
    }

    public void setStatus(WeekendStatusFilter status) {
        this.status = status;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WeekendCriteria that = (WeekendCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(numberOfDays, that.numberOfDays) &&
            Objects.equals(activeFrom, that.activeFrom) &&
            Objects.equals(activeTo, that.activeTo) &&
            Objects.equals(day1, that.day1) &&
            Objects.equals(day2, that.day2) &&
            Objects.equals(day3, that.day3) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        numberOfDays,
        activeFrom,
        activeTo,
        day1,
        day2,
        day3,
        status,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn
        );
    }

    @Override
    public String toString() {
        return "WeekendCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numberOfDays != null ? "numberOfDays=" + numberOfDays + ", " : "") +
                (activeFrom != null ? "activeFrom=" + activeFrom + ", " : "") +
                (activeTo != null ? "activeTo=" + activeTo + ", " : "") +
                (day1 != null ? "day1=" + day1 + ", " : "") +
                (day2 != null ? "day2=" + day2 + ", " : "") +
                (day3 != null ? "day3=" + day3 + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
            "}";
    }

}
