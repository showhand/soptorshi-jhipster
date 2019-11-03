package org.soptorshi.service.dto;

import org.soptorshi.domain.enumeration.CommercialCurrency;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the CommercialPurchaseOrderItem entity.
 */
public class CommercialPurchaseOrderItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String goodsOrServices;

    private String descriptionOfGoodsOrServices;

    private String packaging;

    @NotNull
    private String sizeOrGrade;

    @NotNull
    private Double qtyOrMc;

    @NotNull
    private Double qtyOrKgs;

    @NotNull
    private Double rateOrKg;

    @NotNull
    private CommercialCurrency currencyType;

    @NotNull
    private Double total;

    private String createdBy;

    private LocalDate createOn;

    private String updatedBy;

    private String updatedOn;


    private Long commercialPurchaseOrderId;

    private String commercialPurchaseOrderPurchaseOrderNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsOrServices() {
        return goodsOrServices;
    }

    public void setGoodsOrServices(String goodsOrServices) {
        this.goodsOrServices = goodsOrServices;
    }

    public String getDescriptionOfGoodsOrServices() {
        return descriptionOfGoodsOrServices;
    }

    public void setDescriptionOfGoodsOrServices(String descriptionOfGoodsOrServices) {
        this.descriptionOfGoodsOrServices = descriptionOfGoodsOrServices;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getSizeOrGrade() {
        return sizeOrGrade;
    }

    public void setSizeOrGrade(String sizeOrGrade) {
        this.sizeOrGrade = sizeOrGrade;
    }

    public Double getQtyOrMc() {
        return qtyOrMc;
    }

    public void setQtyOrMc(Double qtyOrMc) {
        this.qtyOrMc = qtyOrMc;
    }

    public Double getQtyOrKgs() {
        return qtyOrKgs;
    }

    public void setQtyOrKgs(Double qtyOrKgs) {
        this.qtyOrKgs = qtyOrKgs;
    }

    public Double getRateOrKg() {
        return rateOrKg;
    }

    public void setRateOrKg(Double rateOrKg) {
        this.rateOrKg = rateOrKg;
    }

    public CommercialCurrency getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(CommercialCurrency currencyType) {
        this.currencyType = currencyType;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public Long getCommercialPurchaseOrderId() {
        return commercialPurchaseOrderId;
    }

    public void setCommercialPurchaseOrderId(Long commercialPurchaseOrderId) {
        this.commercialPurchaseOrderId = commercialPurchaseOrderId;
    }

    public String getCommercialPurchaseOrderPurchaseOrderNo() {
        return commercialPurchaseOrderPurchaseOrderNo;
    }

    public void setCommercialPurchaseOrderPurchaseOrderNo(String commercialPurchaseOrderPurchaseOrderNo) {
        this.commercialPurchaseOrderPurchaseOrderNo = commercialPurchaseOrderPurchaseOrderNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommercialPurchaseOrderItemDTO commercialPurchaseOrderItemDTO = (CommercialPurchaseOrderItemDTO) o;
        if (commercialPurchaseOrderItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPurchaseOrderItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPurchaseOrderItemDTO{" +
            "id=" + getId() +
            ", goodsOrServices='" + getGoodsOrServices() + "'" +
            ", descriptionOfGoodsOrServices='" + getDescriptionOfGoodsOrServices() + "'" +
            ", packaging='" + getPackaging() + "'" +
            ", sizeOrGrade='" + getSizeOrGrade() + "'" +
            ", qtyOrMc=" + getQtyOrMc() +
            ", qtyOrKgs=" + getQtyOrKgs() +
            ", rateOrKg=" + getRateOrKg() +
            ", currencyType='" + getCurrencyType() + "'" +
            ", total=" + getTotal() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createOn='" + getCreateOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", commercialPurchaseOrder=" + getCommercialPurchaseOrderId() +
            ", commercialPurchaseOrder='" + getCommercialPurchaseOrderPurchaseOrderNo() + "'" +
            "}";
    }
}
