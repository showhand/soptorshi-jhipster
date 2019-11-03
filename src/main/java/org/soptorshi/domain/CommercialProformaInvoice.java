package org.soptorshi.domain;


import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CommercialProformaInvoice.
 */
@Entity
@Table(name = "commercial_proforma_invoice")
@Document(indexName = "commercialproformainvoice")
public class CommercialProformaInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "company_description_or_address")
    private String companyDescriptionOrAddress;

    @NotNull
    @Column(name = "proforma_no", nullable = false)
    private String proformaNo;

    @Column(name = "proforma_date")
    private LocalDate proformaDate;

    @NotNull
    @Column(name = "harmonic_code", nullable = false)
    private String harmonicCode;

    @NotNull
    @Column(name = "payment_term", nullable = false)
    private String paymentTerm;

    @NotNull
    @Column(name = "terms_of_delivery", nullable = false)
    private String termsOfDelivery;

    @NotNull
    @Column(name = "shipment_date", nullable = false)
    private String shipmentDate;

    @NotNull
    @Column(name = "port_of_landing", nullable = false)
    private String portOfLanding;

    @NotNull
    @Column(name = "port_of_destination", nullable = false)
    private String portOfDestination;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public CommercialProformaInvoice companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescriptionOrAddress() {
        return companyDescriptionOrAddress;
    }

    public CommercialProformaInvoice companyDescriptionOrAddress(String companyDescriptionOrAddress) {
        this.companyDescriptionOrAddress = companyDescriptionOrAddress;
        return this;
    }

    public void setCompanyDescriptionOrAddress(String companyDescriptionOrAddress) {
        this.companyDescriptionOrAddress = companyDescriptionOrAddress;
    }

    public String getProformaNo() {
        return proformaNo;
    }

    public CommercialProformaInvoice proformaNo(String proformaNo) {
        this.proformaNo = proformaNo;
        return this;
    }

    public void setProformaNo(String proformaNo) {
        this.proformaNo = proformaNo;
    }

    public LocalDate getProformaDate() {
        return proformaDate;
    }

    public CommercialProformaInvoice proformaDate(LocalDate proformaDate) {
        this.proformaDate = proformaDate;
        return this;
    }

    public void setProformaDate(LocalDate proformaDate) {
        this.proformaDate = proformaDate;
    }

    public String getHarmonicCode() {
        return harmonicCode;
    }

    public CommercialProformaInvoice harmonicCode(String harmonicCode) {
        this.harmonicCode = harmonicCode;
        return this;
    }

    public void setHarmonicCode(String harmonicCode) {
        this.harmonicCode = harmonicCode;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public CommercialProformaInvoice paymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
        return this;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getTermsOfDelivery() {
        return termsOfDelivery;
    }

    public CommercialProformaInvoice termsOfDelivery(String termsOfDelivery) {
        this.termsOfDelivery = termsOfDelivery;
        return this;
    }

    public void setTermsOfDelivery(String termsOfDelivery) {
        this.termsOfDelivery = termsOfDelivery;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public CommercialProformaInvoice shipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
        return this;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getPortOfLanding() {
        return portOfLanding;
    }

    public CommercialProformaInvoice portOfLanding(String portOfLanding) {
        this.portOfLanding = portOfLanding;
        return this;
    }

    public void setPortOfLanding(String portOfLanding) {
        this.portOfLanding = portOfLanding;
    }

    public String getPortOfDestination() {
        return portOfDestination;
    }

    public CommercialProformaInvoice portOfDestination(String portOfDestination) {
        this.portOfDestination = portOfDestination;
        return this;
    }

    public void setPortOfDestination(String portOfDestination) {
        this.portOfDestination = portOfDestination;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CommercialProformaInvoice createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreateOn() {
        return createOn;
    }

    public CommercialProformaInvoice createOn(LocalDate createOn) {
        this.createOn = createOn;
        return this;
    }

    public void setCreateOn(LocalDate createOn) {
        this.createOn = createOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CommercialProformaInvoice updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public CommercialProformaInvoice updatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public CommercialPurchaseOrder getCommercialPurchaseOrder() {
        return commercialPurchaseOrder;
    }

    public CommercialProformaInvoice commercialPurchaseOrder(CommercialPurchaseOrder commercialPurchaseOrder) {
        this.commercialPurchaseOrder = commercialPurchaseOrder;
        return this;
    }

    public void setCommercialPurchaseOrder(CommercialPurchaseOrder commercialPurchaseOrder) {
        this.commercialPurchaseOrder = commercialPurchaseOrder;
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
        CommercialProformaInvoice commercialProformaInvoice = (CommercialProformaInvoice) o;
        if (commercialProformaInvoice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialProformaInvoice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialProformaInvoice{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", companyDescriptionOrAddress='" + getCompanyDescriptionOrAddress() + "'" +
            ", proformaNo='" + getProformaNo() + "'" +
            ", proformaDate='" + getProformaDate() + "'" +
            ", harmonicCode='" + getHarmonicCode() + "'" +
            ", paymentTerm='" + getPaymentTerm() + "'" +
            ", termsOfDelivery='" + getTermsOfDelivery() + "'" +
            ", shipmentDate='" + getShipmentDate() + "'" +
            ", portOfLanding='" + getPortOfLanding() + "'" +
            ", portOfDestination='" + getPortOfDestination() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createOn='" + getCreateOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
