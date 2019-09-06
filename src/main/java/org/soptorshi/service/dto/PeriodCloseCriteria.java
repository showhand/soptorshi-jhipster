package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.domain.enumeration.PeriodCloseFlag;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the PeriodClose entity. This class is used in PeriodCloseResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /period-closes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PeriodCloseCriteria implements Serializable {
    /**
     * Class for filtering MonthType
     */
    public static class MonthTypeFilter extends Filter<MonthType> {
    }
    /**
     * Class for filtering PeriodCloseFlag
     */
    public static class PeriodCloseFlagFilter extends Filter<PeriodCloseFlag> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private MonthTypeFilter monthType;

    private IntegerFilter closeYear;

    private PeriodCloseFlagFilter flag;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter financialAccountYearId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public MonthTypeFilter getMonthType() {
        return monthType;
    }

    public void setMonthType(MonthTypeFilter monthType) {
        this.monthType = monthType;
    }

    public IntegerFilter getCloseYear() {
        return closeYear;
    }

    public void setCloseYear(IntegerFilter closeYear) {
        this.closeYear = closeYear;
    }

    public PeriodCloseFlagFilter getFlag() {
        return flag;
    }

    public void setFlag(PeriodCloseFlagFilter flag) {
        this.flag = flag;
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

    public LongFilter getFinancialAccountYearId() {
        return financialAccountYearId;
    }

    public void setFinancialAccountYearId(LongFilter financialAccountYearId) {
        this.financialAccountYearId = financialAccountYearId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PeriodCloseCriteria that = (PeriodCloseCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(monthType, that.monthType) &&
            Objects.equals(closeYear, that.closeYear) &&
            Objects.equals(flag, that.flag) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(financialAccountYearId, that.financialAccountYearId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        monthType,
        closeYear,
        flag,
        modifiedBy,
        modifiedOn,
        financialAccountYearId
        );
    }

    @Override
    public String toString() {
        return "PeriodCloseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (monthType != null ? "monthType=" + monthType + ", " : "") +
                (closeYear != null ? "closeYear=" + closeYear + ", " : "") +
                (flag != null ? "flag=" + flag + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (financialAccountYearId != null ? "financialAccountYearId=" + financialAccountYearId + ", " : "") +
            "}";
    }

}
