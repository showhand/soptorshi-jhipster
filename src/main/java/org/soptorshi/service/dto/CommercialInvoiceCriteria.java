package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the CommercialInvoice entity. This class is used in CommercialInvoiceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /commercial-invoices?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommercialInvoiceCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter invoiceNo;

    private StringFilter invoiceDate;

    private StringFilter termsOfPayment;

    private StringFilter consignedTo;

    private StringFilter portOfLoading;

    private StringFilter portOfDischarge;

    private StringFilter exportRegistrationCertificateNo;

    private StringFilter createdBy;

    private LocalDateFilter createOn;

    private StringFilter updatedBy;

    private StringFilter updatedOn;

    private LongFilter commercialPurchaseOrderId;

    private LongFilter commercialProformaInvoiceId;

    private LongFilter commercialPackagingId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(StringFilter invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public StringFilter getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(StringFilter invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public StringFilter getTermsOfPayment() {
        return termsOfPayment;
    }

    public void setTermsOfPayment(StringFilter termsOfPayment) {
        this.termsOfPayment = termsOfPayment;
    }

    public StringFilter getConsignedTo() {
        return consignedTo;
    }

    public void setConsignedTo(StringFilter consignedTo) {
        this.consignedTo = consignedTo;
    }

    public StringFilter getPortOfLoading() {
        return portOfLoading;
    }

    public void setPortOfLoading(StringFilter portOfLoading) {
        this.portOfLoading = portOfLoading;
    }

    public StringFilter getPortOfDischarge() {
        return portOfDischarge;
    }

    public void setPortOfDischarge(StringFilter portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
    }

    public StringFilter getExportRegistrationCertificateNo() {
        return exportRegistrationCertificateNo;
    }

    public void setExportRegistrationCertificateNo(StringFilter exportRegistrationCertificateNo) {
        this.exportRegistrationCertificateNo = exportRegistrationCertificateNo;
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

    public StringFilter getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(StringFilter updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LongFilter getCommercialPurchaseOrderId() {
        return commercialPurchaseOrderId;
    }

    public void setCommercialPurchaseOrderId(LongFilter commercialPurchaseOrderId) {
        this.commercialPurchaseOrderId = commercialPurchaseOrderId;
    }

    public LongFilter getCommercialProformaInvoiceId() {
        return commercialProformaInvoiceId;
    }

    public void setCommercialProformaInvoiceId(LongFilter commercialProformaInvoiceId) {
        this.commercialProformaInvoiceId = commercialProformaInvoiceId;
    }

    public LongFilter getCommercialPackagingId() {
        return commercialPackagingId;
    }

    public void setCommercialPackagingId(LongFilter commercialPackagingId) {
        this.commercialPackagingId = commercialPackagingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommercialInvoiceCriteria that = (CommercialInvoiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceNo, that.invoiceNo) &&
            Objects.equals(invoiceDate, that.invoiceDate) &&
            Objects.equals(termsOfPayment, that.termsOfPayment) &&
            Objects.equals(consignedTo, that.consignedTo) &&
            Objects.equals(portOfLoading, that.portOfLoading) &&
            Objects.equals(portOfDischarge, that.portOfDischarge) &&
            Objects.equals(exportRegistrationCertificateNo, that.exportRegistrationCertificateNo) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createOn, that.createOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(commercialPurchaseOrderId, that.commercialPurchaseOrderId) &&
            Objects.equals(commercialProformaInvoiceId, that.commercialProformaInvoiceId) &&
            Objects.equals(commercialPackagingId, that.commercialPackagingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        invoiceNo,
        invoiceDate,
        termsOfPayment,
        consignedTo,
        portOfLoading,
        portOfDischarge,
        exportRegistrationCertificateNo,
        createdBy,
        createOn,
        updatedBy,
        updatedOn,
        commercialPurchaseOrderId,
        commercialProformaInvoiceId,
        commercialPackagingId
        );
    }

    @Override
    public String toString() {
        return "CommercialInvoiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (invoiceNo != null ? "invoiceNo=" + invoiceNo + ", " : "") +
                (invoiceDate != null ? "invoiceDate=" + invoiceDate + ", " : "") +
                (termsOfPayment != null ? "termsOfPayment=" + termsOfPayment + ", " : "") +
                (consignedTo != null ? "consignedTo=" + consignedTo + ", " : "") +
                (portOfLoading != null ? "portOfLoading=" + portOfLoading + ", " : "") +
                (portOfDischarge != null ? "portOfDischarge=" + portOfDischarge + ", " : "") +
                (exportRegistrationCertificateNo != null ? "exportRegistrationCertificateNo=" + exportRegistrationCertificateNo + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createOn != null ? "createOn=" + createOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (commercialPurchaseOrderId != null ? "commercialPurchaseOrderId=" + commercialPurchaseOrderId + ", " : "") +
                (commercialProformaInvoiceId != null ? "commercialProformaInvoiceId=" + commercialProformaInvoiceId + ", " : "") +
                (commercialPackagingId != null ? "commercialPackagingId=" + commercialPackagingId + ", " : "") +
            "}";
    }

}
