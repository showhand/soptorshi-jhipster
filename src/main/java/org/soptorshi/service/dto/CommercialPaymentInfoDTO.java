package org.soptorshi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.CommercialPaymentCategory;
import org.soptorshi.domain.enumeration.CommercialCurrency;

/**
 * A DTO for the CommercialPaymentInfo entity.
 */
public class CommercialPaymentInfoDTO implements Serializable {

    private Long id;

    @NotNull
    private CommercialPaymentCategory paymentCategory;

    @NotNull
    private Double totalAmount;

    @NotNull
    private CommercialCurrency currencyType;

    private String paymentTerms;

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

    public CommercialPaymentCategory getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(CommercialPaymentCategory paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public CommercialCurrency getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CommercialCurrency currencyType) {
        this.currencyType = currencyType;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
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

        CommercialPaymentInfoDTO commercialPaymentInfoDTO = (CommercialPaymentInfoDTO) o;
        if (commercialPaymentInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPaymentInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPaymentInfoDTO{" +
            "id=" + getId() +
            ", paymentCategory='" + getPaymentCategory() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", currencyType='" + getCurrencyType() + "'" +
            ", paymentTerms='" + getPaymentTerms() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", commercialPurchaseOrder=" + getCommercialPurchaseOrderId() +
            ", commercialPurchaseOrder='" + getCommercialPurchaseOrderPurchaseOrderNo() + "'" +
            "}";
    }
}
