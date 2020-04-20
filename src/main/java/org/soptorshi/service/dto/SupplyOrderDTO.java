package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.SupplyOrderStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
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

    private BigDecimal offerAmount;

    private LocalDate deliveryDate;

    @NotNull
    private SupplyOrderStatus supplyOrderStatus;


    private Long supplyZoneId;

    private String supplyZoneZoneName;

    private Long supplyAreaId;

    private String supplyAreaAreaName;

    private Long supplySalesRepresentativeId;

    private String supplySalesRepresentativeSalesRepresentativeName;

    private Long supplyAreaManagerId;

    private Long supplyShopId;

    private String supplyShopShopName;

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

    public BigDecimal getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(BigDecimal offerAmount) {
        this.offerAmount = offerAmount;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public SupplyOrderStatus getSupplyOrderStatus() {
        return supplyOrderStatus;
    }

    public void setSupplyOrderStatus(SupplyOrderStatus supplyOrderStatus) {
        this.supplyOrderStatus = supplyOrderStatus;
    }

    public Long getSupplyZoneId() {
        return supplyZoneId;
    }

    public void setSupplyZoneId(Long supplyZoneId) {
        this.supplyZoneId = supplyZoneId;
    }

    public String getSupplyZoneZoneName() {
        return supplyZoneZoneName;
    }

    public void setSupplyZoneZoneName(String supplyZoneZoneName) {
        this.supplyZoneZoneName = supplyZoneZoneName;
    }

    public Long getSupplyAreaId() {
        return supplyAreaId;
    }

    public void setSupplyAreaId(Long supplyAreaId) {
        this.supplyAreaId = supplyAreaId;
    }

    public String getSupplyAreaAreaName() {
        return supplyAreaAreaName;
    }

    public void setSupplyAreaAreaName(String supplyAreaAreaName) {
        this.supplyAreaAreaName = supplyAreaAreaName;
    }

    public Long getSupplySalesRepresentativeId() {
        return supplySalesRepresentativeId;
    }

    public void setSupplySalesRepresentativeId(Long supplySalesRepresentativeId) {
        this.supplySalesRepresentativeId = supplySalesRepresentativeId;
    }

    public String getSupplySalesRepresentativeSalesRepresentativeName() {
        return supplySalesRepresentativeSalesRepresentativeName;
    }

    public void setSupplySalesRepresentativeSalesRepresentativeName(String supplySalesRepresentativeSalesRepresentativeName) {
        this.supplySalesRepresentativeSalesRepresentativeName = supplySalesRepresentativeSalesRepresentativeName;
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

    public String getSupplyShopShopName() {
        return supplyShopShopName;
    }

    public void setSupplyShopShopName(String supplyShopShopName) {
        this.supplyShopShopName = supplyShopShopName;
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
            ", offerAmount=" + getOfferAmount() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", supplyOrderStatus='" + getSupplyOrderStatus() + "'" +
            ", supplyZone=" + getSupplyZoneId() +
            ", supplyZone='" + getSupplyZoneZoneName() + "'" +
            ", supplyArea=" + getSupplyAreaId() +
            ", supplyArea='" + getSupplyAreaAreaName() + "'" +
            ", supplySalesRepresentative=" + getSupplySalesRepresentativeId() +
            ", supplySalesRepresentative='" + getSupplySalesRepresentativeSalesRepresentativeName() + "'" +
            ", supplyAreaManager=" + getSupplyAreaManagerId() +
            ", supplyShop=" + getSupplyShopId() +
            ", supplyShop='" + getSupplyShopShopName() + "'" +
            "}";
    }
}
