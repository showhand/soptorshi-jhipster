package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.ItemUnit;

import org.soptorshi.domain.enumeration.ContainerCategory;

/**
 * A StockInItem.
 */
@Entity
@Table(name = "stock_in_item")
@Document(indexName = "stockinitem")
public class StockInItem implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false)
    private ItemUnit unit;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "container_category", nullable = false)
    private ContainerCategory containerCategory;

    @NotNull
    @Column(name = "container_tracking_id", nullable = false)
    private String containerTrackingId;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "stock_in_by")
    private String stockInBy;

    @Column(name = "stock_in_date")
    private Instant stockInDate;

    @Column(name = "purchase_order_id")
    private String purchaseOrderId;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JsonIgnoreProperties("stockInItems")
    private ItemCategory itemCategories;

    @ManyToOne
    @JsonIgnoreProperties("stockInItems")
    private ItemSubCategory itemSubCategories;

    @ManyToOne
    @JsonIgnoreProperties("stockInItems")
    private InventoryLocation inventoryLocations;

    @ManyToOne
    @JsonIgnoreProperties("stockInItems")
    private InventorySubLocation inventorySubLocations;

    @ManyToOne
    @JsonIgnoreProperties("stockInItems")
    private Manufacturer manufacturers;

    @ManyToOne
    @JsonIgnoreProperties("stockInItems")
    private StockInProcess stockInProcesses;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public StockInItem quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public ItemUnit getUnit() {
        return unit;
    }

    public StockInItem unit(ItemUnit unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(ItemUnit unit) {
        this.unit = unit;
    }

    public Double getPrice() {
        return price;
    }

    public StockInItem price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ContainerCategory getContainerCategory() {
        return containerCategory;
    }

    public StockInItem containerCategory(ContainerCategory containerCategory) {
        this.containerCategory = containerCategory;
        return this;
    }

    public void setContainerCategory(ContainerCategory containerCategory) {
        this.containerCategory = containerCategory;
    }

    public String getContainerTrackingId() {
        return containerTrackingId;
    }

    public StockInItem containerTrackingId(String containerTrackingId) {
        this.containerTrackingId = containerTrackingId;
        return this;
    }

    public void setContainerTrackingId(String containerTrackingId) {
        this.containerTrackingId = containerTrackingId;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public StockInItem expiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStockInBy() {
        return stockInBy;
    }

    public StockInItem stockInBy(String stockInBy) {
        this.stockInBy = stockInBy;
        return this;
    }

    public void setStockInBy(String stockInBy) {
        this.stockInBy = stockInBy;
    }

    public Instant getStockInDate() {
        return stockInDate;
    }

    public StockInItem stockInDate(Instant stockInDate) {
        this.stockInDate = stockInDate;
        return this;
    }

    public void setStockInDate(Instant stockInDate) {
        this.stockInDate = stockInDate;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public StockInItem purchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
        return this;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getRemarks() {
        return remarks;
    }

    public StockInItem remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ItemCategory getItemCategories() {
        return itemCategories;
    }

    public StockInItem itemCategories(ItemCategory itemCategory) {
        this.itemCategories = itemCategory;
        return this;
    }

    public void setItemCategories(ItemCategory itemCategory) {
        this.itemCategories = itemCategory;
    }

    public ItemSubCategory getItemSubCategories() {
        return itemSubCategories;
    }

    public StockInItem itemSubCategories(ItemSubCategory itemSubCategory) {
        this.itemSubCategories = itemSubCategory;
        return this;
    }

    public void setItemSubCategories(ItemSubCategory itemSubCategory) {
        this.itemSubCategories = itemSubCategory;
    }

    public InventoryLocation getInventoryLocations() {
        return inventoryLocations;
    }

    public StockInItem inventoryLocations(InventoryLocation inventoryLocation) {
        this.inventoryLocations = inventoryLocation;
        return this;
    }

    public void setInventoryLocations(InventoryLocation inventoryLocation) {
        this.inventoryLocations = inventoryLocation;
    }

    public InventorySubLocation getInventorySubLocations() {
        return inventorySubLocations;
    }

    public StockInItem inventorySubLocations(InventorySubLocation inventorySubLocation) {
        this.inventorySubLocations = inventorySubLocation;
        return this;
    }

    public void setInventorySubLocations(InventorySubLocation inventorySubLocation) {
        this.inventorySubLocations = inventorySubLocation;
    }

    public Manufacturer getManufacturers() {
        return manufacturers;
    }

    public StockInItem manufacturers(Manufacturer manufacturer) {
        this.manufacturers = manufacturer;
        return this;
    }

    public void setManufacturers(Manufacturer manufacturer) {
        this.manufacturers = manufacturer;
    }

    public StockInProcess getStockInProcesses() {
        return stockInProcesses;
    }

    public StockInItem stockInProcesses(StockInProcess stockInProcess) {
        this.stockInProcesses = stockInProcess;
        return this;
    }

    public void setStockInProcesses(StockInProcess stockInProcess) {
        this.stockInProcesses = stockInProcess;
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
        StockInItem stockInItem = (StockInItem) o;
        if (stockInItem.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockInItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockInItem{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", unit='" + getUnit() + "'" +
            ", price=" + getPrice() +
            ", containerCategory='" + getContainerCategory() + "'" +
            ", containerTrackingId='" + getContainerTrackingId() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", stockInBy='" + getStockInBy() + "'" +
            ", stockInDate='" + getStockInDate() + "'" +
            ", purchaseOrderId='" + getPurchaseOrderId() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
