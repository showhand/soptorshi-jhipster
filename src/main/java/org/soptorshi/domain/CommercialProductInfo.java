package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

/**
 * A CommercialProductInfo.
 */
@Entity
@Table(name = "commercial_product_info")
@Document(indexName = "commercialproductinfo")
public class CommercialProductInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "serial_no", nullable = false)
    private Integer serialNo;

    @Column(name = "packaging_description")
    private String packagingDescription;

    @Column(name = "others_description")
    private String othersDescription;

    @NotNull
    @Column(name = "offered_quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal offeredQuantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "offered_unit", nullable = false)
    private UnitOfMeasurements offeredUnit;

    @NotNull
    @Column(name = "offered_unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal offeredUnitPrice;

    @NotNull
    @Column(name = "offered_total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal offeredTotalPrice;

    @NotNull
    @Column(name = "buying_quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal buyingQuantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "buying_unit", nullable = false)
    private UnitOfMeasurements buyingUnit;

    @NotNull
    @Column(name = "buying_unit_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal buyingUnitPrice;

    @NotNull
    @Column(name = "buying_total_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal buyingTotalPrice;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @ManyToOne
    @JsonIgnoreProperties("commercialProductInfos")
    private CommercialBudget commercialBudget;

    @ManyToOne
    @JsonIgnoreProperties("commercialProductInfos")
    private ProductCategory productCategories;

    @ManyToOne
    @JsonIgnoreProperties("commercialProductInfos")
    private Product products;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public CommercialProductInfo serialNo(Integer serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public String getPackagingDescription() {
        return packagingDescription;
    }

    public CommercialProductInfo packagingDescription(String packagingDescription) {
        this.packagingDescription = packagingDescription;
        return this;
    }

    public void setPackagingDescription(String packagingDescription) {
        this.packagingDescription = packagingDescription;
    }

    public String getOthersDescription() {
        return othersDescription;
    }

    public CommercialProductInfo othersDescription(String othersDescription) {
        this.othersDescription = othersDescription;
        return this;
    }

    public void setOthersDescription(String othersDescription) {
        this.othersDescription = othersDescription;
    }

    public BigDecimal getOfferedQuantity() {
        return offeredQuantity;
    }

    public CommercialProductInfo offeredQuantity(BigDecimal offeredQuantity) {
        this.offeredQuantity = offeredQuantity;
        return this;
    }

    public void setOfferedQuantity(BigDecimal offeredQuantity) {
        this.offeredQuantity = offeredQuantity;
    }

    public UnitOfMeasurements getOfferedUnit() {
        return offeredUnit;
    }

    public CommercialProductInfo offeredUnit(UnitOfMeasurements offeredUnit) {
        this.offeredUnit = offeredUnit;
        return this;
    }

    public void setOfferedUnit(UnitOfMeasurements offeredUnit) {
        this.offeredUnit = offeredUnit;
    }

    public BigDecimal getOfferedUnitPrice() {
        return offeredUnitPrice;
    }

    public CommercialProductInfo offeredUnitPrice(BigDecimal offeredUnitPrice) {
        this.offeredUnitPrice = offeredUnitPrice;
        return this;
    }

    public void setOfferedUnitPrice(BigDecimal offeredUnitPrice) {
        this.offeredUnitPrice = offeredUnitPrice;
    }

    public BigDecimal getOfferedTotalPrice() {
        return offeredTotalPrice;
    }

    public CommercialProductInfo offeredTotalPrice(BigDecimal offeredTotalPrice) {
        this.offeredTotalPrice = offeredTotalPrice;
        return this;
    }

    public void setOfferedTotalPrice(BigDecimal offeredTotalPrice) {
        this.offeredTotalPrice = offeredTotalPrice;
    }

    public BigDecimal getBuyingQuantity() {
        return buyingQuantity;
    }

    public CommercialProductInfo buyingQuantity(BigDecimal buyingQuantity) {
        this.buyingQuantity = buyingQuantity;
        return this;
    }

    public void setBuyingQuantity(BigDecimal buyingQuantity) {
        this.buyingQuantity = buyingQuantity;
    }

    public UnitOfMeasurements getBuyingUnit() {
        return buyingUnit;
    }

    public CommercialProductInfo buyingUnit(UnitOfMeasurements buyingUnit) {
        this.buyingUnit = buyingUnit;
        return this;
    }

    public void setBuyingUnit(UnitOfMeasurements buyingUnit) {
        this.buyingUnit = buyingUnit;
    }

    public BigDecimal getBuyingUnitPrice() {
        return buyingUnitPrice;
    }

    public CommercialProductInfo buyingUnitPrice(BigDecimal buyingUnitPrice) {
        this.buyingUnitPrice = buyingUnitPrice;
        return this;
    }

    public void setBuyingUnitPrice(BigDecimal buyingUnitPrice) {
        this.buyingUnitPrice = buyingUnitPrice;
    }

    public BigDecimal getBuyingTotalPrice() {
        return buyingTotalPrice;
    }

    public CommercialProductInfo buyingTotalPrice(BigDecimal buyingTotalPrice) {
        this.buyingTotalPrice = buyingTotalPrice;
        return this;
    }

    public void setBuyingTotalPrice(BigDecimal buyingTotalPrice) {
        this.buyingTotalPrice = buyingTotalPrice;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CommercialProductInfo createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public CommercialProductInfo createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CommercialProductInfo updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public CommercialProductInfo updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public CommercialBudget getCommercialBudget() {
        return commercialBudget;
    }

    public CommercialProductInfo commercialBudget(CommercialBudget commercialBudget) {
        this.commercialBudget = commercialBudget;
        return this;
    }

    public void setCommercialBudget(CommercialBudget commercialBudget) {
        this.commercialBudget = commercialBudget;
    }

    public ProductCategory getProductCategories() {
        return productCategories;
    }

    public CommercialProductInfo productCategories(ProductCategory productCategory) {
        this.productCategories = productCategory;
        return this;
    }

    public void setProductCategories(ProductCategory productCategory) {
        this.productCategories = productCategory;
    }

    public Product getProducts() {
        return products;
    }

    public CommercialProductInfo products(Product product) {
        this.products = product;
        return this;
    }

    public void setProducts(Product product) {
        this.products = product;
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
        CommercialProductInfo commercialProductInfo = (CommercialProductInfo) o;
        if (commercialProductInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialProductInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialProductInfo{" +
            "id=" + getId() +
            ", serialNo=" + getSerialNo() +
            ", packagingDescription='" + getPackagingDescription() + "'" +
            ", othersDescription='" + getOthersDescription() + "'" +
            ", offeredQuantity=" + getOfferedQuantity() +
            ", offeredUnit='" + getOfferedUnit() + "'" +
            ", offeredUnitPrice=" + getOfferedUnitPrice() +
            ", offeredTotalPrice=" + getOfferedTotalPrice() +
            ", buyingQuantity=" + getBuyingQuantity() +
            ", buyingUnit='" + getBuyingUnit() + "'" +
            ", buyingUnitPrice=" + getBuyingUnitPrice() +
            ", buyingTotalPrice=" + getBuyingTotalPrice() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
