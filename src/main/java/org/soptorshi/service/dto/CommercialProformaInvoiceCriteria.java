package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the CommercialProformaInvoice entity. This class is used in CommercialProformaInvoiceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /commercial-proforma-invoices?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommercialProformaInvoiceCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter companyName;

    private StringFilter companyDescriptionOrAddress;

    private StringFilter proformaNo;

    private LocalDateFilter proformaDate;

    private StringFilter harmonicCode;

    private StringFilter paymentTerm;

    private StringFilter termsOfDelivery;

    private StringFilter shipmentDate;

    private StringFilter portOfLanding;

    private StringFilter portOfDestination;

    private StringFilter createdBy;

    private LocalDateFilter createdOn;

    private StringFilter updatedBy;

    private LocalDateFilter updatedOn;

    private LongFilter commercialPurchaseOrderId;

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

    public StringFilter getCompanyDescriptionOrAddress() {
        return companyDescriptionOrAddress;
    }

    public void setCompanyDescriptionOrAddress(StringFilter companyDescriptionOrAddress) {
        this.companyDescriptionOrAddress = companyDescriptionOrAddress;
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

    public StringFilter getPortOfLanding() {
        return portOfLanding;
    }

    public void setPortOfLanding(StringFilter portOfLanding) {
        this.portOfLanding = portOfLanding;
    }

    public StringFilter getPortOfDestination() {
        return portOfDestination;
    }

    public void setPortOfDestination(StringFilter portOfDestination) {
        this.portOfDestination = portOfDestination;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateFilter getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateFilter createdOn) {
        this.createdOn = createdOn;
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
        final CommercialProformaInvoiceCriteria that = (CommercialProformaInvoiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(companyDescriptionOrAddress, that.companyDescriptionOrAddress) &&
            Objects.equals(proformaNo, that.proformaNo) &&
            Objects.equals(proformaDate, that.proformaDate) &&
            Objects.equals(harmonicCode, that.harmonicCode) &&
            Objects.equals(paymentTerm, that.paymentTerm) &&
            Objects.equals(termsOfDelivery, that.termsOfDelivery) &&
            Objects.equals(shipmentDate, that.shipmentDate) &&
            Objects.equals(portOfLanding, that.portOfLanding) &&
            Objects.equals(portOfDestination, that.portOfDestination) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(commercialPurchaseOrderId, that.commercialPurchaseOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        companyName,
        companyDescriptionOrAddress,
        proformaNo,
        proformaDate,
        harmonicCode,
        paymentTerm,
        termsOfDelivery,
        shipmentDate,
        portOfLanding,
        portOfDestination,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        commercialPurchaseOrderId
        );
    }

    @Override
    public String toString() {
        return "CommercialProformaInvoiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (companyName != null ? "companyName=" + companyName + ", " : "") +
                (companyDescriptionOrAddress != null ? "companyDescriptionOrAddress=" + companyDescriptionOrAddress + ", " : "") +
                (proformaNo != null ? "proformaNo=" + proformaNo + ", " : "") +
                (proformaDate != null ? "proformaDate=" + proformaDate + ", " : "") +
                (harmonicCode != null ? "harmonicCode=" + harmonicCode + ", " : "") +
                (paymentTerm != null ? "paymentTerm=" + paymentTerm + ", " : "") +
                (termsOfDelivery != null ? "termsOfDelivery=" + termsOfDelivery + ", " : "") +
                (shipmentDate != null ? "shipmentDate=" + shipmentDate + ", " : "") +
                (portOfLanding != null ? "portOfLanding=" + portOfLanding + ", " : "") +
                (portOfDestination != null ? "portOfDestination=" + portOfDestination + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (commercialPurchaseOrderId != null ? "commercialPurchaseOrderId=" + commercialPurchaseOrderId + ", " : "") +
            "}";
    }

}
