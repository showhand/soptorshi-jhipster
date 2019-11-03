package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.CommercialCurrency;
import org.soptorshi.domain.enumeration.CommercialPaymentCategory;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the CommercialPaymentInfo entity. This class is used in CommercialPaymentInfoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /commercial-payment-infos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommercialPaymentInfoCriteria implements Serializable {
    /**
     * Class for filtering CommercialPaymentCategory
     */
    public static class CommercialPaymentCategoryFilter extends Filter<CommercialPaymentCategory> {
    }
    /**
     * Class for filtering CommercialCurrency
     */
    public static class CommercialCurrencyFilter extends Filter<CommercialCurrency> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private CommercialPaymentCategoryFilter paymentCategory;

    private DoubleFilter totalAmount;

    private CommercialCurrencyFilter currencyType;

    private StringFilter paymentTerms;

    private StringFilter createdBy;

    private LocalDateFilter createOn;

    private StringFilter updatedBy;

    private LocalDateFilter updatedOn;

    private LongFilter commercialPurchaseOrderId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public CommercialPaymentCategoryFilter getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(CommercialPaymentCategoryFilter paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public DoubleFilter getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(DoubleFilter totalAmount) {
        this.totalAmount = totalAmount;
    }

    public CommercialCurrencyFilter getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CommercialCurrencyFilter currencyType) {
        this.currencyType = currencyType;
    }

    public StringFilter getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(StringFilter paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateFilter getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateFilter createOn) {
        this.createOn = createOn;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateFilter getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateFilter updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LongFilter getCommercialPurchaseOrderId() {
        return commercialPurchaseOrderId;
    }

    public void setCommercialPurchaseOrderId(LongFilter commercialPurchaseOrderId) {
        this.commercialPurchaseOrderId = commercialPurchaseOrderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommercialPaymentInfoCriteria that = (CommercialPaymentInfoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(paymentCategory, that.paymentCategory) &&
            Objects.equals(totalAmount, that.totalAmount) &&
            Objects.equals(currencyType, that.currencyType) &&
            Objects.equals(paymentTerms, that.paymentTerms) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createOn, that.createOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(commercialPurchaseOrderId, that.commercialPurchaseOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        paymentCategory,
        totalAmount,
        currencyType,
        paymentTerms,
        createdBy,
        createOn,
        updatedBy,
        updatedOn,
        commercialPurchaseOrderId
        );
    }

    @Override
    public String toString() {
        return "CommercialPaymentInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (paymentCategory != null ? "paymentCategory=" + paymentCategory + ", " : "") +
                (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "") +
                (currencyType != null ? "currencyType=" + currencyType + ", " : "") +
                (paymentTerms != null ? "paymentTerms=" + paymentTerms + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createOn != null ? "createOn=" + createOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (commercialPurchaseOrderId != null ? "commercialPurchaseOrderId=" + commercialPurchaseOrderId + ", " : "") +
            "}";
    }

}
