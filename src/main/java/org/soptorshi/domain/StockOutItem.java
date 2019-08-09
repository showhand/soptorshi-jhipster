package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A StockOutItem.
 */
@Entity
@Table(name = "stock_out_item")
@Document(indexName = "stockoutitem")
public class StockOutItem implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "container_tracking_id", nullable = false)
    private String containerTrackingId;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "stock_out_by")
    private String stockOutBy;

    @Column(name = "stock_out_date")
    private Instant stockOutDate;

    @Column(name = "receiver_id")
    private String receiverId;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JsonIgnoreProperties("stockOutItems")
    private ItemCategory itemCategories;

    @ManyToOne
    @JsonIgnoreProperties("stockOutItems")
    private ItemSubCategory itemSubCategories;

    @ManyToOne
    @JsonIgnoreProperties("stockOutItems")
    private InventoryLocation inventoryLocations;

    @ManyToOne
    @JsonIgnoreProperties("stockOutItems")
    private InventorySubLocation inventorySubLocations;

    @ManyToOne
    @JsonIgnoreProperties("stockOutItems")
    private StockInItem stockInItems;

    @ManyToOne
    @JsonIgnoreProperties("stockOutItems")
    private StockStatus stockStatuses;

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

    public StockOutItem containerTrackingId(String containerTrackingId) {
        this.containerTrackingId = containerTrackingId;
        return this;
    }

    public void setContainerTrackingId(String containerTrackingId) {
        this.containerTrackingId = containerTrackingId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public StockOutItem quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getStockOutBy() {
        return stockOutBy;
    }

    public StockOutItem stockOutBy(String stockOutBy) {
        this.stockOutBy = stockOutBy;
        return this;
    }

    public void setStockOutBy(String stockOutBy) {
        this.stockOutBy = stockOutBy;
    }

    public Instant getStockOutDate() {
        return stockOutDate;
    }

    public StockOutItem stockOutDate(Instant stockOutDate) {
        this.stockOutDate = stockOutDate;
        return this;
    }

    public void setStockOutDate(Instant stockOutDate) {
        this.stockOutDate = stockOutDate;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public StockOutItem receiverId(String receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getRemarks() {
        return remarks;
    }

    public StockOutItem remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ItemCategory getItemCategories() {
        return itemCategories;
    }

    public StockOutItem itemCategories(ItemCategory itemCategory) {
        this.itemCategories = itemCategory;
        return this;
    }

    public void setItemCategories(ItemCategory itemCategory) {
        this.itemCategories = itemCategory;
    }

    public ItemSubCategory getItemSubCategories() {
        return itemSubCategories;
    }

    public StockOutItem itemSubCategories(ItemSubCategory itemSubCategory) {
        this.itemSubCategories = itemSubCategory;
        return this;
    }

    public void setItemSubCategories(ItemSubCategory itemSubCategory) {
        this.itemSubCategories = itemSubCategory;
    }

    public InventoryLocation getInventoryLocations() {
        return inventoryLocations;
    }

    public StockOutItem inventoryLocations(InventoryLocation inventoryLocation) {
        this.inventoryLocations = inventoryLocation;
        return this;
    }

    public void setInventoryLocations(InventoryLocation inventoryLocation) {
        this.inventoryLocations = inventoryLocation;
    }

    public InventorySubLocation getInventorySubLocations() {
        return inventorySubLocations;
    }

    public StockOutItem inventorySubLocations(InventorySubLocation inventorySubLocation) {
        this.inventorySubLocations = inventorySubLocation;
        return this;
    }

    public void setInventorySubLocations(InventorySubLocation inventorySubLocation) {
        this.inventorySubLocations = inventorySubLocation;
    }

    public StockInItem getStockInItems() {
        return stockInItems;
    }

    public StockOutItem stockInItems(StockInItem stockInItem) {
        this.stockInItems = stockInItem;
        return this;
    }

    public void setStockInItems(StockInItem stockInItem) {
        this.stockInItems = stockInItem;
    }

    public StockStatus getStockStatuses() {
        return stockStatuses;
    }

    public StockOutItem stockStatuses(StockStatus stockStatus) {
        this.stockStatuses = stockStatus;
        return this;
    }

    public void setStockStatuses(StockStatus stockStatus) {
        this.stockStatuses = stockStatus;
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
        StockOutItem stockOutItem = (StockOutItem) o;
        if (stockOutItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockOutItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockOutItem{" +
            "id=" + getId() +
            ", containerTrackingId='" + getContainerTrackingId() + "'" +
            ", quantity=" + getQuantity() +
            ", stockOutBy='" + getStockOutBy() + "'" +
            ", stockOutDate='" + getStockOutDate() + "'" +
            ", receiverId='" + getReceiverId() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
