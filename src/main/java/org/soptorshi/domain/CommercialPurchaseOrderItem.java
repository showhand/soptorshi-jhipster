package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.soptorshi.domain.enumeration.CommercialCurrency;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CommercialPurchaseOrderItem.
 */
@Entity
@Table(name = "commercial_purchase_order_item")
@Document(indexName = "commercialpurchaseorderitem")
public class CommercialPurchaseOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "goods_or_services", nullable = false)
    private String goodsOrServices;

    @Column(name = "description_of_goods_or_services")
    private String descriptionOfGoodsOrServices;

    @Column(name = "packaging")
    private String packaging;

    @NotNull
    @Column(name = "size_or_grade", nullable = false)
    private String sizeOrGrade;

    @NotNull
    @Column(name = "qty_or_mc", nullable = false)
    private Double qtyOrMc;

    @NotNull
    @Column(name = "qty_or_kgs", nullable = false)
    private Double qtyOrKgs;

    @NotNull
    @Column(name = "rate_or_kg", nullable = false)
    private Double rateOrKg;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type", nullable = false)
    private CommercialCurrency currencyType;

    @NotNull
    @Column(name = "total", nullable = false)
    private Double total;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "create_on")
    private LocalDate createOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private String updatedOn;

    @ManyToOne
    @JsonIgnoreProperties("commercialPurchaseOrderItems")
    private CommercialPurchaseOrder commercialPurchaseOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsOrServices() {
        return goodsOrServices;
    }

    public CommercialPurchaseOrderItem goodsOrServices(String goodsOrServices) {
        this.goodsOrServices = goodsOrServices;
        return this;
    }

    public void setGoodsOrServices(String goodsOrServices) {
        this.goodsOrServices = goodsOrServices;
    }

    public String getDescriptionOfGoodsOrServices() {
        return descriptionOfGoodsOrServices;
    }

    public CommercialPurchaseOrderItem descriptionOfGoodsOrServices(String descriptionOfGoodsOrServices) {
        this.descriptionOfGoodsOrServices = descriptionOfGoodsOrServices;
        return this;
    }

    public void setDescriptionOfGoodsOrServices(String descriptionOfGoodsOrServices) {
        this.descriptionOfGoodsOrServices = descriptionOfGoodsOrServices;
    }

    public String getPackaging() {
        return packaging;
    }

    public CommercialPurchaseOrderItem packaging(String packaging) {
        this.packaging = packaging;
        return this;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getSizeOrGrade() {
        return sizeOrGrade;
    }

    public CommercialPurchaseOrderItem sizeOrGrade(String sizeOrGrade) {
        this.sizeOrGrade = sizeOrGrade;
        return this;
    }

    public void setSizeOrGrade(String sizeOrGrade) {
        this.sizeOrGrade = sizeOrGrade;
    }

    public Double getQtyOrMc() {
        return qtyOrMc;
    }

    public CommercialPurchaseOrderItem qtyOrMc(Double qtyOrMc) {
        this.qtyOrMc = qtyOrMc;
        return this;
    }

    public void setQtyOrMc(Double qtyOrMc) {
        this.qtyOrMc = qtyOrMc;
    }

    public Double getQtyOrKgs() {
        return qtyOrKgs;
    }

    public CommercialPurchaseOrderItem qtyOrKgs(Double qtyOrKgs) {
        this.qtyOrKgs = qtyOrKgs;
        return this;
    }

    public void setQtyOrKgs(Double qtyOrKgs) {
        this.qtyOrKgs = qtyOrKgs;
    }

    public Double getRateOrKg() {
        return rateOrKg;
    }

    public CommercialPurchaseOrderItem rateOrKg(Double rateOrKg) {
        this.rateOrKg = rateOrKg;
        return this;
    }

    public void setRateOrKg(Double rateOrKg) {
        this.rateOrKg = rateOrKg;
    }

    public CommercialCurrency getCurrencyType() {
        return currencyType;
    }

    public CommercialPurchaseOrderItem currencyType(CommercialCurrency currencyType) {
        this.currencyType = currencyType;
        return this;
    }

    public void setCurrencyType(CommercialCurrency currencyType) {
        this.currencyType = currencyType;
    }

    public Double getTotal() {
        return total;
    }

    public CommercialPurchaseOrderItem total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CommercialPurchaseOrderItem createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreateOn() {
        return createOn;
    }

    public CommercialPurchaseOrderItem createOn(LocalDate createOn) {
        this.createOn = createOn;
        return this;
    }

    public void setCreateOn(LocalDate createOn) {
        this.createOn = createOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CommercialPurchaseOrderItem updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public CommercialPurchaseOrderItem updatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public CommercialPurchaseOrder getCommercialPurchaseOrder() {
        return commercialPurchaseOrder;
    }

    public CommercialPurchaseOrderItem commercialPurchaseOrder(CommercialPurchaseOrder commercialPurchaseOrder) {
        this.commercialPurchaseOrder = commercialPurchaseOrder;
        return this;
    }

    public void setCommercialPurchaseOrder(CommercialPurchaseOrder commercialPurchaseOrder) {
        this.commercialPurchaseOrder = commercialPurchaseOrder;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommercialPurchaseOrderItem commercialPurchaseOrderItem = (CommercialPurchaseOrderItem) o;
        if (commercialPurchaseOrderItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPurchaseOrderItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPurchaseOrderItem{" +
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
            "}";
    }
}
