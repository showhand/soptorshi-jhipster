package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.CommercialCurrency;

/**
 * A CommercialWorkOrderDetails.
 */
@Entity
@Table(name = "commercial_work_order_details")
@Document(indexName = "commercialworkorderdetails")
public class CommercialWorkOrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "goods", nullable = false)
    private String goods;

    @Column(name = "reason")
    private String reason;

    @Column(name = "jhi_size")
    private String size;

    @Column(name = "color")
    private String color;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type", nullable = false)
    private CommercialCurrency currencyType;

    @NotNull
    @Column(name = "rate", nullable = false)
    private Double rate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    @ManyToOne
    @JsonIgnoreProperties("commercialWorkOrderDetails")
    private CommercialWorkOrder commercialWorkOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoods() {
        return goods;
    }

    public CommercialWorkOrderDetails goods(String goods) {
        this.goods = goods;
        return this;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getReason() {
        return reason;
    }

    public CommercialWorkOrderDetails reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSize() {
        return size;
    }

    public CommercialWorkOrderDetails size(String size) {
        this.size = size;
        return this;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public CommercialWorkOrderDetails color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getQuantity() {
        return quantity;
    }

    public CommercialWorkOrderDetails quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public CommercialCurrency getCurrencyType() {
        return currencyType;
    }

    public CommercialWorkOrderDetails currencyType(CommercialCurrency currencyType) {
        this.currencyType = currencyType;
        return this;
    }

    public void setCurrencyType(CommercialCurrency currencyType) {
        this.currencyType = currencyType;
    }

    public Double getRate() {
        return rate;
    }

    public CommercialWorkOrderDetails rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CommercialWorkOrderDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public CommercialWorkOrderDetails createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CommercialWorkOrderDetails updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public CommercialWorkOrderDetails updatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public CommercialWorkOrder getCommercialWorkOrder() {
        return commercialWorkOrder;
    }

    public CommercialWorkOrderDetails commercialWorkOrder(CommercialWorkOrder commercialWorkOrder) {
        this.commercialWorkOrder = commercialWorkOrder;
        return this;
    }

    public void setCommercialWorkOrder(CommercialWorkOrder commercialWorkOrder) {
        this.commercialWorkOrder = commercialWorkOrder;
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
        CommercialWorkOrderDetails commercialWorkOrderDetails = (CommercialWorkOrderDetails) o;
        if (commercialWorkOrderDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialWorkOrderDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialWorkOrderDetails{" +
            "id=" + getId() +
            ", goods='" + getGoods() + "'" +
            ", reason='" + getReason() + "'" +
            ", size='" + getSize() + "'" +
            ", color='" + getColor() + "'" +
            ", quantity=" + getQuantity() +
            ", currencyType='" + getCurrencyType() + "'" +
            ", rate=" + getRate() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
