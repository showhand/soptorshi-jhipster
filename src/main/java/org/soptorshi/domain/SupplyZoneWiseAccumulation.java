package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.soptorshi.domain.enumeration.SupplyZoneWiseAccumulationStatus;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A SupplyZoneWiseAccumulation.
 */
@Entity
@Table(name = "supply_zone_wise_accumulation")
@Document(indexName = "supplyzonewiseaccumulation")
public class SupplyZoneWiseAccumulation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "zone_wise_accumulation_ref_no", nullable = false)
    private String zoneWiseAccumulationRefNo;

    @NotNull
    @Column(name = "quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal quantity;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SupplyZoneWiseAccumulationStatus status;

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
    @JsonIgnoreProperties("supplyZoneWiseAccumulations")
    private SupplyZone supplyZone;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyZoneWiseAccumulations")
    private SupplyZoneManager supplyZoneManager;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyZoneWiseAccumulations")
    private ProductCategory productCategory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyZoneWiseAccumulations")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZoneWiseAccumulationRefNo() {
        return zoneWiseAccumulationRefNo;
    }

    public SupplyZoneWiseAccumulation zoneWiseAccumulationRefNo(String zoneWiseAccumulationRefNo) {
        this.zoneWiseAccumulationRefNo = zoneWiseAccumulationRefNo;
        return this;
    }

    public void setZoneWiseAccumulationRefNo(String zoneWiseAccumulationRefNo) {
        this.zoneWiseAccumulationRefNo = zoneWiseAccumulationRefNo;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public SupplyZoneWiseAccumulation quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SupplyZoneWiseAccumulation price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public SupplyZoneWiseAccumulationStatus getStatus() {
        return status;
    }

    public SupplyZoneWiseAccumulation status(SupplyZoneWiseAccumulationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SupplyZoneWiseAccumulationStatus status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public SupplyZoneWiseAccumulation remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SupplyZoneWiseAccumulation createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public SupplyZoneWiseAccumulation createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public SupplyZoneWiseAccumulation updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public SupplyZoneWiseAccumulation updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public SupplyZone getSupplyZone() {
        return supplyZone;
    }

    public SupplyZoneWiseAccumulation supplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
        return this;
    }

    public void setSupplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
    }

    public SupplyZoneManager getSupplyZoneManager() {
        return supplyZoneManager;
    }

    public SupplyZoneWiseAccumulation supplyZoneManager(SupplyZoneManager supplyZoneManager) {
        this.supplyZoneManager = supplyZoneManager;
        return this;
    }

    public void setSupplyZoneManager(SupplyZoneManager supplyZoneManager) {
        this.supplyZoneManager = supplyZoneManager;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public SupplyZoneWiseAccumulation productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Product getProduct() {
        return product;
    }

    public SupplyZoneWiseAccumulation product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
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
        SupplyZoneWiseAccumulation supplyZoneWiseAccumulation = (SupplyZoneWiseAccumulation) o;
        if (supplyZoneWiseAccumulation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyZoneWiseAccumulation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyZoneWiseAccumulation{" +
            "id=" + getId() +
            ", zoneWiseAccumulationRefNo='" + getZoneWiseAccumulationRefNo() + "'" +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            ", status='" + getStatus() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
