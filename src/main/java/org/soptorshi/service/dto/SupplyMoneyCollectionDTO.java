package org.soptorshi.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the SupplyMoneyCollection entity.
 */
public class SupplyMoneyCollectionDTO implements Serializable {

    private Long id;

    private Double totalAmount;

    private Double receivedAmount;

    private String remarks;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;


    private Long supplyZoneId;

    private String supplyZoneName;

    private Long supplyZoneManagerId;

    private Long supplyAreaId;

    private String supplyAreaName;

    private Long supplyAreaManagerId;

    private Long supplySalesRepresentativeId;

    private String supplySalesRepresentativeName;

    private Long supplyShopId;

    private String supplyShopName;

    private Long supplyOrderId;

    private String supplyOrderOrderNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(Double receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Long getSupplyZoneId() {
        return supplyZoneId;
    }

    public void setSupplyZoneId(Long supplyZoneId) {
        this.supplyZoneId = supplyZoneId;
    }

    public String getSupplyZoneName() {
        return supplyZoneName;
    }

    public void setSupplyZoneName(String supplyZoneName) {
        this.supplyZoneName = supplyZoneName;
    }

    public Long getSupplyZoneManagerId() {
        return supplyZoneManagerId;
    }

    public void setSupplyZoneManagerId(Long supplyZoneManagerId) {
        this.supplyZoneManagerId = supplyZoneManagerId;
    }

    public Long getSupplyAreaId() {
        return supplyAreaId;
    }

    public void setSupplyAreaId(Long supplyAreaId) {
        this.supplyAreaId = supplyAreaId;
    }

    public String getSupplyAreaName() {
        return supplyAreaName;
    }

    public void setSupplyAreaName(String supplyAreaName) {
        this.supplyAreaName = supplyAreaName;
    }

    public Long getSupplyAreaManagerId() {
        return supplyAreaManagerId;
    }

    public void setSupplyAreaManagerId(Long supplyAreaManagerId) {
        this.supplyAreaManagerId = supplyAreaManagerId;
    }

    public Long getSupplySalesRepresentativeId() {
        return supplySalesRepresentativeId;
    }

    public void setSupplySalesRepresentativeId(Long supplySalesRepresentativeId) {
        this.supplySalesRepresentativeId = supplySalesRepresentativeId;
    }

    public String getSupplySalesRepresentativeName() {
        return supplySalesRepresentativeName;
    }

    public void setSupplySalesRepresentativeName(String supplySalesRepresentativeName) {
        this.supplySalesRepresentativeName = supplySalesRepresentativeName;
    }

    public Long getSupplyShopId() {
        return supplyShopId;
    }

    public void setSupplyShopId(Long supplyShopId) {
        this.supplyShopId = supplyShopId;
    }

    public String getSupplyShopName() {
        return supplyShopName;
    }

    public void setSupplyShopName(String supplyShopName) {
        this.supplyShopName = supplyShopName;
    }

    public Long getSupplyOrderId() {
        return supplyOrderId;
    }

    public void setSupplyOrderId(Long supplyOrderId) {
        this.supplyOrderId = supplyOrderId;
    }

    public String getSupplyOrderOrderNo() {
        return supplyOrderOrderNo;
    }

    public void setSupplyOrderOrderNo(String supplyOrderOrderNo) {
        this.supplyOrderOrderNo = supplyOrderOrderNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupplyMoneyCollectionDTO supplyMoneyCollectionDTO = (SupplyMoneyCollectionDTO) o;
        if (supplyMoneyCollectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyMoneyCollectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyMoneyCollectionDTO{" +
            "id=" + getId() +
            ", totalAmount=" + getTotalAmount() +
            ", receivedAmount=" + getReceivedAmount() +
            ", remarks='" + getRemarks() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", supplyZone=" + getSupplyZoneId() +
            ", supplyZone='" + getSupplyZoneName() + "'" +
            ", supplyZoneManager=" + getSupplyZoneManagerId() +
            ", supplyArea=" + getSupplyAreaId() +
            ", supplyArea='" + getSupplyAreaName() + "'" +
            ", supplyAreaManager=" + getSupplyAreaManagerId() +
            ", supplySalesRepresentative=" + getSupplySalesRepresentativeId() +
            ", supplySalesRepresentative='" + getSupplySalesRepresentativeName() + "'" +
            ", supplyShop=" + getSupplyShopId() +
            ", supplyShop='" + getSupplyShopName() + "'" +
            ", supplyOrder=" + getSupplyOrderId() +
            ", supplyOrder='" + getSupplyOrderOrderNo() + "'" +
            "}";
    }
}
