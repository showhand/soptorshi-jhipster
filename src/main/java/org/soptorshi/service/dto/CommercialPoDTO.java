package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.CommercialPoStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the CommercialPo entity.
 */
public class CommercialPoDTO implements Serializable {

    private Long id;

    @NotNull
    private String purchaseOrderNo;

    private LocalDate purchaseOrderDate;

    private String originOfGoods;

    private String finalDestination;

    private LocalDate shipmentDate;

    private CommercialPoStatus poStatus;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;


    private Long commercialPiId;

    private String commercialPiProformaNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public LocalDate getPurchaseOrderDate() {
        return purchaseOrderDate;
    }

    public void setPurchaseOrderDate(LocalDate purchaseOrderDate) {
        this.purchaseOrderDate = purchaseOrderDate;
    }

    public String getOriginOfGoods() {
        return originOfGoods;
    }

    public void setOriginOfGoods(String originOfGoods) {
        this.originOfGoods = originOfGoods;
    }

    public String getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
    }

    public LocalDate getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(LocalDate shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public CommercialPoStatus getPoStatus() {
        return poStatus;
    }

    public void setPoStatus(CommercialPoStatus poStatus) {
        this.poStatus = poStatus;
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

    public Long getCommercialPiId() {
        return commercialPiId;
    }

    public void setCommercialPiId(Long commercialPiId) {
        this.commercialPiId = commercialPiId;
    }

    public String getCommercialPiProformaNo() {
        return commercialPiProformaNo;
    }

    public void setCommercialPiProformaNo(String commercialPiProformaNo) {
        this.commercialPiProformaNo = commercialPiProformaNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommercialPoDTO commercialPoDTO = (CommercialPoDTO) o;
        if (commercialPoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPoDTO{" +
            "id=" + getId() +
            ", purchaseOrderNo='" + getPurchaseOrderNo() + "'" +
            ", purchaseOrderDate='" + getPurchaseOrderDate() + "'" +
            ", originOfGoods='" + getOriginOfGoods() + "'" +
            ", finalDestination='" + getFinalDestination() + "'" +
            ", shipmentDate='" + getShipmentDate() + "'" +
            ", poStatus='" + getPoStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", commercialPi=" + getCommercialPiId() +
            ", commercialPi='" + getCommercialPiProformaNo() + "'" +
            "}";
    }
}
