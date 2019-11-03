package org.soptorshi.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the CommercialPurchaseOrder entity.
 */
public class CommercialPurchaseOrderDTO implements Serializable {

    private Long id;

    @NotNull
    private String purchaseOrderNo;

    @NotNull
    private LocalDate purchaseOrderDate;

    private String originOfGoods;

    private String finalDestination;

    @NotNull
    private LocalDate shipmentDate;

    private String createdBy;

    private LocalDate createOn;

    private String updatedBy;

    private String updatedOn;


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

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommercialPurchaseOrderDTO commercialPurchaseOrderDTO = (CommercialPurchaseOrderDTO) o;
        if (commercialPurchaseOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPurchaseOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPurchaseOrderDTO{" +
            "id=" + getId() +
            ", purchaseOrderNo='" + getPurchaseOrderNo() + "'" +
            ", purchaseOrderDate='" + getPurchaseOrderDate() + "'" +
            ", originOfGoods='" + getOriginOfGoods() + "'" +
            ", finalDestination='" + getFinalDestination() + "'" +
            ", shipmentDate='" + getShipmentDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createOn='" + getCreateOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
