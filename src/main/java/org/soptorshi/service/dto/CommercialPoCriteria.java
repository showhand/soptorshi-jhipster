package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.CommercialPoStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the CommercialPo entity. This class is used in CommercialPoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /commercial-pos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommercialPoCriteria implements Serializable {
    /**
     * Class for filtering CommercialPoStatus
     */
    public static class CommercialPoStatusFilter extends Filter<CommercialPoStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter purchaseOrderNo;

    private LocalDateFilter purchaseOrderDate;

    private StringFilter originOfGoods;

    private StringFilter finalDestination;

    private LocalDateFilter shipmentDate;

    private CommercialPoStatusFilter poStatus;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private LongFilter commercialPiId;

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

    public CommercialPoStatusFilter getPoStatus() {
        return poStatus;
    }

    public void setPoStatus(CommercialPoStatusFilter poStatus) {
        this.poStatus = poStatus;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public InstantFilter getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(InstantFilter updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LongFilter getCommercialPiId() {
        return commercialPiId;
    }

    public void setCommercialPiId(LongFilter commercialPiId) {
        this.commercialPiId = commercialPiId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommercialPoCriteria that = (CommercialPoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(purchaseOrderNo, that.purchaseOrderNo) &&
            Objects.equals(purchaseOrderDate, that.purchaseOrderDate) &&
            Objects.equals(originOfGoods, that.originOfGoods) &&
            Objects.equals(finalDestination, that.finalDestination) &&
            Objects.equals(shipmentDate, that.shipmentDate) &&
            Objects.equals(poStatus, that.poStatus) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(commercialPiId, that.commercialPiId);
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
        poStatus,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        commercialPiId
        );
    }

    @Override
    public String toString() {
        return "CommercialPoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (purchaseOrderNo != null ? "purchaseOrderNo=" + purchaseOrderNo + ", " : "") +
                (purchaseOrderDate != null ? "purchaseOrderDate=" + purchaseOrderDate + ", " : "") +
                (originOfGoods != null ? "originOfGoods=" + originOfGoods + ", " : "") +
                (finalDestination != null ? "finalDestination=" + finalDestination + ", " : "") +
                (shipmentDate != null ? "shipmentDate=" + shipmentDate + ", " : "") +
                (poStatus != null ? "poStatus=" + poStatus + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (commercialPiId != null ? "commercialPiId=" + commercialPiId + ", " : "") +
            "}";
    }

}
