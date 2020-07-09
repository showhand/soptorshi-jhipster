package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A SupplyMoneyCollection.
 */
@Entity
@Table(name = "supply_money_collection")
@Document(indexName = "supplymoneycollection")
public class SupplyMoneyCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "received_amount")
    private Double receivedAmount;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyMoneyCollections")
    private SupplyZone supplyZone;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyMoneyCollections")
    private SupplyZoneManager supplyZoneManager;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyMoneyCollections")
    private SupplyArea supplyArea;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyMoneyCollections")
    private SupplyAreaManager supplyAreaManager;

    @ManyToOne
    @JsonIgnoreProperties("supplyMoneyCollections")
    private SupplySalesRepresentative supplySalesRepresentative;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyMoneyCollections")
    private SupplyShop supplyShop;

    @ManyToOne
    @JsonIgnoreProperties("supplyMoneyCollections")
    private SupplyOrder supplyOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public SupplyMoneyCollection totalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getReceivedAmount() {
        return receivedAmount;
    }

    public SupplyMoneyCollection receivedAmount(Double receivedAmount) {
        this.receivedAmount = receivedAmount;
        return this;
    }

    public void setReceivedAmount(Double receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public SupplyMoneyCollection remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SupplyMoneyCollection createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public SupplyMoneyCollection createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public SupplyMoneyCollection updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public SupplyMoneyCollection updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public SupplyZone getSupplyZone() {
        return supplyZone;
    }

    public SupplyMoneyCollection supplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
        return this;
    }

    public void setSupplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
    }

    public SupplyZoneManager getSupplyZoneManager() {
        return supplyZoneManager;
    }

    public SupplyMoneyCollection supplyZoneManager(SupplyZoneManager supplyZoneManager) {
        this.supplyZoneManager = supplyZoneManager;
        return this;
    }

    public void setSupplyZoneManager(SupplyZoneManager supplyZoneManager) {
        this.supplyZoneManager = supplyZoneManager;
    }

    public SupplyArea getSupplyArea() {
        return supplyArea;
    }

    public SupplyMoneyCollection supplyArea(SupplyArea supplyArea) {
        this.supplyArea = supplyArea;
        return this;
    }

    public void setSupplyArea(SupplyArea supplyArea) {
        this.supplyArea = supplyArea;
    }

    public SupplyAreaManager getSupplyAreaManager() {
        return supplyAreaManager;
    }

    public SupplyMoneyCollection supplyAreaManager(SupplyAreaManager supplyAreaManager) {
        this.supplyAreaManager = supplyAreaManager;
        return this;
    }

    public void setSupplyAreaManager(SupplyAreaManager supplyAreaManager) {
        this.supplyAreaManager = supplyAreaManager;
    }

    public SupplySalesRepresentative getSupplySalesRepresentative() {
        return supplySalesRepresentative;
    }

    public SupplyMoneyCollection supplySalesRepresentative(SupplySalesRepresentative supplySalesRepresentative) {
        this.supplySalesRepresentative = supplySalesRepresentative;
        return this;
    }

    public void setSupplySalesRepresentative(SupplySalesRepresentative supplySalesRepresentative) {
        this.supplySalesRepresentative = supplySalesRepresentative;
    }

    public SupplyShop getSupplyShop() {
        return supplyShop;
    }

    public SupplyMoneyCollection supplyShop(SupplyShop supplyShop) {
        this.supplyShop = supplyShop;
        return this;
    }

    public void setSupplyShop(SupplyShop supplyShop) {
        this.supplyShop = supplyShop;
    }

    public SupplyOrder getSupplyOrder() {
        return supplyOrder;
    }

    public SupplyMoneyCollection supplyOrder(SupplyOrder supplyOrder) {
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
        SupplyMoneyCollection supplyMoneyCollection = (SupplyMoneyCollection) o;
        if (supplyMoneyCollection.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyMoneyCollection.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyMoneyCollection{" +
            "id=" + getId() +
            ", totalAmount=" + getTotalAmount() +
            ", receivedAmount=" + getReceivedAmount() +
            ", remarks='" + getRemarks() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
