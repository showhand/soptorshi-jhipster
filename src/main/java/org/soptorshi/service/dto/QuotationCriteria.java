package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.Currency;
import org.soptorshi.domain.enumeration.PayType;
import org.soptorshi.domain.enumeration.VatStatus;
import org.soptorshi.domain.enumeration.AITStatus;
import org.soptorshi.domain.enumeration.WarrantyStatus;
import org.soptorshi.domain.enumeration.SelectionType;
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
 * Criteria class for the Quotation entity. This class is used in QuotationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /quotations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuotationCriteria implements Serializable {
    /**
     * Class for filtering Currency
     */
    public static class CurrencyFilter extends Filter<Currency> {
    }
    /**
     * Class for filtering PayType
     */
    public static class PayTypeFilter extends Filter<PayType> {
    }
    /**
     * Class for filtering VatStatus
     */
    public static class VatStatusFilter extends Filter<VatStatus> {
    }
    /**
     * Class for filtering AITStatus
     */
    public static class AITStatusFilter extends Filter<AITStatus> {
    }
    /**
     * Class for filtering WarrantyStatus
     */
    public static class WarrantyStatusFilter extends Filter<WarrantyStatus> {
    }
    /**
     * Class for filtering SelectionType
     */
    public static class SelectionTypeFilter extends Filter<SelectionType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter quotationNo;

    private CurrencyFilter currency;

    private PayTypeFilter payType;

    private BigDecimalFilter creditLimit;

    private VatStatusFilter vatStatus;

    private AITStatusFilter aitStatus;

    private WarrantyStatusFilter warrantyStatus;

    private StringFilter loadingPort;

    private SelectionTypeFilter selectionStatus;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter requisitionId;

    private LongFilter vendorId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getQuotationNo() {
        return quotationNo;
    }

    public void setQuotationNo(StringFilter quotationNo) {
        this.quotationNo = quotationNo;
    }

    public CurrencyFilter getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyFilter currency) {
        this.currency = currency;
    }

    public PayTypeFilter getPayType() {
        return payType;
    }

    public void setPayType(PayTypeFilter payType) {
        this.payType = payType;
    }

    public BigDecimalFilter getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimalFilter creditLimit) {
        this.creditLimit = creditLimit;
    }

    public VatStatusFilter getVatStatus() {
        return vatStatus;
    }

    public void setVatStatus(VatStatusFilter vatStatus) {
        this.vatStatus = vatStatus;
    }

    public AITStatusFilter getAitStatus() {
        return aitStatus;
    }

    public void setAitStatus(AITStatusFilter aitStatus) {
        this.aitStatus = aitStatus;
    }

    public WarrantyStatusFilter getWarrantyStatus() {
        return warrantyStatus;
    }

    public void setWarrantyStatus(WarrantyStatusFilter warrantyStatus) {
        this.warrantyStatus = warrantyStatus;
    }

    public StringFilter getLoadingPort() {
        return loadingPort;
    }

    public void setLoadingPort(StringFilter loadingPort) {
        this.loadingPort = loadingPort;
    }

    public SelectionTypeFilter getSelectionStatus() {
        return selectionStatus;
    }

    public void setSelectionStatus(SelectionTypeFilter selectionStatus) {
        this.selectionStatus = selectionStatus;
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

    public LongFilter getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(LongFilter requisitionId) {
        this.requisitionId = requisitionId;
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
        final QuotationCriteria that = (QuotationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quotationNo, that.quotationNo) &&
            Objects.equals(currency, that.currency) &&
            Objects.equals(payType, that.payType) &&
            Objects.equals(creditLimit, that.creditLimit) &&
            Objects.equals(vatStatus, that.vatStatus) &&
            Objects.equals(aitStatus, that.aitStatus) &&
            Objects.equals(warrantyStatus, that.warrantyStatus) &&
            Objects.equals(loadingPort, that.loadingPort) &&
            Objects.equals(selectionStatus, that.selectionStatus) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(requisitionId, that.requisitionId) &&
            Objects.equals(vendorId, that.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quotationNo,
        currency,
        payType,
        creditLimit,
        vatStatus,
        aitStatus,
        warrantyStatus,
        loadingPort,
        selectionStatus,
        modifiedBy,
        modifiedOn,
        requisitionId,
        vendorId
        );
    }

    @Override
    public String toString() {
        return "QuotationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quotationNo != null ? "quotationNo=" + quotationNo + ", " : "") +
                (currency != null ? "currency=" + currency + ", " : "") +
                (payType != null ? "payType=" + payType + ", " : "") +
                (creditLimit != null ? "creditLimit=" + creditLimit + ", " : "") +
                (vatStatus != null ? "vatStatus=" + vatStatus + ", " : "") +
                (aitStatus != null ? "aitStatus=" + aitStatus + ", " : "") +
                (warrantyStatus != null ? "warrantyStatus=" + warrantyStatus + ", " : "") +
                (loadingPort != null ? "loadingPort=" + loadingPort + ", " : "") +
                (selectionStatus != null ? "selectionStatus=" + selectionStatus + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (requisitionId != null ? "requisitionId=" + requisitionId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}
