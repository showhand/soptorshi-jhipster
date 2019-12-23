package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.CommercialStatus;

/**
 * A CommercialPoStatus.
 */
@Entity
@Table(name = "commercial_po_status")
@Document(indexName = "commercialpostatus")
public class CommercialPoStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CommercialStatus status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private LocalDate createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    @ManyToOne
    @JsonIgnoreProperties("commercialPoStatuses")
    private CommercialPurchaseOrder commercialPurchaseOrder;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CommercialStatus getStatus() {
        return status;
    }

    public CommercialPoStatus status(CommercialStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(CommercialStatus status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CommercialPoStatus createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public CommercialPoStatus createdOn(LocalDate createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CommercialPoStatus updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public CommercialPoStatus updatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
    }

    public CommercialPurchaseOrder getCommercialPurchaseOrder() {
        return commercialPurchaseOrder;
    }

    public CommercialPoStatus commercialPurchaseOrder(CommercialPurchaseOrder commercialPurchaseOrder) {
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
        CommercialPoStatus commercialPoStatus = (CommercialPoStatus) o;
        if (commercialPoStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPoStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPoStatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
