package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.soptorshi.domain.enumeration.SupplyAreaWiseAccumulationStatus;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A SupplyAreaWiseAccumulation.
 */
@Entity
@Table(name = "supply_area_wise_accumulation")
@Document(indexName = "supplyareawiseaccumulation")
public class SupplyAreaWiseAccumulation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "area_wise_accumulation_ref_no", nullable = false)
    private String areaWiseAccumulationRefNo;

    @NotNull
    @Column(name = "quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal quantity;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SupplyAreaWiseAccumulationStatus status;

    @Column(name = "zone_wise_accumulation_ref_no")
    private String zoneWiseAccumulationRefNo;

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
    @JsonIgnoreProperties("supplyAreaWiseAccumulations")
    private SupplyZone supplyZone;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyAreaWiseAccumulations")
    private SupplyZoneManager supplyZoneManager;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyAreaWiseAccumulations")
    private SupplyArea supplyArea;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyAreaWiseAccumulations")
    private SupplyAreaManager supplyAreaManager;

    @ManyToOne
    @JsonIgnoreProperties("supplyAreaWiseAccumulations")
    private ProductCategory productCategory;

    @ManyToOne
    @JsonIgnoreProperties("supplyAreaWiseAccumulations")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaWiseAccumulationRefNo() {
        return areaWiseAccumulationRefNo;
    }

    public SupplyAreaWiseAccumulation areaWiseAccumulationRefNo(String areaWiseAccumulationRefNo) {
        this.areaWiseAccumulationRefNo = areaWiseAccumulationRefNo;
        return this;
    }

    public void setAreaWiseAccumulationRefNo(String areaWiseAccumulationRefNo) {
        this.areaWiseAccumulationRefNo = areaWiseAccumulationRefNo;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public SupplyAreaWiseAccumulation quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SupplyAreaWiseAccumulation price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public SupplyAreaWiseAccumulationStatus getStatus() {
        return status;
    }

    public SupplyAreaWiseAccumulation status(SupplyAreaWiseAccumulationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(SupplyAreaWiseAccumulationStatus status) {
        this.status = status;
    }

    public String getZoneWiseAccumulationRefNo() {
        return zoneWiseAccumulationRefNo;
    }

    public SupplyAreaWiseAccumulation zoneWiseAccumulationRefNo(String zoneWiseAccumulationRefNo) {
        this.zoneWiseAccumulationRefNo = zoneWiseAccumulationRefNo;
        return this;
    }

    public void setZoneWiseAccumulationRefNo(String zoneWiseAccumulationRefNo) {
        this.zoneWiseAccumulationRefNo = zoneWiseAccumulationRefNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public SupplyAreaWiseAccumulation remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SupplyAreaWiseAccumulation createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public SupplyAreaWiseAccumulation createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public SupplyAreaWiseAccumulation updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public SupplyAreaWiseAccumulation updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public SupplyZone getSupplyZone() {
        return supplyZone;
    }

    public SupplyAreaWiseAccumulation supplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
        return this;
    }

    public void setSupplyZone(SupplyZone supplyZone) {
        this.supplyZone = supplyZone;
    }

    public SupplyZoneManager getSupplyZoneManager() {
        return supplyZoneManager;
    }

    public SupplyAreaWiseAccumulation supplyZoneManager(SupplyZoneManager supplyZoneManager) {
        this.supplyZoneManager = supplyZoneManager;
        return this;
    }

    public void setSupplyZoneManager(SupplyZoneManager supplyZoneManager) {
        this.supplyZoneManager = supplyZoneManager;
    }

    public SupplyArea getSupplyArea() {
        return supplyArea;
    }

    public SupplyAreaWiseAccumulation supplyArea(SupplyArea supplyArea) {
        this.supplyArea = supplyArea;
        return this;
    }

    public void setSupplyArea(SupplyArea supplyArea) {
        this.supplyArea = supplyArea;
    }

    public SupplyAreaManager getSupplyAreaManager() {
        return supplyAreaManager;
    }

    public SupplyAreaWiseAccumulation supplyAreaManager(SupplyAreaManager supplyAreaManager) {
        this.supplyAreaManager = supplyAreaManager;
        return this;
    }

    public void setSupplyAreaManager(SupplyAreaManager supplyAreaManager) {
        this.supplyAreaManager = supplyAreaManager;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public SupplyAreaWiseAccumulation productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Product getProduct() {
        return product;
    }

    public SupplyAreaWiseAccumulation product(Product product) {
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
        SupplyAreaWiseAccumulation supplyAreaWiseAccumulation = (SupplyAreaWiseAccumulation) o;
        if (supplyAreaWiseAccumulation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyAreaWiseAccumulation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyAreaWiseAccumulation{" +
            "id=" + getId() +
            ", areaWiseAccumulationRefNo='" + getAreaWiseAccumulationRefNo() + "'" +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            ", status='" + getStatus() + "'" +
            ", zoneWiseAccumulationRefNo='" + getZoneWiseAccumulationRefNo() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
