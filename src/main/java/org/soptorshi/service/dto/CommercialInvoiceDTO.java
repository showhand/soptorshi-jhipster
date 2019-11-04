package org.soptorshi.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the CommercialInvoice entity.
 */
public class CommercialInvoiceDTO implements Serializable {

    private Long id;

    @NotNull
    private String invoiceNo;

    @NotNull
    private String invoiceDate;

    @NotNull
    private String termsOfPayment;

    @NotNull
    private String consignedTo;

    @NotNull
    private String portOfLoading;

    @NotNull
    private String portOfDischarge;

    @NotNull
    private String exportRegistrationCertificateNo;

    private String createdBy;

    private LocalDate createOn;

    private String updatedBy;

    private LocalDate updatedOn;


    private Long commercialPurchaseOrderId;

    private String commercialPurchaseOrderPurchaseOrderNo;

    private Long commercialProformaInvoiceId;

    private String commercialProformaInvoiceProformaNo;

    private Long commercialPackagingId;

    private String commercialPackagingConsignmentNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getTermsOfPayment() {
        return termsOfPayment;
    }

    public void setTermsOfPayment(String termsOfPayment) {
        this.termsOfPayment = termsOfPayment;
    }

    public String getConsignedTo() {
        return consignedTo;
    }

    public void setConsignedTo(String consignedTo) {
        this.consignedTo = consignedTo;
    }

    public String getPortOfLoading() {
        return portOfLoading;
    }

    public void setPortOfLoading(String portOfLoading) {
        this.portOfLoading = portOfLoading;
    }

    public String getPortOfDischarge() {
        return portOfDischarge;
    }

    public void setPortOfDischarge(String portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
    }

    public String getExportRegistrationCertificateNo() {
        return exportRegistrationCertificateNo;
    }

    public void setExportRegistrationCertificateNo(String exportRegistrationCertificateNo) {
        this.exportRegistrationCertificateNo = exportRegistrationCertificateNo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDate createOn) {
        this.createOn = createOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getCommercialPurchaseOrderId() {
        return commercialPurchaseOrderId;
    }

    public void setCommercialPurchaseOrderId(Long commercialPurchaseOrderId) {
        this.commercialPurchaseOrderId = commercialPurchaseOrderId;
    }

    public String getCommercialPurchaseOrderPurchaseOrderNo() {
        return commercialPurchaseOrderPurchaseOrderNo;
    }

    public void setCommercialPurchaseOrderPurchaseOrderNo(String commercialPurchaseOrderPurchaseOrderNo) {
        this.commercialPurchaseOrderPurchaseOrderNo = commercialPurchaseOrderPurchaseOrderNo;
    }

    public Long getCommercialProformaInvoiceId() {
        return commercialProformaInvoiceId;
    }

    public void setCommercialProformaInvoiceId(Long commercialProformaInvoiceId) {
        this.commercialProformaInvoiceId = commercialProformaInvoiceId;
    }

    public String getCommercialProformaInvoiceProformaNo() {
        return commercialProformaInvoiceProformaNo;
    }

    public void setCommercialProformaInvoiceProformaNo(String commercialProformaInvoiceProformaNo) {
        this.commercialProformaInvoiceProformaNo = commercialProformaInvoiceProformaNo;
    }

    public Long getCommercialPackagingId() {
        return commercialPackagingId;
    }

    public void setCommercialPackagingId(Long commercialPackagingId) {
        this.commercialPackagingId = commercialPackagingId;
    }

    public String getCommercialPackagingConsignmentNo() {
        return commercialPackagingConsignmentNo;
    }

    public void setCommercialPackagingConsignmentNo(String commercialPackagingConsignmentNo) {
        this.commercialPackagingConsignmentNo = commercialPackagingConsignmentNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommercialInvoiceDTO commercialInvoiceDTO = (CommercialInvoiceDTO) o;
        if (commercialInvoiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialInvoiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialInvoiceDTO{" +
            "id=" + getId() +
            ", invoiceNo='" + getInvoiceNo() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", termsOfPayment='" + getTermsOfPayment() + "'" +
            ", consignedTo='" + getConsignedTo() + "'" +
            ", portOfLoading='" + getPortOfLoading() + "'" +
            ", portOfDischarge='" + getPortOfDischarge() + "'" +
            ", exportRegistrationCertificateNo='" + getExportRegistrationCertificateNo() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createOn='" + getCreateOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", commercialPurchaseOrder=" + getCommercialPurchaseOrderId() +
            ", commercialPurchaseOrder='" + getCommercialPurchaseOrderPurchaseOrderNo() + "'" +
            ", commercialProformaInvoice=" + getCommercialProformaInvoiceId() +
            ", commercialProformaInvoice='" + getCommercialProformaInvoiceProformaNo() + "'" +
            ", commercialPackaging=" + getCommercialPackagingId() +
            ", commercialPackaging='" + getCommercialPackagingConsignmentNo() + "'" +
            "}";
    }
}
