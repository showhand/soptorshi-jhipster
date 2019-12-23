package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

import org.soptorshi.domain.enumeration.UnitOfMeasurements;

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
    @Column(name = "total_quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalQuantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false)
    private UnitOfMeasurements unit;

    @NotNull
    @Column(name = "available_quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal availableQuantity;

    @NotNull
    @Column(name = "total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    @NotNull
    @Column(name = "available_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal availablePrice;

    @Column(name = "stock_in_by")
    private String stockInBy;

    @Column(name = "stock_in_date")
    private Instant stockInDate;

    @OneToOne
    @JoinColumn(unique = true)
    private StockInItem stockInItem;

    @ManyToOne
    @JsonIgnoreProperties("stockStatuses")
    private ProductCategory productCategories;

    @ManyToOne
    @JsonIgnoreProperties("stockStatuses")
    private Product products;

    @ManyToOne
    @JsonIgnoreProperties("stockStatuses")
    private InventoryLocation inventoryLocations;

    @ManyToOne
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

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public StockStatus totalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
        return this;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public UnitOfMeasurements getUnit() {
        return unit;
    }

    public StockStatus unit(UnitOfMeasurements unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(UnitOfMeasurements unit) {
        this.unit = unit;
    }

    public BigDecimal getAvailableQuantity() {
        return availableQuantity;
    }

    public StockStatus availableQuantity(BigDecimal availableQuantity) {
        this.availableQuantity = availableQuantity;
        return this;
    }

    public void setAvailableQuantity(BigDecimal availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public StockStatus totalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getAvailablePrice() {
        return availablePrice;
    }

    public StockStatus availablePrice(BigDecimal availablePrice) {
        this.availablePrice = availablePrice;
        return this;
    }

    public void setAvailablePrice(BigDecimal availablePrice) {
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

    public StockInItem getStockInItem() {
        return stockInItem;
    }

    public StockStatus stockInItem(StockInItem stockInItem) {
        this.stockInItem = stockInItem;
        return this;
    }

    public void setStockInItem(StockInItem stockInItem) {
        this.stockInItem = stockInItem;
    }

    public ProductCategory getProductCategories() {
        return productCategories;
    }

    public StockStatus productCategories(ProductCategory productCategory) {
        this.productCategories = productCategory;
        return this;
    }

    public void setProductCategories(ProductCategory productCategory) {
        this.productCategories = productCategory;
    }

    public Product getProducts() {
        return products;
    }

    public StockStatus products(Product product) {
        this.products = product;
        return this;
    }

    public void setProducts(Product product) {
        this.products = product;
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
            ", unit='" + getUnit() + "'" +
            ", availableQuantity=" + getAvailableQuantity() +
            ", totalPrice=" + getTotalPrice() +
            ", availablePrice=" + getAvailablePrice() +
            ", stockInBy='" + getStockInBy() + "'" +
            ", stockInDate='" + getStockInDate() + "'" +
            "}";
    }
}
