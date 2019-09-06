package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.BalanceType;
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
 * Criteria class for the CreditorLedger entity. This class is used in CreditorLedgerResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /creditor-ledgers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CreditorLedgerCriteria implements Serializable {
    /**
     * Class for filtering BalanceType
     */
    public static class BalanceTypeFilter extends Filter<BalanceType> {
    }
    /**
     * Class for filtering BillClosingFlag
     */
    public static class BillClosingFlagFilter extends Filter<BillClosingFlag> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter serialNo;

    private StringFilter billNo;

    private LocalDateFilter billDate;

    private BigDecimalFilter amount;

    private BigDecimalFilter paidAmount;

    private BalanceTypeFilter balanceType;

    private BillClosingFlagFilter billClosingFlag;

    private LocalDateFilter dueDate;

    private StringFilter vatNo;

    private StringFilter contCode;

    private StringFilter orderNo;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter vendorId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(IntegerFilter serialNo) {
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

    public BalanceTypeFilter getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(BalanceTypeFilter balanceType) {
        this.balanceType = balanceType;
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

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CreditorLedgerCriteria that = (CreditorLedgerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(serialNo, that.serialNo) &&
            Objects.equals(billNo, that.billNo) &&
            Objects.equals(billDate, that.billDate) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(paidAmount, that.paidAmount) &&
            Objects.equals(balanceType, that.balanceType) &&
            Objects.equals(billClosingFlag, that.billClosingFlag) &&
            Objects.equals(dueDate, that.dueDate) &&
            Objects.equals(vatNo, that.vatNo) &&
            Objects.equals(contCode, that.contCode) &&
            Objects.equals(orderNo, that.orderNo) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(vendorId, that.vendorId);
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
        balanceType,
        billClosingFlag,
        dueDate,
        vatNo,
        contCode,
        orderNo,
        modifiedBy,
        modifiedOn,
        vendorId
        );
    }

    @Override
    public String toString() {
        return "CreditorLedgerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (serialNo != null ? "serialNo=" + serialNo + ", " : "") +
                (billNo != null ? "billNo=" + billNo + ", " : "") +
                (billDate != null ? "billDate=" + billDate + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (paidAmount != null ? "paidAmount=" + paidAmount + ", " : "") +
                (balanceType != null ? "balanceType=" + balanceType + ", " : "") +
                (billClosingFlag != null ? "billClosingFlag=" + billClosingFlag + ", " : "") +
                (dueDate != null ? "dueDate=" + dueDate + ", " : "") +
                (vatNo != null ? "vatNo=" + vatNo + ", " : "") +
                (contCode != null ? "contCode=" + contCode + ", " : "") +
                (orderNo != null ? "orderNo=" + orderNo + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}
