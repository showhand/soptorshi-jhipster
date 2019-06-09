package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.PaymentStatus;
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
 * Criteria class for the Loan entity. This class is used in LoanResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /loans?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LoanCriteria implements Serializable {
    /**
     * Class for filtering PaymentStatus
     */
    public static class PaymentStatusFilter extends Filter<PaymentStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter amount;

    private LocalDateFilter takenOn;

    private DoubleFilter monthlyPayable;

    private PaymentStatusFilter paymentStatus;

    private BigDecimalFilter left;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedDate;

    private LongFilter employeeId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public LocalDateFilter getTakenOn() {
        return takenOn;
    }

    public void setTakenOn(LocalDateFilter takenOn) {
        this.takenOn = takenOn;
    }

    public DoubleFilter getMonthlyPayable() {
        return monthlyPayable;
    }

    public void setMonthlyPayable(DoubleFilter monthlyPayable) {
        this.monthlyPayable = monthlyPayable;
    }

    public PaymentStatusFilter getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusFilter paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimalFilter getLeft() {
        return left;
    }

    public void setLeft(BigDecimalFilter left) {
        this.left = left;
    }

    public StringFilter getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(StringFilter modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateFilter getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateFilter modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LoanCriteria that = (LoanCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(takenOn, that.takenOn) &&
            Objects.equals(monthlyPayable, that.monthlyPayable) &&
            Objects.equals(paymentStatus, that.paymentStatus) &&
            Objects.equals(left, that.left) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedDate, that.modifiedDate) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        amount,
        takenOn,
        monthlyPayable,
        paymentStatus,
        left,
        modifiedBy,
        modifiedDate,
        employeeId
        );
    }

    @Override
    public String toString() {
        return "LoanCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (takenOn != null ? "takenOn=" + takenOn + ", " : "") +
                (monthlyPayable != null ? "monthlyPayable=" + monthlyPayable + ", " : "") +
                (paymentStatus != null ? "paymentStatus=" + paymentStatus + ", " : "") +
                (left != null ? "left=" + left + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedDate != null ? "modifiedDate=" + modifiedDate + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
