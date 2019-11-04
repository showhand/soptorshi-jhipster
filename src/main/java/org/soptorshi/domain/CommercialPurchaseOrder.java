package org.soptorshi.domain;


import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CommercialPurchaseOrder.
 */
@Entity
@Table(name = "commercial_purchase_order")
@Document(indexName = "commercialpurchaseorder")
public class CommercialPurchaseOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "purchase_order_no", nullable = false)
    private String purchaseOrderNo;

    @NotNull
    @Column(name = "purchase_order_date", nullable = false)
    private LocalDate purchaseOrderDate;

    @Column(name = "origin_of_goods")
    private String originOfGoods;

    @Column(name = "final_destination")
    private String finalDestination;

    @NotNull
    @Column(name = "shipment_date", nullable = false)
    private LocalDate shipmentDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "create_on")
    private LocalDate createOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private LocalDate updatedOn;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public CommercialPurchaseOrder purchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
        return this;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public LocalDate getPurchaseOrderDate() {
        return purchaseOrderDate;
    }

    public CommercialPurchaseOrder purchaseOrderDate(LocalDate purchaseOrderDate) {
        this.purchaseOrderDate = purchaseOrderDate;
        return this;
    }

    public void setPurchaseOrderDate(LocalDate purchaseOrderDate) {
        this.purchaseOrderDate = purchaseOrderDate;
    }

    public String getOriginOfGoods() {
        return originOfGoods;
    }

    public CommercialPurchaseOrder originOfGoods(String originOfGoods) {
        this.originOfGoods = originOfGoods;
        return this;
    }

    public void setOriginOfGoods(String originOfGoods) {
        this.originOfGoods = originOfGoods;
    }

    public String getFinalDestination() {
        return finalDestination;
    }

    public CommercialPurchaseOrder finalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
        return this;
    }

    public void setFinalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
    }

    public LocalDate getShipmentDate() {
        return shipmentDate;
    }

    public CommercialPurchaseOrder shipmentDate(LocalDate shipmentDate) {
        this.shipmentDate = shipmentDate;
        return this;
    }

    public void setShipmentDate(LocalDate shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CommercialPurchaseOrder createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreateOn() {
        return createOn;
    }

    public CommercialPurchaseOrder createOn(LocalDate createOn) {
        this.createOn = createOn;
        return this;
    }

    public void setCreateOn(LocalDate createOn) {
        this.createOn = createOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CommercialPurchaseOrder updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDate getUpdatedOn() {
        return updatedOn;
    }

    public CommercialPurchaseOrder updatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(LocalDate updatedOn) {
        this.updatedOn = updatedOn;
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
        CommercialPurchaseOrder commercialPurchaseOrder = (CommercialPurchaseOrder) o;
        if (commercialPurchaseOrder.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPurchaseOrder.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPurchaseOrder{" +
            "id=" + getId() +
            ", purchaseOrderNo='" + getPurchaseOrderNo() + "'" +
            ", purchaseOrderDate='" + getPurchaseOrderDate() + "'" +
            ", originOfGoods='" + getOriginOfGoods() + "'" +
            ", finalDestination='" + getFinalDestination() + "'" +
            ", shipmentDate='" + getShipmentDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createOn='" + getCreateOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
