package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.BalanceType;
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
 * Criteria class for the AccountBalance entity. This class is used in AccountBalanceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /account-balances?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AccountBalanceCriteria implements Serializable {
    /**
     * Class for filtering BalanceType
     */
    public static class BalanceTypeFilter extends Filter<BalanceType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter yearOpenBalance;

    private BalanceTypeFilter yearOpenBalanceType;

    private BigDecimalFilter totDebitTrans;

    private BigDecimalFilter totCreditTrans;

    private LocalDateFilter modifiedOn;

    private StringFilter modifiedBy;

    private LongFilter financialAccountYearId;

    private LongFilter accountId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getYearOpenBalance() {
        return yearOpenBalance;
    }

    public void setYearOpenBalance(BigDecimalFilter yearOpenBalance) {
        this.yearOpenBalance = yearOpenBalance;
    }

    public BalanceTypeFilter getYearOpenBalanceType() {
        return yearOpenBalanceType;
    }

    public void setYearOpenBalanceType(BalanceTypeFilter yearOpenBalanceType) {
        this.yearOpenBalanceType = yearOpenBalanceType;
    }

    public BigDecimalFilter getTotDebitTrans() {
        return totDebitTrans;
    }

    public void setTotDebitTrans(BigDecimalFilter totDebitTrans) {
        this.totDebitTrans = totDebitTrans;
    }

    public BigDecimalFilter getTotCreditTrans() {
        return totCreditTrans;
    }

    public void setTotCreditTrans(BigDecimalFilter totCreditTrans) {
        this.totCreditTrans = totCreditTrans;
    }

    public LocalDateFilter getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateFilter modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public StringFilter getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(StringFilter modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LongFilter getFinancialAccountYearId() {
        return financialAccountYearId;
    }

    public void setFinancialAccountYearId(LongFilter financialAccountYearId) {
        this.financialAccountYearId = financialAccountYearId;
    }

    public LongFilter getAccountId() {
        return accountId;
    }

    public void setAccountId(LongFilter accountId) {
        this.accountId = accountId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AccountBalanceCriteria that = (AccountBalanceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(yearOpenBalance, that.yearOpenBalance) &&
            Objects.equals(yearOpenBalanceType, that.yearOpenBalanceType) &&
            Objects.equals(totDebitTrans, that.totDebitTrans) &&
            Objects.equals(totCreditTrans, that.totCreditTrans) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(financialAccountYearId, that.financialAccountYearId) &&
            Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        yearOpenBalance,
        yearOpenBalanceType,
        totDebitTrans,
        totCreditTrans,
        modifiedOn,
        modifiedBy,
        financialAccountYearId,
        accountId
        );
    }

    @Override
    public String toString() {
        return "AccountBalanceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (yearOpenBalance != null ? "yearOpenBalance=" + yearOpenBalance + ", " : "") +
                (yearOpenBalanceType != null ? "yearOpenBalanceType=" + yearOpenBalanceType + ", " : "") +
                (totDebitTrans != null ? "totDebitTrans=" + totDebitTrans + ", " : "") +
                (totCreditTrans != null ? "totCreditTrans=" + totCreditTrans + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (financialAccountYearId != null ? "financialAccountYearId=" + financialAccountYearId + ", " : "") +
                (accountId != null ? "accountId=" + accountId + ", " : "") +
            "}";
    }

}
