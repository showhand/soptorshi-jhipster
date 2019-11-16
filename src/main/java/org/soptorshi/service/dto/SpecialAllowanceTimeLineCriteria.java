package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.AllowanceType;
import org.soptorshi.domain.enumeration.Religion;
import org.soptorshi.domain.enumeration.MonthType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the SpecialAllowanceTimeLine entity. This class is used in SpecialAllowanceTimeLineResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /special-allowance-time-lines?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SpecialAllowanceTimeLineCriteria implements Serializable {
    /**
     * Class for filtering AllowanceType
     */
    public static class AllowanceTypeFilter extends Filter<AllowanceType> {
    }
    /**
     * Class for filtering Religion
     */
    public static class ReligionFilter extends Filter<Religion> {
    }
    /**
     * Class for filtering MonthType
     */
    public static class MonthTypeFilter extends Filter<MonthType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private AllowanceTypeFilter allowanceType;

    private ReligionFilter religion;

    private IntegerFilter year;

    private MonthTypeFilter month;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public AllowanceTypeFilter getAllowanceType() {
        return allowanceType;
    }

    public void setAllowanceType(AllowanceTypeFilter allowanceType) {
        this.allowanceType = allowanceType;
    }

    public ReligionFilter getReligion() {
        return religion;
    }

    public void setReligion(ReligionFilter religion) {
        this.religion = religion;
    }

    public IntegerFilter getYear() {
        return year;
    }

    public void setYear(IntegerFilter year) {
        this.year = year;
    }

    public MonthTypeFilter getMonth() {
        return month;
    }

    public void setMonth(MonthTypeFilter month) {
        this.month = month;
    }

    public StringFilter getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(StringFilter modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateFilter getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateFilter modifiedOn) {
        this.modifiedOn = modifiedOn;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SpecialAllowanceTimeLineCriteria that = (SpecialAllowanceTimeLineCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(allowanceType, that.allowanceType) &&
            Objects.equals(religion, that.religion) &&
            Objects.equals(year, that.year) &&
            Objects.equals(month, that.month) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        allowanceType,
        religion,
        year,
        month,
        modifiedBy,
        modifiedOn
        );
    }

    @Override
    public String toString() {
        return "SpecialAllowanceTimeLineCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (allowanceType != null ? "allowanceType=" + allowanceType + ", " : "") +
                (religion != null ? "religion=" + religion + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (month != null ? "month=" + month + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
            "}";
    }

}
