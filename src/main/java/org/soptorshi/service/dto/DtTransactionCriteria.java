package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.VoucherType;
import org.soptorshi.domain.enumeration.InstrumentType;
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
 * Criteria class for the DtTransaction entity. This class is used in DtTransactionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /dt-transactions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DtTransactionCriteria implements Serializable {
    /**
     * Class for filtering BalanceType
     */
    public static class BalanceTypeFilter extends Filter<BalanceType> {
    }
    /**
     * Class for filtering VoucherType
     */
    public static class VoucherTypeFilter extends Filter<VoucherType> {
    }
    /**
     * Class for filtering InstrumentType
     */
    public static class InstrumentTypeFilter extends Filter<InstrumentType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter voucherNo;

    private LocalDateFilter voucherDate;

    private IntegerFilter serialNo;

    private BigDecimalFilter amount;

    private BalanceTypeFilter balanceType;

    private VoucherTypeFilter type;

    private StringFilter invoiceNo;

    private LocalDateFilter invoiceDate;

    private InstrumentTypeFilter instrumentType;

    private StringFilter instrumentNo;

    private LocalDateFilter instrumentDate;

    private BigDecimalFilter fCurrency;

    private BigDecimalFilter convFactor;

    private LocalDateFilter postDate;

    private StringFilter narration;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private StringFilter reference;

    private LongFilter accountId;

    private LongFilter voucherId;

    private LongFilter currencyId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(StringFilter voucherNo) {
        this.voucherNo = voucherNo;
    }

    public LocalDateFilter getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(LocalDateFilter voucherDate) {
        this.voucherDate = voucherDate;
    }

    public IntegerFilter getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(IntegerFilter serialNo) {
        this.serialNo = serialNo;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public BalanceTypeFilter getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(BalanceTypeFilter balanceType) {
        this.balanceType = balanceType;
    }

    public VoucherTypeFilter getType() {
        return type;
    }

    public void setType(VoucherTypeFilter type) {
        this.type = type;
    }

    public StringFilter getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(StringFilter invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public LocalDateFilter getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateFilter invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public InstrumentTypeFilter getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(InstrumentTypeFilter instrumentType) {
        this.instrumentType = instrumentType;
    }

    public StringFilter getInstrumentNo() {
        return instrumentNo;
    }

    public void setInstrumentNo(StringFilter instrumentNo) {
        this.instrumentNo = instrumentNo;
    }

    public LocalDateFilter getInstrumentDate() {
        return instrumentDate;
    }

    public void setInstrumentDate(LocalDateFilter instrumentDate) {
        this.instrumentDate = instrumentDate;
    }

    public BigDecimalFilter getfCurrency() {
        return fCurrency;
    }

    public void setfCurrency(BigDecimalFilter fCurrency) {
        this.fCurrency = fCurrency;
    }

    public BigDecimalFilter getConvFactor() {
        return convFactor;
    }

    public void setConvFactor(BigDecimalFilter convFactor) {
        this.convFactor = convFactor;
    }

    public LocalDateFilter getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateFilter postDate) {
        this.postDate = postDate;
    }

    public StringFilter getNarration() {
        return narration;
    }

    public void setNarration(StringFilter narration) {
        this.narration = narration;
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

    public StringFilter getReference() {
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public LongFilter getAccountId() {
        return accountId;
    }

    public void setAccountId(LongFilter accountId) {
        this.accountId = accountId;
    }

    public LongFilter getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(LongFilter voucherId) {
        this.voucherId = voucherId;
    }

    public LongFilter getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(LongFilter currencyId) {
        this.currencyId = currencyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DtTransactionCriteria that = (DtTransactionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(voucherNo, that.voucherNo) &&
            Objects.equals(voucherDate, that.voucherDate) &&
            Objects.equals(serialNo, that.serialNo) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(balanceType, that.balanceType) &&
            Objects.equals(type, that.type) &&
            Objects.equals(invoiceNo, that.invoiceNo) &&
            Objects.equals(invoiceDate, that.invoiceDate) &&
            Objects.equals(instrumentType, that.instrumentType) &&
            Objects.equals(instrumentNo, that.instrumentNo) &&
            Objects.equals(instrumentDate, that.instrumentDate) &&
            Objects.equals(fCurrency, that.fCurrency) &&
            Objects.equals(convFactor, that.convFactor) &&
            Objects.equals(postDate, that.postDate) &&
            Objects.equals(narration, that.narration) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(accountId, that.accountId) &&
            Objects.equals(voucherId, that.voucherId) &&
            Objects.equals(currencyId, that.currencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        voucherNo,
        voucherDate,
        serialNo,
        amount,
        balanceType,
        type,
        invoiceNo,
        invoiceDate,
        instrumentType,
        instrumentNo,
        instrumentDate,
        fCurrency,
        convFactor,
        postDate,
        narration,
        modifiedBy,
        modifiedOn,
        reference,
        accountId,
        voucherId,
        currencyId
        );
    }

    @Override
    public String toString() {
        return "DtTransactionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (voucherNo != null ? "voucherNo=" + voucherNo + ", " : "") +
                (voucherDate != null ? "voucherDate=" + voucherDate + ", " : "") +
                (serialNo != null ? "serialNo=" + serialNo + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (balanceType != null ? "balanceType=" + balanceType + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (invoiceNo != null ? "invoiceNo=" + invoiceNo + ", " : "") +
                (invoiceDate != null ? "invoiceDate=" + invoiceDate + ", " : "") +
                (instrumentType != null ? "instrumentType=" + instrumentType + ", " : "") +
                (instrumentNo != null ? "instrumentNo=" + instrumentNo + ", " : "") +
                (instrumentDate != null ? "instrumentDate=" + instrumentDate + ", " : "") +
                (fCurrency != null ? "fCurrency=" + fCurrency + ", " : "") +
                (convFactor != null ? "convFactor=" + convFactor + ", " : "") +
                (postDate != null ? "postDate=" + postDate + ", " : "") +
                (narration != null ? "narration=" + narration + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (reference != null ? "reference=" + reference + ", " : "") +
                (accountId != null ? "accountId=" + accountId + ", " : "") +
                (voucherId != null ? "voucherId=" + voucherId + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
            "}";
    }

}
