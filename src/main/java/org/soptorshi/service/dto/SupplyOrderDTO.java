package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.SupplyOrderStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the SupplyOrder entity.
 */
public class SupplyOrderDTO implements Serializable {

    private Long id;

    @NotNull
    private String orderNo;

    private LocalDate dateOfOrder;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;

    private LocalDate deliveryDate;

    @NotNull
    private SupplyOrderStatus status;

    private String areaWiseAccumulationRefNo;

    private String remarks;


    private Long supplyZoneId;

    private String supplyZoneName;

    private Long supplyZoneManagerId;

    private Long supplyAreaId;

    private String supplyAreaName;

    private Long supplySalesRepresentativeId;

    private String supplySalesRepresentativeName;

    private Long supplyAreaManagerId;

    private Long supplyShopId;

    private String supplyShopName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public LocalDate getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(LocalDate dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
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

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public SupplyOrderStatus getStatus() {
        return status;
    }

    public void setStatus(SupplyOrderStatus status) {
        this.status = status;
    }

    public String getAreaWiseAccumulationRefNo() {
        return areaWiseAccumulationRefNo;
    }

    public void setAreaWiseAccumulationRefNo(String areaWiseAccumulationRefNo) {
        this.areaWiseAccumulationRefNo = areaWiseAccumulationRefNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Long getSupplyAreaManagerId() {
        return supplyAreaManagerId;
    }

    public void setSupplyAreaManagerId(Long supplyAreaManagerId) {
        this.supplyAreaManagerId = supplyAreaManagerId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupplyOrderDTO supplyOrderDTO = (SupplyOrderDTO) o;
        if (supplyOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyOrderDTO{" +
            "id=" + getId() +
            ", orderNo='" + getOrderNo() + "'" +
            ", dateOfOrder='" + getDateOfOrder() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", areaWiseAccumulationRefNo='" + getAreaWiseAccumulationRefNo() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", supplyZone=" + getSupplyZoneId() +
            ", supplyZone='" + getSupplyZoneName() + "'" +
            ", supplyZoneManager=" + getSupplyZoneManagerId() +
            ", supplyArea=" + getSupplyAreaId() +
            ", supplyArea='" + getSupplyAreaName() + "'" +
            ", supplySalesRepresentative=" + getSupplySalesRepresentativeId() +
            ", supplySalesRepresentative='" + getSupplySalesRepresentativeName() + "'" +
            ", supplyAreaManager=" + getSupplyAreaManagerId() +
            ", supplyShop=" + getSupplyShopId() +
            ", supplyShop='" + getSupplyShopName() + "'" +
            "}";
    }
}
