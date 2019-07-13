package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the TermsAndConditions entity.
 */
public class TermsAndConditionsDTO implements Serializable {

    private Long id;

    @Lob
    private String description;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long purchaseOrderId;

    private String purchaseOrderPurchaseOrderNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getPurchaseOrderPurchaseOrderNo() {
        return purchaseOrderPurchaseOrderNo;
    }

    public void setPurchaseOrderPurchaseOrderNo(String purchaseOrderPurchaseOrderNo) {
        this.purchaseOrderPurchaseOrderNo = purchaseOrderPurchaseOrderNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TermsAndConditionsDTO termsAndConditionsDTO = (TermsAndConditionsDTO) o;
        if (termsAndConditionsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), termsAndConditionsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TermsAndConditionsDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", purchaseOrder=" + getPurchaseOrderId() +
            ", purchaseOrder='" + getPurchaseOrderPurchaseOrderNo() + "'" +
            "}";
    }
}
