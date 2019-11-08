package org.soptorshi.service.dto;

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

    @NotNull
    private String offer;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;


    private Long supplyZoneId;

    private String supplyZoneZoneName;

    private Long supplyAreaId;

    private String supplyAreaAreaName;

    private Long supplyAreaManagerId;

    private String supplyAreaManagerManagerName;

    private Long supplySalesRepresentativeId;

    private String supplySalesRepresentativeSalesRepresentativeName;

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

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
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

    public Long getSupplyAreaManagerId() {
        return supplyAreaManagerId;
    }

    public void setSupplyAreaManagerId(Long supplyAreaManagerId) {
        this.supplyAreaManagerId = supplyAreaManagerId;
    }

    public String getSupplyAreaManagerManagerName() {
        return supplyAreaManagerManagerName;
    }

    public void setSupplyAreaManagerManagerName(String supplyAreaManagerManagerName) {
        this.supplyAreaManagerManagerName = supplyAreaManagerManagerName;
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
            ", offer='" + getOffer() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", supplyZone=" + getSupplyZoneId() +
            ", supplyZone='" + getSupplyZoneZoneName() + "'" +
            ", supplyArea=" + getSupplyAreaId() +
            ", supplyArea='" + getSupplyAreaAreaName() + "'" +
            ", supplyAreaManager=" + getSupplyAreaManagerId() +
            ", supplyAreaManager='" + getSupplyAreaManagerManagerName() + "'" +
            ", supplySalesRepresentative=" + getSupplySalesRepresentativeId() +
            ", supplySalesRepresentative='" + getSupplySalesRepresentativeSalesRepresentativeName() + "'" +
            "}";
    }
}
