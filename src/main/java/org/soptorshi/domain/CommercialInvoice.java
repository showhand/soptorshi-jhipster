package org.soptorshi.domain;


import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CommercialInvoice.
 */
@Entity
@Table(name = "commercial_invoice")
@Document(indexName = "commercialinvoice")
public class CommercialInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "invoice_no", nullable = false)
    private String invoiceNo;

    @NotNull
    @Column(name = "invoice_date", nullable = false)
    private String invoiceDate;

    @NotNull
    @Column(name = "terms_of_payment", nullable = false)
    private String termsOfPayment;

    @NotNull
    @Column(name = "consigned_to", nullable = false)
    private String consignedTo;

    @NotNull
    @Column(name = "port_of_loading", nullable = false)
    private String portOfLoading;

    @NotNull
    @Column(name = "port_of_discharge", nullable = false)
    private String portOfDischarge;

    @NotNull
    @Column(name = "export_registration_certificate_no", nullable = false)
    private String exportRegistrationCertificateNo;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "create_on")
    private LocalDate createOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    @OneToOne
    @JoinColumn(unique = true)
    private CommercialPurchaseOrder commercialPurchaseOrder;

    @OneToOne
    @JoinColumn(unique = true)
    private CommercialProformaInvoice commercialProformaInvoice;

    @OneToOne
    @JoinColumn(unique = true)
    private CommercialPackaging commercialPackaging;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public CommercialInvoice invoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
        return this;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public CommercialInvoice invoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getTermsOfPayment() {
        return termsOfPayment;
    }

    public CommercialInvoice termsOfPayment(String termsOfPayment) {
        this.termsOfPayment = termsOfPayment;
        return this;
    }

    public void setTermsOfPayment(String termsOfPayment) {
        this.termsOfPayment = termsOfPayment;
    }

    public String getConsignedTo() {
        return consignedTo;
    }

    public CommercialInvoice consignedTo(String consignedTo) {
        this.consignedTo = consignedTo;
        return this;
    }

    public void setConsignedTo(String consignedTo) {
        this.consignedTo = consignedTo;
    }

    public String getPortOfLoading() {
        return portOfLoading;
    }

    public CommercialInvoice portOfLoading(String portOfLoading) {
        this.portOfLoading = portOfLoading;
        return this;
    }

    public void setPortOfLoading(String portOfLoading) {
        this.portOfLoading = portOfLoading;
    }

    public String getPortOfDischarge() {
        return portOfDischarge;
    }

    public CommercialInvoice portOfDischarge(String portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
        return this;
    }

    public void setPortOfDischarge(String portOfDischarge) {
        this.portOfDischarge = portOfDischarge;
    }

    public String getExportRegistrationCertificateNo() {
        return exportRegistrationCertificateNo;
    }

    public CommercialInvoice exportRegistrationCertificateNo(String exportRegistrationCertificateNo) {
        this.exportRegistrationCertificateNo = exportRegistrationCertificateNo;
        return this;
    }

    public void setExportRegistrationCertificateNo(String exportRegistrationCertificateNo) {
        this.exportRegistrationCertificateNo = exportRegistrationCertificateNo;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CommercialInvoice createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreateOn() {
        return createOn;
    }

    public CommercialInvoice createOn(LocalDate createOn) {
        this.createOn = createOn;
        return this;
    }

    public void setCreateOn(LocalDate createOn) {
        this.createOn = createOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CommercialInvoice updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public CommercialInvoice updatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public CommercialPurchaseOrder getCommercialPurchaseOrder() {
        return commercialPurchaseOrder;
    }

    public CommercialInvoice commercialPurchaseOrder(CommercialPurchaseOrder commercialPurchaseOrder) {
        this.commercialPurchaseOrder = commercialPurchaseOrder;
        return this;
    }

    public void setCommercialPurchaseOrder(CommercialPurchaseOrder commercialPurchaseOrder) {
        this.commercialPurchaseOrder = commercialPurchaseOrder;
    }

    public CommercialProformaInvoice getCommercialProformaInvoice() {
        return commercialProformaInvoice;
    }

    public CommercialInvoice commercialProformaInvoice(CommercialProformaInvoice commercialProformaInvoice) {
        this.commercialProformaInvoice = commercialProformaInvoice;
        return this;
    }

    public void setCommercialProformaInvoice(CommercialProformaInvoice commercialProformaInvoice) {
        this.commercialProformaInvoice = commercialProformaInvoice;
    }

    public CommercialPackaging getCommercialPackaging() {
        return commercialPackaging;
    }

    public CommercialInvoice commercialPackaging(CommercialPackaging commercialPackaging) {
        this.commercialPackaging = commercialPackaging;
        return this;
    }

    public void setCommercialPackaging(CommercialPackaging commercialPackaging) {
        this.commercialPackaging = commercialPackaging;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommercialInvoice commercialInvoice = (CommercialInvoice) o;
        if (commercialInvoice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialInvoice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialInvoice{" +
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
            "}";
    }
}
