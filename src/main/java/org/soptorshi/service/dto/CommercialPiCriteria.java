package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.CommercialPiStatus;
import org.soptorshi.domain.enumeration.PaymentType;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the CommercialPi entity. This class is used in CommercialPiResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /commercial-pis?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommercialPiCriteria implements Serializable {
    /**
     * Class for filtering PaymentType
     */
    public static class PaymentTypeFilter extends Filter<PaymentType> {
    }
    /**
     * Class for filtering CommercialPiStatus
     */
    public static class CommercialPiStatusFilter extends Filter<CommercialPiStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter companyName;

    private StringFilter companyDescription;

    private StringFilter proformaNo;

    private LocalDateFilter proformaDate;

    private StringFilter harmonicCode;

    private PaymentTypeFilter paymentType;

    private StringFilter paymentTerm;

    private StringFilter termsOfDelivery;

    private StringFilter shipmentDate;

    private StringFilter portOfLoading;

    private StringFilter portOfDestination;

    private StringFilter purchaseOrderNo;

    private CommercialPiStatusFilter piStatus;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private LongFilter commercialBudgetId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCompanyName() {
        return companyName;
    }

    public void setCompanyName(StringFilter companyName) {
        this.companyName = companyName;
    }

    public StringFilter getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(StringFilter companyDescription) {
        this.companyDescription = companyDescription;
    }

    public StringFilter getProformaNo() {
        return proformaNo;
    }

    public void setProformaNo(StringFilter proformaNo) {
        this.proformaNo = proformaNo;
    }

    public LocalDateFilter getProformaDate() {
        return proformaDate;
    }

    public void setProformaDate(LocalDateFilter proformaDate) {
        this.proformaDate = proformaDate;
    }

    public StringFilter getHarmonicCode() {
        return harmonicCode;
    }

    public void setHarmonicCode(StringFilter harmonicCode) {
        this.harmonicCode = harmonicCode;
    }

    public PaymentTypeFilter getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentTypeFilter paymentType) {
        this.paymentType = paymentType;
    }

    public StringFilter getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(StringFilter paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public StringFilter getTermsOfDelivery() {
        return termsOfDelivery;
    }

    public void setTermsOfDelivery(StringFilter termsOfDelivery) {
        this.termsOfDelivery = termsOfDelivery;
    }

    public StringFilter getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(StringFilter shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public StringFilter getPortOfLoading() {
        return portOfLoading;
    }

    public void setPortOfLoading(StringFilter portOfLoading) {
        this.portOfLoading = portOfLoading;
    }

    public StringFilter getPortOfDestination() {
        return portOfDestination;
    }

    public void setPortOfDestination(StringFilter portOfDestination) {
        this.portOfDestination = portOfDestination;
    }

    public StringFilter getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(StringFilter purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public CommercialPiStatusFilter getPiStatus() {
        return piStatus;
    }

    public void setPiStatus(CommercialPiStatusFilter piStatus) {
        this.piStatus = piStatus;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public InstantFilter getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(InstantFilter updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LongFilter getCommercialBudgetId() {
        return commercialBudgetId;
    }

    public void setCommercialBudgetId(LongFilter commercialBudgetId) {
        this.commercialBudgetId = commercialBudgetId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommercialPiCriteria that = (CommercialPiCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(companyDescription, that.companyDescription) &&
            Objects.equals(proformaNo, that.proformaNo) &&
            Objects.equals(proformaDate, that.proformaDate) &&
            Objects.equals(harmonicCode, that.harmonicCode) &&
            Objects.equals(paymentType, that.paymentType) &&
            Objects.equals(paymentTerm, that.paymentTerm) &&
            Objects.equals(termsOfDelivery, that.termsOfDelivery) &&
            Objects.equals(shipmentDate, that.shipmentDate) &&
            Objects.equals(portOfLoading, that.portOfLoading) &&
            Objects.equals(portOfDestination, that.portOfDestination) &&
            Objects.equals(purchaseOrderNo, that.purchaseOrderNo) &&
            Objects.equals(piStatus, that.piStatus) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(commercialBudgetId, that.commercialBudgetId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        companyName,
        companyDescription,
        proformaNo,
        proformaDate,
        harmonicCode,
        paymentType,
        paymentTerm,
        termsOfDelivery,
        shipmentDate,
        portOfLoading,
        portOfDestination,
        purchaseOrderNo,
        piStatus,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        commercialBudgetId
        );
    }

    @Override
    public String toString() {
        return "CommercialPiCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (companyName != null ? "companyName=" + companyName + ", " : "") +
                (companyDescription != null ? "companyDescription=" + companyDescription + ", " : "") +
                (proformaNo != null ? "proformaNo=" + proformaNo + ", " : "") +
                (proformaDate != null ? "proformaDate=" + proformaDate + ", " : "") +
                (harmonicCode != null ? "harmonicCode=" + harmonicCode + ", " : "") +
                (paymentType != null ? "paymentType=" + paymentType + ", " : "") +
                (paymentTerm != null ? "paymentTerm=" + paymentTerm + ", " : "") +
                (termsOfDelivery != null ? "termsOfDelivery=" + termsOfDelivery + ", " : "") +
                (shipmentDate != null ? "shipmentDate=" + shipmentDate + ", " : "") +
                (portOfLoading != null ? "portOfLoading=" + portOfLoading + ", " : "") +
                (portOfDestination != null ? "portOfDestination=" + portOfDestination + ", " : "") +
                (purchaseOrderNo != null ? "purchaseOrderNo=" + purchaseOrderNo + ", " : "") +
                (piStatus != null ? "piStatus=" + piStatus + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (commercialBudgetId != null ? "commercialBudgetId=" + commercialBudgetId + ", " : "") +
            "}";
    }

}
