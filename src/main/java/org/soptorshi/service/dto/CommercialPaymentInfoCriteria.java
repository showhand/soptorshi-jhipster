package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.CommercialPaymentStatus;
import org.soptorshi.domain.enumeration.PaymentType;

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
     * Class for filtering PaymentType
     */
    public static class PaymentTypeFilter extends Filter<PaymentType> {
    }
    /**
     * Class for filtering CommercialPaymentStatus
     */
    public static class CommercialPaymentStatusFilter extends Filter<CommercialPaymentStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private PaymentTypeFilter paymentType;

    private BigDecimalFilter totalAmountToPay;

    private BigDecimalFilter totalAmountPaid;

    private BigDecimalFilter remainingAmountToPay;

    private CommercialPaymentStatusFilter paymentStatus;

    private InstantFilter createdOn;

    private StringFilter createdBy;

    private InstantFilter updatedOn;

    private StringFilter updatedBy;

    private LongFilter commercialPiId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public PaymentTypeFilter getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeFilter paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimalFilter getTotalAmountToPay() {
        return totalAmountToPay;
    }

    public void setTotalAmountToPay(BigDecimalFilter totalAmountToPay) {
        this.totalAmountToPay = totalAmountToPay;
    }

    public BigDecimalFilter getTotalAmountPaid() {
        return totalAmountPaid;
    }

    public void setTotalAmountPaid(BigDecimalFilter totalAmountPaid) {
        this.totalAmountPaid = totalAmountPaid;
    }

    public BigDecimalFilter getRemainingAmountToPay() {
        return remainingAmountToPay;
    }

    public void setRemainingAmountToPay(BigDecimalFilter remainingAmountToPay) {
        this.remainingAmountToPay = remainingAmountToPay;
    }

    public CommercialPaymentStatusFilter getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(CommercialPaymentStatusFilter paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(InstantFilter updatedOn) {
        this.updatedOn = updatedOn;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LongFilter getCommercialPiId() {
        return commercialPiId;
    }

    public void setCommercialPiId(LongFilter commercialPiId) {
        this.commercialPiId = commercialPiId;
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
            Objects.equals(paymentType, that.paymentType) &&
            Objects.equals(totalAmountToPay, that.totalAmountToPay) &&
            Objects.equals(totalAmountPaid, that.totalAmountPaid) &&
            Objects.equals(remainingAmountToPay, that.remainingAmountToPay) &&
            Objects.equals(paymentStatus, that.paymentStatus) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(commercialPiId, that.commercialPiId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        paymentType,
        totalAmountToPay,
        totalAmountPaid,
        remainingAmountToPay,
        paymentStatus,
        createdOn,
        createdBy,
        updatedOn,
        updatedBy,
        commercialPiId
        );
    }

    @Override
    public String toString() {
        return "CommercialPaymentInfoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (paymentType != null ? "paymentType=" + paymentType + ", " : "") +
                (totalAmountToPay != null ? "totalAmountToPay=" + totalAmountToPay + ", " : "") +
                (totalAmountPaid != null ? "totalAmountPaid=" + totalAmountPaid + ", " : "") +
                (remainingAmountToPay != null ? "remainingAmountToPay=" + remainingAmountToPay + ", " : "") +
                (paymentStatus != null ? "paymentStatus=" + paymentStatus + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (commercialPiId != null ? "commercialPiId=" + commercialPiId + ", " : "") +
            "}";
    }

}
