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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the MonthlyBalance entity. This class is used in MonthlyBalanceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /monthly-balances?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MonthlyBalanceCriteria implements Serializable {
    /**
     * Class for filtering MonthType
     */
    public static class MonthTypeFilter extends Filter<MonthType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private MonthTypeFilter monthType;

    private BigDecimalFilter totMonthDbBal;

    private BigDecimalFilter totMonthCrBal;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter accountBalanceId;

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

    public BigDecimalFilter getTotMonthDbBal() {
        return totMonthDbBal;
    }

    public void setTotMonthDbBal(BigDecimalFilter totMonthDbBal) {
        this.totMonthDbBal = totMonthDbBal;
    }

    public BigDecimalFilter getTotMonthCrBal() {
        return totMonthCrBal;
    }

    public void setTotMonthCrBal(BigDecimalFilter totMonthCrBal) {
        this.totMonthCrBal = totMonthCrBal;
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

    public LongFilter getAccountBalanceId() {
        return accountBalanceId;
    }

    public void setAccountBalanceId(LongFilter accountBalanceId) {
        this.accountBalanceId = accountBalanceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MonthlyBalanceCriteria that = (MonthlyBalanceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(monthType, that.monthType) &&
            Objects.equals(totMonthDbBal, that.totMonthDbBal) &&
            Objects.equals(totMonthCrBal, that.totMonthCrBal) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(accountBalanceId, that.accountBalanceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        monthType,
        totMonthDbBal,
        totMonthCrBal,
        modifiedBy,
        modifiedOn,
        accountBalanceId
        );
    }

    @Override
    public String toString() {
        return "MonthlyBalanceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (monthType != null ? "monthType=" + monthType + ", " : "") +
                (totMonthDbBal != null ? "totMonthDbBal=" + totMonthDbBal + ", " : "") +
                (totMonthCrBal != null ? "totMonthCrBal=" + totMonthCrBal + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (accountBalanceId != null ? "accountBalanceId=" + accountBalanceId + ", " : "") +
            "}";
    }

}
