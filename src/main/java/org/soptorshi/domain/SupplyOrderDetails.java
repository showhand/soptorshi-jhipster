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
 * A SupplyOrderDetails.
 */
@Entity
@Table(name = "supply_order_details")
@Document(indexName = "supplyorderdetails")
public class SupplyOrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @NotNull
    @Column(name = "quantity", precision = 10, scale = 2, nullable = false)
    private BigDecimal quantity;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyOrderDetails")
    private SupplyOrder supplyOrder;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyOrderDetails")
    private ProductCategory productCategory;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplyOrderDetails")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public SupplyOrderDetails createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public SupplyOrderDetails createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public SupplyOrderDetails updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public SupplyOrderDetails updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public SupplyOrderDetails quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SupplyOrderDetails price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public SupplyOrder getSupplyOrder() {
        return supplyOrder;
    }

    public SupplyOrderDetails supplyOrder(SupplyOrder supplyOrder) {
        this.supplyOrder = supplyOrder;
        return this;
    }

    public void setSupplyOrder(SupplyOrder supplyOrder) {
        this.supplyOrder = supplyOrder;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public SupplyOrderDetails productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Product getProduct() {
        return product;
    }

    public SupplyOrderDetails product(Product product) {
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
        SupplyOrderDetails supplyOrderDetails = (SupplyOrderDetails) o;
        if (supplyOrderDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplyOrderDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplyOrderDetails{" +
            "id=" + getId() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            "}";
    }
}
