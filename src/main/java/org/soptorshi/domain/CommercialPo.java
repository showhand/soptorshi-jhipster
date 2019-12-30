package org.soptorshi.domain;


import org.soptorshi.domain.enumeration.CommercialPoStatus;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A CommercialPo.
 */
@Entity
@Table(name = "commercial_po")
@Document(indexName = "commercialpo")
public class CommercialPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "purchase_order_no", nullable = false)
    private String purchaseOrderNo;

    @Column(name = "purchase_order_date")
    private LocalDate purchaseOrderDate;

    @Column(name = "origin_of_goods")
    private String originOfGoods;

    @Column(name = "final_destination")
    private String finalDestination;

    @Column(name = "shipment_date")
    private LocalDate shipmentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "po_status")
    private CommercialPoStatus poStatus;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_on")
    private Instant updatedOn;

    @OneToOne
    @JoinColumn(unique = true)
    private CommercialPi commercialPi;

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

    public CommercialPo purchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
        return this;
    }

    public void setPurchaseOrderNo(String purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public LocalDate getPurchaseOrderDate() {
        return purchaseOrderDate;
    }

    public CommercialPo purchaseOrderDate(LocalDate purchaseOrderDate) {
        this.purchaseOrderDate = purchaseOrderDate;
        return this;
    }

    public void setPurchaseOrderDate(LocalDate purchaseOrderDate) {
        this.purchaseOrderDate = purchaseOrderDate;
    }

    public String getOriginOfGoods() {
        return originOfGoods;
    }

    public CommercialPo originOfGoods(String originOfGoods) {
        this.originOfGoods = originOfGoods;
        return this;
    }

    public void setOriginOfGoods(String originOfGoods) {
        this.originOfGoods = originOfGoods;
    }

    public String getFinalDestination() {
        return finalDestination;
    }

    public CommercialPo finalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
        return this;
    }

    public void setFinalDestination(String finalDestination) {
        this.finalDestination = finalDestination;
    }

    public LocalDate getShipmentDate() {
        return shipmentDate;
    }

    public CommercialPo shipmentDate(LocalDate shipmentDate) {
        this.shipmentDate = shipmentDate;
        return this;
    }

    public void setShipmentDate(LocalDate shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public CommercialPoStatus getPoStatus() {
        return poStatus;
    }

    public CommercialPo poStatus(CommercialPoStatus poStatus) {
        this.poStatus = poStatus;
        return this;
    }

    public void setPoStatus(CommercialPoStatus poStatus) {
        this.poStatus = poStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CommercialPo createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public CommercialPo createdOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public CommercialPo updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public CommercialPo updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public CommercialPi getCommercialPi() {
        return commercialPi;
    }

    public CommercialPo commercialPi(CommercialPi commercialPi) {
        this.commercialPi = commercialPi;
        return this;
    }

    public void setCommercialPi(CommercialPi commercialPi) {
        this.commercialPi = commercialPi;
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
        CommercialPo commercialPo = (CommercialPo) o;
        if (commercialPo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commercialPo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommercialPo{" +
            "id=" + getId() +
            ", purchaseOrderNo='" + getPurchaseOrderNo() + "'" +
            ", purchaseOrderDate='" + getPurchaseOrderDate() + "'" +
            ", originOfGoods='" + getOriginOfGoods() + "'" +
            ", finalDestination='" + getFinalDestination() + "'" +
            ", shipmentDate='" + getShipmentDate() + "'" +
            ", poStatus='" + getPoStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            "}";
    }
}
