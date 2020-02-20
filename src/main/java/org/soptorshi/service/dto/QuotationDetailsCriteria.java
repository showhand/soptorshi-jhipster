package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.Currency;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;
import org.soptorshi.domain.enumeration.PayType;
import org.soptorshi.domain.enumeration.VatStatus;
import org.soptorshi.domain.enumeration.AITStatus;
import org.soptorshi.domain.enumeration.WarrantyStatus;
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
 * Criteria class for the QuotationDetails entity. This class is used in QuotationDetailsResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /quotation-details?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuotationDetailsCriteria implements Serializable {
    /**
     * Class for filtering Currency
     */
    public static class CurrencyFilter extends Filter<Currency> {
    }
    /**
     * Class for filtering UnitOfMeasurements
     */
    public static class UnitOfMeasurementsFilter extends Filter<UnitOfMeasurements> {
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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private CurrencyFilter currency;

    private BigDecimalFilter rate;

    private UnitOfMeasurementsFilter unitOfMeasurements;

    private IntegerFilter quantity;

    private PayTypeFilter payType;

    private BigDecimalFilter creditLimit;

    private VatStatusFilter vatStatus;

    private BigDecimalFilter vatPercentage;

    private AITStatusFilter aitStatus;

    private BigDecimalFilter aitPercentage;

    private LocalDateFilter estimatedDate;

    private WarrantyStatusFilter warrantyStatus;

    private StringFilter loadingPort;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter quotationId;

    private LongFilter requisitionDetailsId;

    private LongFilter productId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public CurrencyFilter getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyFilter currency) {
        this.currency = currency;
    }

    public BigDecimalFilter getRate() {
        return rate;
    }

    public void setRate(BigDecimalFilter rate) {
        this.rate = rate;
    }

    public UnitOfMeasurementsFilter getUnitOfMeasurements() {
        return unitOfMeasurements;
    }

    public void setUnitOfMeasurements(UnitOfMeasurementsFilter unitOfMeasurements) {
        this.unitOfMeasurements = unitOfMeasurements;
    }

    public IntegerFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(IntegerFilter quantity) {
        this.quantity = quantity;
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

    public BigDecimalFilter getVatPercentage() {
        return vatPercentage;
    }

    public void setVatPercentage(BigDecimalFilter vatPercentage) {
        this.vatPercentage = vatPercentage;
    }

    public AITStatusFilter getAitStatus() {
        return aitStatus;
    }

    public void setAitStatus(AITStatusFilter aitStatus) {
        this.aitStatus = aitStatus;
    }

    public BigDecimalFilter getAitPercentage() {
        return aitPercentage;
    }

    public void setAitPercentage(BigDecimalFilter aitPercentage) {
        this.aitPercentage = aitPercentage;
    }

    public LocalDateFilter getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(LocalDateFilter estimatedDate) {
        this.estimatedDate = estimatedDate;
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

    public LongFilter getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(LongFilter quotationId) {
        this.quotationId = quotationId;
    }

    public LongFilter getRequisitionDetailsId() {
        return requisitionDetailsId;
    }

    public void setRequisitionDetailsId(LongFilter requisitionDetailsId) {
        this.requisitionDetailsId = requisitionDetailsId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QuotationDetailsCriteria that = (QuotationDetailsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(currency, that.currency) &&
            Objects.equals(rate, that.rate) &&
            Objects.equals(unitOfMeasurements, that.unitOfMeasurements) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(payType, that.payType) &&
            Objects.equals(creditLimit, that.creditLimit) &&
            Objects.equals(vatStatus, that.vatStatus) &&
            Objects.equals(vatPercentage, that.vatPercentage) &&
            Objects.equals(aitStatus, that.aitStatus) &&
            Objects.equals(aitPercentage, that.aitPercentage) &&
            Objects.equals(estimatedDate, that.estimatedDate) &&
            Objects.equals(warrantyStatus, that.warrantyStatus) &&
            Objects.equals(loadingPort, that.loadingPort) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(quotationId, that.quotationId) &&
            Objects.equals(requisitionDetailsId, that.requisitionDetailsId) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        currency,
        rate,
        unitOfMeasurements,
        quantity,
        payType,
        creditLimit,
        vatStatus,
        vatPercentage,
        aitStatus,
        aitPercentage,
        estimatedDate,
        warrantyStatus,
        loadingPort,
        modifiedBy,
        modifiedOn,
        quotationId,
        requisitionDetailsId,
        productId
        );
    }

    @Override
    public String toString() {
        return "QuotationDetailsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (currency != null ? "currency=" + currency + ", " : "") +
                (rate != null ? "rate=" + rate + ", " : "") +
                (unitOfMeasurements != null ? "unitOfMeasurements=" + unitOfMeasurements + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (payType != null ? "payType=" + payType + ", " : "") +
                (creditLimit != null ? "creditLimit=" + creditLimit + ", " : "") +
                (vatStatus != null ? "vatStatus=" + vatStatus + ", " : "") +
                (vatPercentage != null ? "vatPercentage=" + vatPercentage + ", " : "") +
                (aitStatus != null ? "aitStatus=" + aitStatus + ", " : "") +
                (aitPercentage != null ? "aitPercentage=" + aitPercentage + ", " : "") +
                (estimatedDate != null ? "estimatedDate=" + estimatedDate + ", " : "") +
                (warrantyStatus != null ? "warrantyStatus=" + warrantyStatus + ", " : "") +
                (loadingPort != null ? "loadingPort=" + loadingPort + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (quotationId != null ? "quotationId=" + quotationId + ", " : "") +
                (requisitionDetailsId != null ? "requisitionDetailsId=" + requisitionDetailsId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
