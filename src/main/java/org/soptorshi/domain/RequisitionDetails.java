package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.UnitOfMeasurements;

/**
 * A RequisitionDetails.
 */
@Entity
@Table(name = "requisition_details")
@Document(indexName = "requisitiondetails")
public class RequisitionDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "required_on")
    private LocalDate requiredOn;

    @Column(name = "estimated_date")
    private LocalDate estimatedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "uom")
    private UnitOfMeasurements uom;

    @Column(name = "unit")
    private Integer unit;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "quantity", precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("requisitionDetails")
    private ProductCategory productCategory;

    @ManyToOne
    @JsonIgnoreProperties("requisitionDetails")
    private Requisition requisition;

    @ManyToOne
    @JsonIgnoreProperties("requisitionDetails")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRequiredOn() {
        return requiredOn;
    }

    public RequisitionDetails requiredOn(LocalDate requiredOn) {
        this.requiredOn = requiredOn;
        return this;
    }

    public void setRequiredOn(LocalDate requiredOn) {
        this.requiredOn = requiredOn;
    }

    public LocalDate getEstimatedDate() {
        return estimatedDate;
    }

    public RequisitionDetails estimatedDate(LocalDate estimatedDate) {
        this.estimatedDate = estimatedDate;
        return this;
    }

    public void setEstimatedDate(LocalDate estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public UnitOfMeasurements getUom() {
        return uom;
    }

    public RequisitionDetails uom(UnitOfMeasurements uom) {
        this.uom = uom;
        return this;
    }

    public void setUom(UnitOfMeasurements uom) {
        this.uom = uom;
    }

    public Integer getUnit() {
        return unit;
    }

    public RequisitionDetails unit(Integer unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public RequisitionDetails unitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public RequisitionDetails quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public RequisitionDetails modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public RequisitionDetails modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public RequisitionDetails productCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
        return this;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public Requisition getRequisition() {
        return requisition;
    }

    public RequisitionDetails requisition(Requisition requisition) {
        this.requisition = requisition;
        return this;
    }

    public void setRequisition(Requisition requisition) {
        this.requisition = requisition;
    }

    public Product getProduct() {
        return product;
    }

    public RequisitionDetails product(Product product) {
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
        RequisitionDetails requisitionDetails = (RequisitionDetails) o;
        if (requisitionDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requisitionDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequisitionDetails{" +
            "id=" + getId() +
            ", requiredOn='" + getRequiredOn() + "'" +
            ", estimatedDate='" + getEstimatedDate() + "'" +
            ", uom='" + getUom() + "'" +
            ", unit=" + getUnit() +
            ", unitPrice=" + getUnitPrice() +
            ", quantity=" + getQuantity() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
