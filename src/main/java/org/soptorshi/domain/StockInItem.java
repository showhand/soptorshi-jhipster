package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.soptorshi.domain.enumeration.ContainerCategory;
import org.soptorshi.domain.enumeration.ProductType;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

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
    @Column(name = "quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal quantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false)
    private UnitOfMeasurements unit;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "container_category", nullable = false)
    private ContainerCategory containerCategory;

    @NotNull
    @Column(name = "container_tracking_id", nullable = false)
    private String containerTrackingId;

    @Column(name = "mfg_date")
    private LocalDate mfgDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_product")
    private ProductType typeOfProduct;

    @Column(name = "stock_in_by")
    private String stockInBy;

    @Column(name = "stock_in_date")
    private Instant stockInDate;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JsonIgnoreProperties("stockInItems")
    private ProductCategory productCategories;

    @ManyToOne
    @JsonIgnoreProperties("stockInItems")
    private Product products;

    @ManyToOne
    @JsonIgnoreProperties("stockInItems")
    private InventoryLocation inventoryLocations;

    @ManyToOne
    @JsonIgnoreProperties("stockInItems")
    private InventorySubLocation inventorySubLocations;

    @ManyToOne
    @JsonIgnoreProperties("stockInItems")
    private Vendor vendor;

    @ManyToOne
    @JsonIgnoreProperties("stockInItems")
    private StockInProcess stockInProcesses;

    @ManyToOne
    @JsonIgnoreProperties("stockInItems")
    private Requisition requisitions;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public StockInItem quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public UnitOfMeasurements getUnit() {
        return unit;
    }

    public StockInItem unit(UnitOfMeasurements unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(UnitOfMeasurements unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public StockInItem price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
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

    public LocalDate getMfgDate() {
        return mfgDate;
    }

    public StockInItem mfgDate(LocalDate mfgDate) {
        this.mfgDate = mfgDate;
        return this;
    }

    public void setMfgDate(LocalDate mfgDate) {
        this.mfgDate = mfgDate;
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

    public ProductType getTypeOfProduct() {
        return typeOfProduct;
    }

    public StockInItem typeOfProduct(ProductType typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
        return this;
    }

    public void setTypeOfProduct(ProductType typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
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

    public ProductCategory getProductCategories() {
        return productCategories;
    }

    public StockInItem productCategories(ProductCategory productCategory) {
        this.productCategories = productCategory;
        return this;
    }

    public void setProductCategories(ProductCategory productCategory) {
        this.productCategories = productCategory;
    }

    public Product getProducts() {
        return products;
    }

    public StockInItem products(Product product) {
        this.products = product;
        return this;
    }

    public void setProducts(Product product) {
        this.products = product;
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

    public Vendor getVendor() {
        return vendor;
    }

    public StockInItem vendor(Vendor vendor) {
        this.vendor = vendor;
        return this;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
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

    public Requisition getRequisitions() {
        return requisitions;
    }

    public StockInItem requisitions(Requisition requisition) {
        this.requisitions = requisition;
        return this;
    }

    public void setRequisitions(Requisition requisition) {
        this.requisitions = requisition;
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
            ", mfgDate='" + getMfgDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", typeOfProduct='" + getTypeOfProduct() + "'" +
            ", stockInBy='" + getStockInBy() + "'" +
            ", stockInDate='" + getStockInDate() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
