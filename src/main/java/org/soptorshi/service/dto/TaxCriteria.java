package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.TaxStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the Tax entity. This class is used in TaxResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /taxes?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaxCriteria implements Serializable {
    /**
     * Class for filtering TaxStatus
     */
    public static class TaxStatusFilter extends Filter<TaxStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter minimumSalary;

    private DoubleFilter rate;

    private TaxStatusFilter taxStatus;

    private LongFilter financialAccountYearId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getMinimumSalary() {
        return minimumSalary;
    }

    public void setMinimumSalary(BigDecimalFilter minimumSalary) {
        this.minimumSalary = minimumSalary;
    }

    public DoubleFilter getRate() {
        return rate;
    }

    public void setRate(DoubleFilter rate) {
        this.rate = rate;
    }

    public TaxStatusFilter getTaxStatus() {
        return taxStatus;
    }

    public void setTaxStatus(TaxStatusFilter taxStatus) {
        this.taxStatus = taxStatus;
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
        final TaxCriteria that = (TaxCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(minimumSalary, that.minimumSalary) &&
            Objects.equals(rate, that.rate) &&
            Objects.equals(taxStatus, that.taxStatus) &&
            Objects.equals(financialAccountYearId, that.financialAccountYearId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        minimumSalary,
        rate,
        taxStatus,
        financialAccountYearId
        );
    }

    @Override
    public String toString() {
        return "TaxCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (minimumSalary != null ? "minimumSalary=" + minimumSalary + ", " : "") +
                (rate != null ? "rate=" + rate + ", " : "") +
                (taxStatus != null ? "taxStatus=" + taxStatus + ", " : "") +
                (financialAccountYearId != null ? "financialAccountYearId=" + financialAccountYearId + ", " : "") +
            "}";
    }

}
