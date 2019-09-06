package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.BillClosingFlag;
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
 * Criteria class for the DebtorLedger entity. This class is used in DebtorLedgerResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /debtor-ledgers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DebtorLedgerCriteria implements Serializable {
    /**
     * Class for filtering BillClosingFlag
     */
    public static class BillClosingFlagFilter extends Filter<BillClosingFlag> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter serialNo;

    private StringFilter billNo;

    private LocalDateFilter billDate;

    private BigDecimalFilter amount;

    private BigDecimalFilter paidAmount;

    private BillClosingFlagFilter billClosingFlag;

    private LocalDateFilter dueDate;

    private StringFilter vatNo;

    private StringFilter contCode;

    private StringFilter orderNo;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter customerId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(StringFilter serialNo) {
        this.serialNo = serialNo;
    }

    public StringFilter getBillNo() {
        return billNo;
    }

    public void setBillNo(StringFilter billNo) {
        this.billNo = billNo;
    }

    public LocalDateFilter getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateFilter billDate) {
        this.billDate = billDate;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public BigDecimalFilter getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimalFilter paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BillClosingFlagFilter getBillClosingFlag() {
        return billClosingFlag;
    }

    public void setBillClosingFlag(BillClosingFlagFilter billClosingFlag) {
        this.billClosingFlag = billClosingFlag;
    }

    public LocalDateFilter getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateFilter dueDate) {
        this.dueDate = dueDate;
    }

    public StringFilter getVatNo() {
        return vatNo;
    }

    public void setVatNo(StringFilter vatNo) {
        this.vatNo = vatNo;
    }

    public StringFilter getContCode() {
        return contCode;
    }

    public void setContCode(StringFilter contCode) {
        this.contCode = contCode;
    }

    public StringFilter getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(StringFilter orderNo) {
        this.orderNo = orderNo;
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

    public LongFilter getCustomerId() {
        return customerId;
    }

    public void setCustomerId(LongFilter customerId) {
        this.customerId = customerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DebtorLedgerCriteria that = (DebtorLedgerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(serialNo, that.serialNo) &&
            Objects.equals(billNo, that.billNo) &&
            Objects.equals(billDate, that.billDate) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(paidAmount, that.paidAmount) &&
            Objects.equals(billClosingFlag, that.billClosingFlag) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(vatNo, that.vatNo) &&
            Objects.equals(contCode, that.contCode) &&
            Objects.equals(orderNo, that.orderNo) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        serialNo,
        billNo,
        billDate,
        amount,
        paidAmount,
        billClosingFlag,
        dueDate,
        vatNo,
        contCode,
        orderNo,
        modifiedBy,
        modifiedOn,
        customerId
        );
    }

    @Override
    public String toString() {
        return "DebtorLedgerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (serialNo != null ? "serialNo=" + serialNo + ", " : "") +
                (billNo != null ? "billNo=" + billNo + ", " : "") +
                (billDate != null ? "billDate=" + billDate + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (paidAmount != null ? "paidAmount=" + paidAmount + ", " : "") +
                (billClosingFlag != null ? "billClosingFlag=" + billClosingFlag + ", " : "") +
                (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
                (vatNo != null ? "vatNo=" + vatNo + ", " : "") +
                (contCode != null ? "contCode=" + contCode + ", " : "") +
                (orderNo != null ? "orderNo=" + orderNo + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (customerId != null ? "customerId=" + customerId + ", " : "") +
            "}";
    }

}
