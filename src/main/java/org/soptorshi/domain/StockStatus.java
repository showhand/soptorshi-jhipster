package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A StockStatus.
 */
@Entity
@Table(name = "stock_status")
@Document(indexName = "stockstatus")
public class StockStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "container_tracking_id", nullable = false)
    private String containerTrackingId;

    @NotNull
    @Column(name = "total_quantity", nullable = false)
    private Double totalQuantity;

    @NotNull
    @Column(name = "available_quantity", nullable = false)
    private Double availableQuantity;

    @NotNull
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @NotNull
    @Column(name = "available_price", nullable = false)
    private Double availablePrice;

    @Column(name = "stock_in_by")
    private String stockInBy;

    @Column(name = "stock_in_date")
    private Instant stockInDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("stockStatuses")
    private ItemCategory itemCategories;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("stockStatuses")
    private ItemSubCategory itemSubCategories;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("stockStatuses")
    private InventoryLocation inventoryLocations;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("stockStatuses")
    private InventorySubLocation inventorySubLocations;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContainerTrackingId() {
        return containerTrackingId;
    }

    public StockStatus containerTrackingId(String containerTrackingId) {
        this.containerTrackingId = containerTrackingId;
        return this;
    }

    public void setContainerTrackingId(String containerTrackingId) {
        this.containerTrackingId = containerTrackingId;
    }

    public Double getTotalQuantity() {
        return totalQuantity;
    }

    public StockStatus totalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
        return this;
    }

    public void setTotalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Double getAvailableQuantity() {
        return availableQuantity;
    }

    public StockStatus availableQuantity(Double availableQuantity) {
        this.availableQuantity = availableQuantity;
        return this;
    }

    public void setAvailableQuantity(Double availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public StockStatus totalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getAvailablePrice() {
        return availablePrice;
    }

    public StockStatus availablePrice(Double availablePrice) {
        this.availablePrice = availablePrice;
        return this;
    }

    public void setAvailablePrice(Double availablePrice) {
        this.availablePrice = availablePrice;
    }

    public String getStockInBy() {
        return stockInBy;
    }

    public StockStatus stockInBy(String stockInBy) {
        this.stockInBy = stockInBy;
        return this;
    }

    public void setStockInBy(String stockInBy) {
        this.stockInBy = stockInBy;
    }

    public Instant getStockInDate() {
        return stockInDate;
    }

    public StockStatus stockInDate(Instant stockInDate) {
        this.stockInDate = stockInDate;
        return this;
    }

    public void setStockInDate(Instant stockInDate) {
        this.stockInDate = stockInDate;
    }

    public ItemCategory getItemCategories() {
        return itemCategories;
    }

    public StockStatus itemCategories(ItemCategory itemCategory) {
        this.itemCategories = itemCategory;
        return this;
    }

    public void setItemCategories(ItemCategory itemCategory) {
        this.itemCategories = itemCategory;
    }

    public ItemSubCategory getItemSubCategories() {
        return itemSubCategories;
    }

    public StockStatus itemSubCategories(ItemSubCategory itemSubCategory) {
        this.itemSubCategories = itemSubCategory;
        return this;
    }

    public void setItemSubCategories(ItemSubCategory itemSubCategory) {
        this.itemSubCategories = itemSubCategory;
    }

    public InventoryLocation getInventoryLocations() {
        return inventoryLocations;
    }

    public StockStatus inventoryLocations(InventoryLocation inventoryLocation) {
        this.inventoryLocations = inventoryLocation;
        return this;
    }

    public void setInventoryLocations(InventoryLocation inventoryLocation) {
        this.inventoryLocations = inventoryLocation;
    }

    public InventorySubLocation getInventorySubLocations() {
        return inventorySubLocations;
    }

    public StockStatus inventorySubLocations(InventorySubLocation inventorySubLocation) {
        this.inventorySubLocations = inventorySubLocation;
        return this;
    }

    public void setInventorySubLocations(InventorySubLocation inventorySubLocation) {
        this.inventorySubLocations = inventorySubLocation;
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
        StockStatus stockStatus = (StockStatus) o;
        if (stockStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockStatus{" +
            "id=" + getId() +
            ", containerTrackingId='" + getContainerTrackingId() + "'" +
            ", totalQuantity=" + getTotalQuantity() +
            ", availableQuantity=" + getAvailableQuantity() +
            ", totalPrice=" + getTotalPrice() +
            ", availablePrice=" + getAvailablePrice() +
            ", stockInBy='" + getStockInBy() + "'" +
            ", stockInDate='" + getStockInDate() + "'" +
            "}";
    }
}
