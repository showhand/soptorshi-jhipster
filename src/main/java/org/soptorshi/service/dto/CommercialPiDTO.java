package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.CommercialPiStatus;
import org.soptorshi.domain.enumeration.PaymentType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the CommercialPi entity.
 */
public class CommercialPiDTO implements Serializable {

    private Long id;

    private String companyName;

    private String companyDescription;

    @NotNull
    private String proformaNo;

    private LocalDate proformaDate;

    private String harmonicCode;

    @NotNull
    private PaymentType paymentType;

    private String paymentTerm;

    private String termsOfDelivery;

    private String shipmentDate;

    private String portOfLoading;

    private String portOfDestination;

    private String purchaseOrderNo;

    private CommercialPiStatus piStatus;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;


    private Long commercialBudgetId;

    private String commercialBudgetBudgetNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getProformaNo() {
        return proformaNo;
    }

    public void setProformaNo(String proformaNo) {
        this.proformaNo = proformaNo;
    }

    public LocalDate getProformaDate() {
        return proformaDate;
    }

    public void setProformaDate(LocalDate proformaDate) {
        this.proformaDate = proformaDate;
    }

    public String getHarmonicCode() {
        return harmonicCode;
    }

    public void setHarmonicCode(String harmonicCode) {
        this.harmonicCode = harmonicCode;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getTermsOfDelivery() {
        return termsOfDelivery;
    }

    public void setTermsOfDelivery(String termsOfDelivery) {
        this.termsOfDelivery = termsOfDelivery;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getPortOfLoading() {
        return portOfLoading;
    }

    public void setPortOfLoading(String portOfLoading) {
        this.portOfLoading = portOfLoading;
    }

    public String getPortOfDestination() {
        return portOfDestination;
    }

    public void setPortOfDestination(String portOfDestination) {
        this.portOfDestination = portOfDestination;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public CommercialPiStatus getPiStatus() {
        return piStatus;
    }

    public void setPiStatus(CommercialPiStatus piStatus) {
        this.piStatus = piStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getCommercialBudgetId() {
        return commercialBudgetId;
    }

    public void setCommercialBudgetId(Long commercialBudgetId) {
        this.commercialBudgetId = commercialBudgetId;
    }

    public String getCommercialBudgetBudgetNo() {
        return commercialBudgetBudgetNo;
    }

    public void setCommercialBudgetBudgetNo(String commercialBudgetBudgetNo) {
        this.commercialBudgetBudgetNo = commercialBudgetBudgetNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommercialPiDTO commercialPiDTO = (CommercialPiDTO) o;
        if (commercialPiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPiDTO{" +
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
            ", commercialBudget=" + getCommercialBudgetId() +
            ", commercialBudget='" + getCommercialBudgetBudgetNo() + "'" +
            "}";
    }
}
