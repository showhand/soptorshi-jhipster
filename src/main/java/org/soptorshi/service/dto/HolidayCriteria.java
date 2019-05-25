package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Holiday entity. This class is used in HolidayResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /holidays?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HolidayCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter fromDate;

    private LocalDateFilter toDate;

    private IntegerFilter numberOfDays;

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
            Objects.equals(holidayTypeId, that.holidayTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fromDate,
        toDate,
        numberOfDays,
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
                (holidayTypeId != null ? "holidayTypeId=" + holidayTypeId + ", " : "") +
            "}";
    }

}
