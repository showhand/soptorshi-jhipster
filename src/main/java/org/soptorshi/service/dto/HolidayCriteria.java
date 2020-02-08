package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.HolidayImposedAuthority;
import org.soptorshi.domain.enumeration.YesOrNo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the Holiday entity. This class is used in HolidayResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /holidays?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HolidayCriteria implements Serializable {
    /**
     * Class for filtering YesOrNo
     */
    public static class YesOrNoFilter extends Filter<YesOrNo> {
    }
    /**
     * Class for filtering HolidayImposedAuthority
     */
    public static class HolidayImposedAuthorityFilter extends Filter<HolidayImposedAuthority> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter fromDate;

    private LocalDateFilter toDate;

    private IntegerFilter numberOfDays;

    private YesOrNoFilter moonDependency;

    private HolidayImposedAuthorityFilter holidayDeclaredBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private IntegerFilter holidayYear;

    private LongFilter holidayTypeId;

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

    public YesOrNoFilter getMoonDependency() {
        return moonDependency;
    }

    public void setMoonDependency(YesOrNoFilter moonDependency) {
        this.moonDependency = moonDependency;
    }

    public HolidayImposedAuthorityFilter getHolidayDeclaredBy() {
        return holidayDeclaredBy;
    }

    public void setHolidayDeclaredBy(HolidayImposedAuthorityFilter holidayDeclaredBy) {
        this.holidayDeclaredBy = holidayDeclaredBy;
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

    public IntegerFilter getHolidayYear() {
        return holidayYear;
    }

    public void setHolidayYear(IntegerFilter holidayYear) {
        this.holidayYear = holidayYear;
    }

    public LongFilter getHolidayTypeId() {
        return holidayTypeId;
    }

    public void setHolidayTypeId(LongFilter holidayTypeId) {
        this.holidayTypeId = holidayTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HolidayCriteria that = (HolidayCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fromDate, that.fromDate) &&
            Objects.equals(toDate, that.toDate) &&
            Objects.equals(numberOfDays, that.numberOfDays) &&
            Objects.equals(moonDependency, that.moonDependency) &&
            Objects.equals(holidayDeclaredBy, that.holidayDeclaredBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(holidayYear, that.holidayYear) &&
            Objects.equals(holidayTypeId, that.holidayTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fromDate,
        toDate,
        numberOfDays,
        moonDependency,
        holidayDeclaredBy,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        holidayYear,
        holidayTypeId
        );
    }

    @Override
    public String toString() {
        return "HolidayCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fromDate != null ? "fromDate=" + fromDate + ", " : "") +
                (toDate != null ? "toDate=" + toDate + ", " : "") +
                (numberOfDays != null ? "numberOfDays=" + numberOfDays + ", " : "") +
                (moonDependency != null ? "moonDependency=" + moonDependency + ", " : "") +
                (holidayDeclaredBy != null ? "holidayDeclaredBy=" + holidayDeclaredBy + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (holidayYear != null ? "holidayYear=" + holidayYear + ", " : "") +
                (holidayTypeId != null ? "holidayTypeId=" + holidayTypeId + ", " : "") +
            "}";
    }

}
