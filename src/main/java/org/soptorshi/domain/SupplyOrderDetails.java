package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A SupplyOrderDetails.
 */
@Entity
@Table(name = "supply_order_details")
@Document(indexName = "supplyorderdetails")
public class SupplyOrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "product_name", nullable = false)
    private String productName;

    @NotNull
    @Column(name = "product_volume", nullable = false)
    private Double productVolume;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @ManyToOne
    @JsonIgnoreProperties("supplyOrderDetails")
    private SupplyOrder supplyOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public SupplyOrderDetails productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductVolume() {
        return productVolume;
    }

    public SupplyOrderDetails productVolume(Double productVolume) {
        this.productVolume = productVolume;
        return this;
    }

    public void setProductVolume(Double productVolume) {
        this.productVolume = productVolume;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public SupplyOrderDetails totalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SupplyOrderDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public SupplyOrderDetails createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public SupplyOrderDetails updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public SupplyOrderDetails updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public SupplyOrder getSupplyOrder() {
        return supplyOrder;
    }

    public SupplyOrderDetails supplyOrder(SupplyOrder supplyOrder) {
        this.supplyOrder = supplyOrder;
        return this;
    }

    public void setSupplyOrder(SupplyOrder supplyOrder) {
        this.supplyOrder = supplyOrder;
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
        SupplyOrderDetails supplyOrderDetails = (SupplyOrderDetails) o;
        if (supplyOrderDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyOrderDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyOrderDetails{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", productVolume=" + getProductVolume() +
            ", totalPrice=" + getTotalPrice() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
