package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.SupplyZoneWiseAccumulationStatus;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the SupplyZoneWiseAccumulation entity.
 */
public class SupplyZoneWiseAccumulationDTO implements Serializable {

    private Long id;

    @NotNull
    private String zoneWiseAccumulationRefNo;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private BigDecimal price;

    @NotNull
    private SupplyZoneWiseAccumulationStatus status;

    private String remarks;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;


    private Long supplyZoneId;

    private String supplyZoneName;

    private Long supplyZoneManagerId;

    private Long productCategoryId;

    private String productCategoryName;

    private Long productId;

    private String productName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZoneWiseAccumulationRefNo() {
        return zoneWiseAccumulationRefNo;
    }

    public void setZoneWiseAccumulationRefNo(String zoneWiseAccumulationRefNo) {
        this.zoneWiseAccumulationRefNo = zoneWiseAccumulationRefNo;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public SupplyZoneWiseAccumulationStatus getStatus() {
        return status;
    }

    public void setStatus(SupplyZoneWiseAccumulationStatus status) {
        this.status = status;
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

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO = (SupplyZoneWiseAccumulationDTO) o;
        if (supplyZoneWiseAccumulationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyZoneWiseAccumulationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyZoneWiseAccumulationDTO{" +
            "id=" + getId() +
            ", zoneWiseAccumulationRefNo='" + getZoneWiseAccumulationRefNo() + "'" +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            ", status='" + getStatus() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", supplyZone=" + getSupplyZoneId() +
            ", supplyZone='" + getSupplyZoneName() + "'" +
            ", supplyZoneManager=" + getSupplyZoneManagerId() +
            ", productCategory=" + getProductCategoryId() +
            ", productCategory='" + getProductCategoryName() + "'" +
            ", product=" + getProductId() +
            ", product='" + getProductName() + "'" +
            "}";
    }
}
