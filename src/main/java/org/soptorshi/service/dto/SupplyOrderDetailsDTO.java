package org.soptorshi.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SupplyOrderDetails entity.
 */
public class SupplyOrderDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String productName;

    @NotNull
    private Double productVolume;

    @NotNull
    private Double totalPrice;

    private String createdBy;

    private Instant createdOn;

    private String updatedBy;

    private Instant updatedOn;


    private Long supplyOrderId;

    private String supplyOrderOrderNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductVolume() {
        return productVolume;
    }

    public void setProductVolume(Double productVolume) {
        this.productVolume = productVolume;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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

        SupplyOrderDetailsDTO supplyOrderDetailsDTO = (SupplyOrderDetailsDTO) o;
        if (supplyOrderDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyOrderDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyOrderDetailsDTO{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", productVolume=" + getProductVolume() +
            ", totalPrice=" + getTotalPrice() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", supplyOrder=" + getSupplyOrderId() +
            ", supplyOrder='" + getSupplyOrderOrderNo() + "'" +
            "}";
    }
}
