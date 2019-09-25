package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.FinancialYearStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the FinancialAccountYear entity. This class is used in FinancialAccountYearResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /financial-account-years?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FinancialAccountYearCriteria implements Serializable {
    /**
     * Class for filtering FinancialYearStatus
     */
    public static class FinancialYearStatusFilter extends Filter<FinancialYearStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter startDate;

    private LocalDateFilter endDate;

    private LocalDateFilter previousStartDate;

    private LocalDateFilter previousEndDate;

    private StringFilter durationStr;

    private FinancialYearStatusFilter status;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateFilter startDate) {
        this.startDate = startDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public LocalDateFilter getPreviousStartDate() {
        return previousStartDate;
    }

    public void setPreviousStartDate(LocalDateFilter previousStartDate) {
        this.previousStartDate = previousStartDate;
    }

    public LocalDateFilter getPreviousEndDate() {
        return previousEndDate;
    }

    public void setPreviousEndDate(LocalDateFilter previousEndDate) {
        this.previousEndDate = previousEndDate;
    }

    public StringFilter getDurationStr() {
        return durationStr;
    }

    public void setDurationStr(StringFilter durationStr) {
        this.durationStr = durationStr;
    }

    public FinancialYearStatusFilter getStatus() {
        return status;
    }

    public void setStatus(FinancialYearStatusFilter status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FinancialAccountYearCriteria that = (FinancialAccountYearCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(previousStartDate, that.previousStartDate) &&
            Objects.equals(previousEndDate, that.previousEndDate) &&
            Objects.equals(durationStr, that.durationStr) &&
            Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        startDate,
        endDate,
        previousStartDate,
        previousEndDate,
        durationStr,
        status
        );
    }

    @Override
    public String toString() {
        return "FinancialAccountYearCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (startDate != null ? "startDate=" + startDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (previousStartDate != null ? "previousStartDate=" + previousStartDate + ", " : "") +
                (previousEndDate != null ? "previousEndDate=" + previousEndDate + ", " : "") +
                (durationStr != null ? "durationStr=" + durationStr + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
            "}";
    }

}
