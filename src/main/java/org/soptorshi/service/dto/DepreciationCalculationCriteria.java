package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.MonthType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the DepreciationCalculation entity. This class is used in DepreciationCalculationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /depreciation-calculations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DepreciationCalculationCriteria implements Serializable {
    /**
     * Class for filtering MonthType
     */
    public static class MonthTypeFilter extends Filter<MonthType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private MonthTypeFilter monthType;

    private BooleanFilter isExecuted;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter modifiedBy;

    private InstantFilter modifiedOn;

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

    public BooleanFilter getIsExecuted() {
        return isExecuted;
    }

    public void setIsExecuted(BooleanFilter isExecuted) {
        this.isExecuted = isExecuted;
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

    public StringFilter getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(StringFilter modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public InstantFilter getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(InstantFilter modifiedOn) {
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
        final DepreciationCalculationCriteria that = (DepreciationCalculationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(monthType, that.monthType) &&
            Objects.equals(isExecuted, that.isExecuted) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(financialAccountYearId, that.financialAccountYearId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        monthType,
        isExecuted,
        createdBy,
        createdOn,
        modifiedBy,
        modifiedOn,
        financialAccountYearId
        );
    }

    @Override
    public String toString() {
        return "DepreciationCalculationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (monthType != null ? "monthType=" + monthType + ", " : "") +
                (isExecuted != null ? "isExecuted=" + isExecuted + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (financialAccountYearId != null ? "financialAccountYearId=" + financialAccountYearId + ", " : "") +
            "}";
    }

}
