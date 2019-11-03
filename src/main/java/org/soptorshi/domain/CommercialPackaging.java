package org.soptorshi.domain;


import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CommercialPackaging.
 */
@Entity
@Table(name = "commercial_packaging")
@Document(indexName = "commercialpackaging")
public class CommercialPackaging implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "consignment_no", nullable = false)
    private String consignmentNo;

    @NotNull
    @Column(name = "consignment_date", nullable = false)
    private LocalDate consignmentDate;

    @Column(name = "brand")
    private String brand;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "create_on")
    private LocalDate createOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private String updatedOn;

    @OneToOne
    @JoinColumn(unique = true)
    private CommercialPurchaseOrder commercialPurchaseOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsignmentNo() {
        return consignmentNo;
    }

    public CommercialPackaging consignmentNo(String consignmentNo) {
        this.consignmentNo = consignmentNo;
        return this;
    }

    public void setConsignmentNo(String consignmentNo) {
        this.consignmentNo = consignmentNo;
    }

    public LocalDate getConsignmentDate() {
        return consignmentDate;
    }

    public CommercialPackaging consignmentDate(LocalDate consignmentDate) {
        this.consignmentDate = consignmentDate;
        return this;
    }

    public void setConsignmentDate(LocalDate consignmentDate) {
        this.consignmentDate = consignmentDate;
    }

    public String getBrand() {
        return brand;
    }

    public CommercialPackaging brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CommercialPackaging createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreateOn() {
        return createOn;
    }

    public CommercialPackaging createOn(LocalDate createOn) {
        this.createOn = createOn;
        return this;
    }

    public void setCreateOn(LocalDate createOn) {
        this.createOn = createOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CommercialPackaging updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public CommercialPackaging updatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public CommercialPurchaseOrder getCommercialPurchaseOrder() {
        return commercialPurchaseOrder;
    }

    public CommercialPackaging commercialPurchaseOrder(CommercialPurchaseOrder commercialPurchaseOrder) {
        this.commercialPurchaseOrder = commercialPurchaseOrder;
        return this;
    }

    public void setCommercialPurchaseOrder(CommercialPurchaseOrder commercialPurchaseOrder) {
        this.commercialPurchaseOrder = commercialPurchaseOrder;
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
        CommercialPackaging commercialPackaging = (CommercialPackaging) o;
        if (commercialPackaging.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPackaging.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPackaging{" +
            "id=" + getId() +
            ", consignmentNo='" + getConsignmentNo() + "'" +
            ", consignmentDate='" + getConsignmentDate() + "'" +
            ", brand='" + getBrand() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createOn='" + getCreateOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
