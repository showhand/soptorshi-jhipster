package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the CommercialPurchaseOrder entity. This class is used in CommercialPurchaseOrderResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /commercial-purchase-orders?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommercialPurchaseOrderCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter purchaseOrderNo;

    private LocalDateFilter purchaseOrderDate;

    private StringFilter originOfGoods;

    private StringFilter finalDestination;

    private LocalDateFilter shipmentDate;

    private StringFilter createdBy;

    private LocalDateFilter createdOn;

    private StringFilter updatedBy;

    private LocalDateFilter updatedOn;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(StringFilter purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public LocalDateFilter getPurchaseOrderDate() {
        return purchaseOrderDate;
    }

    public void setPurchaseOrderDate(LocalDateFilter purchaseOrderDate) {
        this.purchaseOrderDate = purchaseOrderDate;
    }

    public StringFilter getOriginOfGoods() {
        return originOfGoods;
    }

    public void setOriginOfGoods(StringFilter originOfGoods) {
        this.originOfGoods = originOfGoods;
    }

    public StringFilter getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(StringFilter finalDestination) {
        this.finalDestination = finalDestination;
    }

    public LocalDateFilter getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(LocalDateFilter shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateFilter getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateFilter createdOn) {
        this.createdOn = createdOn;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateFilter getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateFilter updatedOn) {
        this.updatedOn = updatedOn;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommercialPurchaseOrderCriteria that = (CommercialPurchaseOrderCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(purchaseOrderNo, that.purchaseOrderNo) &&
            Objects.equals(purchaseOrderDate, that.purchaseOrderDate) &&
            Objects.equals(originOfGoods, that.originOfGoods) &&
            Objects.equals(finalDestination, that.finalDestination) &&
            Objects.equals(shipmentDate, that.shipmentDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        purchaseOrderNo,
        purchaseOrderDate,
        originOfGoods,
        finalDestination,
        shipmentDate,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn
        );
    }

    @Override
    public String toString() {
        return "CommercialPurchaseOrderCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (purchaseOrderNo != null ? "purchaseOrderNo=" + purchaseOrderNo + ", " : "") +
                (purchaseOrderDate != null ? "purchaseOrderDate=" + purchaseOrderDate + ", " : "") +
                (originOfGoods != null ? "originOfGoods=" + originOfGoods + ", " : "") +
                (finalDestination != null ? "finalDestination=" + finalDestination + ", " : "") +
                (shipmentDate != null ? "shipmentDate=" + shipmentDate + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
            "}";
    }

}
