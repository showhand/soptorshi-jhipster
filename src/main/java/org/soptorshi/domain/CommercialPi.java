package org.soptorshi.domain;


import org.soptorshi.domain.enumeration.CommercialPiStatus;
import org.soptorshi.domain.enumeration.PaymentType;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CommercialPi.
 */
@Entity
@Table(name = "commercial_pi")
@Document(indexName = "commercialpi")
public class CommercialPi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_description")
    private String companyDescription;

    @NotNull
    @Column(name = "proforma_no", nullable = false)
    private String proformaNo;

    @Column(name = "proforma_date")
    private LocalDate proformaDate;

    @Column(name = "harmonic_code")
    private String harmonicCode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @Column(name = "payment_term")
    private String paymentTerm;

    @Column(name = "terms_of_delivery")
    private String termsOfDelivery;

    @Column(name = "shipment_date")
    private String shipmentDate;

    @Column(name = "port_of_loading")
    private String portOfLoading;

    @Column(name = "port_of_destination")
    private String portOfDestination;

    @Column(name = "purchase_order_no")
    private String purchaseOrderNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "pi_status")
    private CommercialPiStatus piStatus;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private CommercialBudget commercialBudget;

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

    public CommercialPi companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public CommercialPi companyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
        return this;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getProformaNo() {
        return proformaNo;
    }

    public CommercialPi proformaNo(String proformaNo) {
        this.proformaNo = proformaNo;
        return this;
    }

    public void setProformaNo(String proformaNo) {
        this.proformaNo = proformaNo;
    }

    public LocalDate getProformaDate() {
        return proformaDate;
    }

    public CommercialPi proformaDate(LocalDate proformaDate) {
        this.proformaDate = proformaDate;
        return this;
    }

    public void setProformaDate(LocalDate proformaDate) {
        this.proformaDate = proformaDate;
    }

    public String getHarmonicCode() {
        return harmonicCode;
    }

    public CommercialPi harmonicCode(String harmonicCode) {
        this.harmonicCode = harmonicCode;
        return this;
    }

    public void setHarmonicCode(String harmonicCode) {
        this.harmonicCode = harmonicCode;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public CommercialPi paymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public CommercialPi paymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
        return this;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getTermsOfDelivery() {
        return termsOfDelivery;
    }

    public CommercialPi termsOfDelivery(String termsOfDelivery) {
        this.termsOfDelivery = termsOfDelivery;
        return this;
    }

    public void setTermsOfDelivery(String termsOfDelivery) {
        this.termsOfDelivery = termsOfDelivery;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public CommercialPi shipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
        return this;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getPortOfLoading() {
        return portOfLoading;
    }

    public CommercialPi portOfLoading(String portOfLoading) {
        this.portOfLoading = portOfLoading;
        return this;
    }

    public void setPortOfLoading(String portOfLoading) {
        this.portOfLoading = portOfLoading;
    }

    public String getPortOfDestination() {
        return portOfDestination;
    }

    public CommercialPi portOfDestination(String portOfDestination) {
        this.portOfDestination = portOfDestination;
        return this;
    }

    public void setPortOfDestination(String portOfDestination) {
        this.portOfDestination = portOfDestination;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public CommercialPi purchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
        return this;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public CommercialPiStatus getPiStatus() {
        return piStatus;
    }

    public CommercialPi piStatus(CommercialPiStatus piStatus) {
        this.piStatus = piStatus;
        return this;
    }

    public void setPiStatus(CommercialPiStatus piStatus) {
        this.piStatus = piStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CommercialPi createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public CommercialPi createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CommercialPi updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public CommercialPi updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public CommercialBudget getCommercialBudget() {
        return commercialBudget;
    }

    public CommercialPi commercialBudget(CommercialBudget commercialBudget) {
        this.commercialBudget = commercialBudget;
        return this;
    }

    public void setCommercialBudget(CommercialBudget commercialBudget) {
        this.commercialBudget = commercialBudget;
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
        CommercialPi commercialPi = (CommercialPi) o;
        if (commercialPi.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPi.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPi{" +
            "id=" + getId() +
            ", companyName='" + getCompanyName() + "'" +
            ", companyDescription='" + getCompanyDescription() + "'" +
            ", proformaNo='" + getProformaNo() + "'" +
            ", proformaDate='" + getProformaDate() + "'" +
            ", harmonicCode='" + getHarmonicCode() + "'" +
            ", paymentType='" + getPaymentType() + "'" +
            ", paymentTerm='" + getPaymentTerm() + "'" +
            ", termsOfDelivery='" + getTermsOfDelivery() + "'" +
            ", shipmentDate='" + getShipmentDate() + "'" +
            ", portOfLoading='" + getPortOfLoading() + "'" +
            ", portOfDestination='" + getPortOfDestination() + "'" +
            ", purchaseOrderNo='" + getPurchaseOrderNo() + "'" +
            ", piStatus='" + getPiStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
