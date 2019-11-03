package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.CommercialCurrency;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the CommercialWorkOrderDetails entity.
 */
public class CommercialWorkOrderDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String goods;

    private String reason;

    private String size;

    private String color;

    @NotNull
    private Double quantity;

    @NotNull
    private CommercialCurrency currencyType;

    @NotNull
    private Double rate;

    private String createdBy;

    private LocalDate createOn;

    private String updatedBy;

    private String updatedOn;


    private Long commercialWorkOrderId;

    private String commercialWorkOrderRefNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public CommercialCurrency getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CommercialCurrency currencyType) {
        this.currencyType = currencyType;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
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

    public Long getCommercialWorkOrderId() {
        return commercialWorkOrderId;
    }

    public void setCommercialWorkOrderId(Long commercialWorkOrderId) {
        this.commercialWorkOrderId = commercialWorkOrderId;
    }

    public String getCommercialWorkOrderRefNo() {
        return commercialWorkOrderRefNo;
    }

    public void setCommercialWorkOrderRefNo(String commercialWorkOrderRefNo) {
        this.commercialWorkOrderRefNo = commercialWorkOrderRefNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommercialWorkOrderDetailsDTO commercialWorkOrderDetailsDTO = (CommercialWorkOrderDetailsDTO) o;
        if (commercialWorkOrderDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialWorkOrderDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialWorkOrderDetailsDTO{" +
            "id=" + getId() +
            ", goods='" + getGoods() + "'" +
            ", reason='" + getReason() + "'" +
            ", size='" + getSize() + "'" +
            ", color='" + getColor() + "'" +
            ", quantity=" + getQuantity() +
            ", currencyType='" + getCurrencyType() + "'" +
            ", rate=" + getRate() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createOn='" + getCreateOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", commercialWorkOrder=" + getCommercialWorkOrderId() +
            ", commercialWorkOrder='" + getCommercialWorkOrderRefNo() + "'" +
            "}";
    }
}
