package org.soptorshi.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the SupplyChallan entity.
 */
public class SupplyChallanDTO implements Serializable {

    private Long id;

    @NotNull
    private String challanNo;

    private LocalDate dateOfChallan;

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

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(String challanNo) {
        this.challanNo = challanNo;
    }

    public LocalDate getDateOfChallan() {
        return dateOfChallan;
    }

    public void setDateOfChallan(LocalDate dateOfChallan) {
        this.dateOfChallan = dateOfChallan;
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

        SupplyChallanDTO supplyChallanDTO = (SupplyChallanDTO) o;
        if (supplyChallanDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyChallanDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyChallanDTO{" +
            "id=" + getId() +
            ", challanNo='" + getChallanNo() + "'" +
            ", dateOfChallan='" + getDateOfChallan() + "'" +
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
