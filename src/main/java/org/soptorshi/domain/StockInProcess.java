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
 * A StockInProcess.
 */
@Entity
@Table(name = "stock_in_process")
@Document(indexName = "stockinprocess")
public class StockInProcess implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "total_quantity", nullable = false)
    private Double totalQuantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false)
    private ItemUnit unit;

    @NotNull
    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @NotNull
    @Column(name = "total_container", nullable = false)
    private Integer totalContainer;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "container_category", nullable = false)
    private ContainerCategory containerCategory;

    @NotNull
    @Column(name = "container_tracking_id", nullable = false)
    private String containerTrackingId;

    @NotNull
    @Column(name = "item_per_container", nullable = false)
    private String itemPerContainer;

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
    @JsonIgnoreProperties("stockInProcesses")
    private ItemCategory itemCategories;

    @ManyToOne
    @JsonIgnoreProperties("stockInProcesses")
    private ItemSubCategory itemSubCategories;

    @ManyToOne
    @JsonIgnoreProperties("stockInProcesses")
    private InventoryLocation inventoryLocations;

    @ManyToOne
    @JsonIgnoreProperties("stockInProcesses")
    private InventorySubLocation inventorySubLocations;

    @ManyToOne
    @JsonIgnoreProperties("stockInProcesses")
    private Manufacturer manufacturers;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalQuantity() {
        return totalQuantity;
    }

    public StockInProcess totalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
        return this;
    }

    public void setTotalQuantity(Double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public ItemUnit getUnit() {
        return unit;
    }

    public StockInProcess unit(ItemUnit unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(ItemUnit unit) {
        this.unit = unit;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public StockInProcess unitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getTotalContainer() {
        return totalContainer;
    }

    public StockInProcess totalContainer(Integer totalContainer) {
        this.totalContainer = totalContainer;
        return this;
    }

    public void setTotalContainer(Integer totalContainer) {
        this.totalContainer = totalContainer;
    }

    public ContainerCategory getContainerCategory() {
        return containerCategory;
    }

    public StockInProcess containerCategory(ContainerCategory containerCategory) {
        this.containerCategory = containerCategory;
        return this;
    }

    public void setContainerCategory(ContainerCategory containerCategory) {
        this.containerCategory = containerCategory;
    }

    public String getContainerTrackingId() {
        return containerTrackingId;
    }

    public StockInProcess containerTrackingId(String containerTrackingId) {
        this.containerTrackingId = containerTrackingId;
        return this;
    }

    public void setContainerTrackingId(String containerTrackingId) {
        this.containerTrackingId = containerTrackingId;
    }

    public String getItemPerContainer() {
        return itemPerContainer;
    }

    public StockInProcess itemPerContainer(String itemPerContainer) {
        this.itemPerContainer = itemPerContainer;
        return this;
    }

    public void setItemPerContainer(String itemPerContainer) {
        this.itemPerContainer = itemPerContainer;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public StockInProcess expiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStockInBy() {
        return stockInBy;
    }

    public StockInProcess stockInBy(String stockInBy) {
        this.stockInBy = stockInBy;
        return this;
    }

    public void setStockInBy(String stockInBy) {
        this.stockInBy = stockInBy;
    }

    public Instant getStockInDate() {
        return stockInDate;
    }

    public StockInProcess stockInDate(Instant stockInDate) {
        this.stockInDate = stockInDate;
        return this;
    }

    public void setStockInDate(Instant stockInDate) {
        this.stockInDate = stockInDate;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public StockInProcess purchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
        return this;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getRemarks() {
        return remarks;
    }

    public StockInProcess remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ItemCategory getItemCategories() {
        return itemCategories;
    }

    public StockInProcess itemCategories(ItemCategory itemCategory) {
        this.itemCategories = itemCategory;
        return this;
    }

    public void setItemCategories(ItemCategory itemCategory) {
        this.itemCategories = itemCategory;
    }

    public ItemSubCategory getItemSubCategories() {
        return itemSubCategories;
    }

    public StockInProcess itemSubCategories(ItemSubCategory itemSubCategory) {
        this.itemSubCategories = itemSubCategory;
        return this;
    }

    public void setItemSubCategories(ItemSubCategory itemSubCategory) {
        this.itemSubCategories = itemSubCategory;
    }

    public InventoryLocation getInventoryLocations() {
        return inventoryLocations;
    }

    public StockInProcess inventoryLocations(InventoryLocation inventoryLocation) {
        this.inventoryLocations = inventoryLocation;
        return this;
    }

    public void setInventoryLocations(InventoryLocation inventoryLocation) {
        this.inventoryLocations = inventoryLocation;
    }

    public InventorySubLocation getInventorySubLocations() {
        return inventorySubLocations;
    }

    public StockInProcess inventorySubLocations(InventorySubLocation inventorySubLocation) {
        this.inventorySubLocations = inventorySubLocation;
        return this;
    }

    public void setInventorySubLocations(InventorySubLocation inventorySubLocation) {
        this.inventorySubLocations = inventorySubLocation;
    }

    public Manufacturer getManufacturers() {
        return manufacturers;
    }

    public StockInProcess manufacturers(Manufacturer manufacturer) {
        this.manufacturers = manufacturer;
        return this;
    }

    public void setManufacturers(Manufacturer manufacturer) {
        this.manufacturers = manufacturer;
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
        StockInProcess stockInProcess = (StockInProcess) o;
        if (stockInProcess.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stockInProcess.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StockInProcess{" +
            "id=" + getId() +
            ", totalQuantity=" + getTotalQuantity() +
            ", unit='" + getUnit() + "'" +
            ", unitPrice=" + getUnitPrice() +
            ", totalContainer=" + getTotalContainer() +
            ", containerCategory='" + getContainerCategory() + "'" +
            ", containerTrackingId='" + getContainerTrackingId() + "'" +
            ", itemPerContainer='" + getItemPerContainer() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", stockInBy='" + getStockInBy() + "'" +
            ", stockInDate='" + getStockInDate() + "'" +
            ", purchaseOrderId='" + getPurchaseOrderId() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
