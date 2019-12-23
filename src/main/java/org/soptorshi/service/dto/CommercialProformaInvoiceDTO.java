package org.soptorshi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CommercialProformaInvoice entity.
 */
public class CommercialProformaInvoiceDTO implements Serializable {

    private Long id;

    @NotNull
    private String companyName;

    private String companyDescriptionOrAddress;

    @NotNull
    private String proformaNo;

    private LocalDate proformaDate;

    @NotNull
    private String harmonicCode;

    @NotNull
    private String paymentTerm;

    @NotNull
    private String termsOfDelivery;

    @NotNull
    private String shipmentDate;

    @NotNull
    private String portOfLanding;

    @NotNull
    private String portOfDestination;

    private String createdBy;

    private LocalDate createdOn;

    private String updatedBy;

    private LocalDate updatedOn;


    private Long commercialPurchaseOrderId;

    private String commercialPurchaseOrderPurchaseOrderNo;

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

    public String getCompanyDescriptionOrAddress() {
        return companyDescriptionOrAddress;
    }

    public void setCompanyDescriptionOrAddress(String companyDescriptionOrAddress) {
        this.companyDescriptionOrAddress = companyDescriptionOrAddress;
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

    public String getPortOfLanding() {
        return portOfLanding;
    }

    public void setPortOfLanding(String portOfLanding) {
        this.portOfLanding = portOfLanding;
    }

    public String getPortOfDestination() {
        return portOfDestination;
    }

    public void setPortOfDestination(String portOfDestination) {
        this.portOfDestination = portOfDestination;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommercialProformaInvoiceDTO commercialProformaInvoiceDTO = (CommercialProformaInvoiceDTO) o;
        if (commercialProformaInvoiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialProformaInvoiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialProformaInvoiceDTO{" +
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
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", commercialPurchaseOrder=" + getCommercialPurchaseOrderId() +
            ", commercialPurchaseOrder='" + getCommercialPurchaseOrderPurchaseOrderNo() + "'" +
            "}";
    }
}
