package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.soptorshi.domain.enumeration.ContainerCategory;
import org.soptorshi.domain.enumeration.ProductType;
import org.soptorshi.domain.enumeration.StockInProcessStatus;
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
    @Column(name = "total_quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalQuantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "unit", nullable = false)
    private UnitOfMeasurements unit;

    @NotNull
    @Column(name = "unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "total_container")
    private Integer totalContainer;

    @Enumerated(EnumType.STRING)
    @Column(name = "container_category")
    private ContainerCategory containerCategory;

    @Column(name = "container_tracking_id")
    private String containerTrackingId;

    @Column(name = "quantity_per_container")
    private String quantityPerContainer;

    @Column(name = "mfg_date")
    private LocalDate mfgDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_product")
    private ProductType typeOfProduct;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StockInProcessStatus status;

    @Column(name = "process_started_by")
    private String processStartedBy;

    @Column(name = "process_started_on")
    private Instant processStartedOn;

    @Column(name = "stock_in_by")
    private String stockInBy;

    @Column(name = "stock_in_date")
    private Instant stockInDate;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JsonIgnoreProperties("stockInProcesses")
    private ProductCategory productCategories;

    @ManyToOne
    @JsonIgnoreProperties("stockInProcesses")
    private Product products;

    @ManyToOne
    @JsonIgnoreProperties("stockInProcesses")
    private InventoryLocation inventoryLocations;

    @ManyToOne
    @JsonIgnoreProperties("stockInProcesses")
    private InventorySubLocation inventorySubLocations;

    @ManyToOne
    @JsonIgnoreProperties("stockInProcesses")
    private Vendor vendor;

    @ManyToOne
    @JsonIgnoreProperties("stockInProcesses")
    private Requisition requisitions;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public StockInProcess totalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
        return this;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public UnitOfMeasurements getUnit() {
        return unit;
    }

    public StockInProcess unit(UnitOfMeasurements unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(UnitOfMeasurements unit) {
        this.unit = unit;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public StockInProcess unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
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

    public String getQuantityPerContainer() {
        return quantityPerContainer;
    }

    public StockInProcess quantityPerContainer(String quantityPerContainer) {
        this.quantityPerContainer = quantityPerContainer;
        return this;
    }

    public void setQuantityPerContainer(String quantityPerContainer) {
        this.quantityPerContainer = quantityPerContainer;
    }

    public LocalDate getMfgDate() {
        return mfgDate;
    }

    public StockInProcess mfgDate(LocalDate mfgDate) {
        this.mfgDate = mfgDate;
        return this;
    }

    public void setMfgDate(LocalDate mfgDate) {
        this.mfgDate = mfgDate;
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

    public ProductType getTypeOfProduct() {
        return typeOfProduct;
    }

    public StockInProcess typeOfProduct(ProductType typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
        return this;
    }

    public void setTypeOfProduct(ProductType typeOfProduct) {
        this.typeOfProduct = typeOfProduct;
    }

    public StockInProcessStatus getStatus() {
        return status;
    }

    public StockInProcess status(StockInProcessStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(StockInProcessStatus status) {
        this.status = status;
    }

    public String getProcessStartedBy() {
        return processStartedBy;
    }

    public StockInProcess processStartedBy(String processStartedBy) {
        this.processStartedBy = processStartedBy;
        return this;
    }

    public void setProcessStartedBy(String processStartedBy) {
        this.processStartedBy = processStartedBy;
    }

    public Instant getProcessStartedOn() {
        return processStartedOn;
    }

    public StockInProcess processStartedOn(Instant processStartedOn) {
        this.processStartedOn = processStartedOn;
        return this;
    }

    public void setProcessStartedOn(Instant processStartedOn) {
        this.processStartedOn = processStartedOn;
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

    public ProductCategory getProductCategories() {
        return productCategories;
    }

    public StockInProcess productCategories(ProductCategory productCategory) {
        this.productCategories = productCategory;
        return this;
    }

    public void setProductCategories(ProductCategory productCategory) {
        this.productCategories = productCategory;
    }

    public Product getProducts() {
        return products;
    }

    public StockInProcess products(Product product) {
        this.products = product;
        return this;
    }

    public void setProducts(Product product) {
        this.products = product;
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

    public Vendor getVendor() {
        return vendor;
    }

    public StockInProcess vendor(Vendor vendor) {
        this.vendor = vendor;
        return this;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Requisition getRequisitions() {
        return requisitions;
    }

    public StockInProcess requisitions(Requisition requisition) {
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
            ", quantityPerContainer='" + getQuantityPerContainer() + "'" +
            ", mfgDate='" + getMfgDate() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", typeOfProduct='" + getTypeOfProduct() + "'" +
            ", status='" + getStatus() + "'" +
            ", processStartedBy='" + getProcessStartedBy() + "'" +
            ", processStartedOn='" + getProcessStartedOn() + "'" +
            ", stockInBy='" + getStockInBy() + "'" +
            ", stockInDate='" + getStockInDate() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
