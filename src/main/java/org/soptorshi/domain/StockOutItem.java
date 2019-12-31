package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
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
    @Column(name = "quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal quantity;

    @Column(name = "stock_out_by")
    private String stockOutBy;

    @Column(name = "stock_out_date")
    private Instant stockOutDate;

    @Column(name = "receiver_id")
    private String receiverId;

    @Column(name = "receiving_place")
    private String receivingPlace;

    @Column(name = "remarks")
    private String remarks;

    @ManyToOne
    @JsonIgnoreProperties("stockOutItems")
    private ProductCategory productCategories;

    @ManyToOne
    @JsonIgnoreProperties("stockOutItems")
    private Product products;

    @ManyToOne
    @JsonIgnoreProperties("stockOutItems")
    private InventoryLocation inventoryLocations;

    @ManyToOne
    @JsonIgnoreProperties("stockOutItems")
    private InventorySubLocation inventorySubLocations;

    @ManyToOne
    @JsonIgnoreProperties("stockOutItems")
    private StockInItem stockInItems;

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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public StockOutItem quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
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

    public String getReceivingPlace() {
        return receivingPlace;
    }

    public StockOutItem receivingPlace(String receivingPlace) {
        this.receivingPlace = receivingPlace;
        return this;
    }

    public void setReceivingPlace(String receivingPlace) {
        this.receivingPlace = receivingPlace;
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

    public ProductCategory getProductCategories() {
        return productCategories;
    }

    public StockOutItem productCategories(ProductCategory productCategory) {
        this.productCategories = productCategory;
        return this;
    }

    public void setProductCategories(ProductCategory productCategory) {
        this.productCategories = productCategory;
    }

    public Product getProducts() {
        return products;
    }

    public StockOutItem products(Product product) {
        this.products = product;
        return this;
    }

    public void setProducts(Product product) {
        this.products = product;
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
            ", receivingPlace='" + getReceivingPlace() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
