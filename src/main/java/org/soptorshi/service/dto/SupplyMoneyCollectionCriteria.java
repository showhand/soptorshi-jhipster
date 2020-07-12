package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the SupplyMoneyCollection entity. This class is used in SupplyMoneyCollectionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /supply-money-collections?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplyMoneyCollectionCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter totalAmount;

    private DoubleFilter receivedAmount;

    private StringFilter remarks;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private LongFilter supplyZoneId;

    private LongFilter supplyZoneManagerId;

    private LongFilter supplyAreaId;

    private LongFilter supplyAreaManagerId;

    private LongFilter supplySalesRepresentativeId;

    private LongFilter supplyShopId;

    private LongFilter supplyOrderId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(DoubleFilter totalAmount) {
        this.totalAmount = totalAmount;
    }

    public DoubleFilter getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(DoubleFilter receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
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

    public LongFilter getSupplyZoneId() {
        return supplyZoneId;
    }

    public void setSupplyZoneId(LongFilter supplyZoneId) {
        this.supplyZoneId = supplyZoneId;
    }

    public LongFilter getSupplyZoneManagerId() {
        return supplyZoneManagerId;
    }

    public void setSupplyZoneManagerId(LongFilter supplyZoneManagerId) {
        this.supplyZoneManagerId = supplyZoneManagerId;
    }

    public LongFilter getSupplyAreaId() {
        return supplyAreaId;
    }

    public void setSupplyAreaId(LongFilter supplyAreaId) {
        this.supplyAreaId = supplyAreaId;
    }

    public LongFilter getSupplyAreaManagerId() {
        return supplyAreaManagerId;
    }

    public void setSupplyAreaManagerId(LongFilter supplyAreaManagerId) {
        this.supplyAreaManagerId = supplyAreaManagerId;
    }

    public LongFilter getSupplySalesRepresentativeId() {
        return supplySalesRepresentativeId;
    }

    public void setSupplySalesRepresentativeId(LongFilter supplySalesRepresentativeId) {
        this.supplySalesRepresentativeId = supplySalesRepresentativeId;
    }

    public LongFilter getSupplyShopId() {
        return supplyShopId;
    }

    public void setSupplyShopId(LongFilter supplyShopId) {
        this.supplyShopId = supplyShopId;
    }

    public LongFilter getSupplyOrderId() {
        return supplyOrderId;
    }

    public void setSupplyOrderId(LongFilter supplyOrderId) {
        this.supplyOrderId = supplyOrderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplyMoneyCollectionCriteria that = (SupplyMoneyCollectionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(totalAmount, that.totalAmount) &&
            Objects.equals(receivedAmount, that.receivedAmount) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(supplyZoneId, that.supplyZoneId) &&
            Objects.equals(supplyZoneManagerId, that.supplyZoneManagerId) &&
            Objects.equals(supplyAreaId, that.supplyAreaId) &&
            Objects.equals(supplyAreaManagerId, that.supplyAreaManagerId) &&
            Objects.equals(supplySalesRepresentativeId, that.supplySalesRepresentativeId) &&
            Objects.equals(supplyShopId, that.supplyShopId) &&
            Objects.equals(supplyOrderId, that.supplyOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        totalAmount,
        receivedAmount,
        remarks,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        supplyZoneId,
        supplyZoneManagerId,
        supplyAreaId,
        supplyAreaManagerId,
        supplySalesRepresentativeId,
        supplyShopId,
        supplyOrderId
        );
    }

    @Override
    public String toString() {
        return "SupplyMoneyCollectionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "") +
                (receivedAmount != null ? "receivedAmount=" + receivedAmount + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (supplyZoneId != null ? "supplyZoneId=" + supplyZoneId + ", " : "") +
                (supplyZoneManagerId != null ? "supplyZoneManagerId=" + supplyZoneManagerId + ", " : "") +
                (supplyAreaId != null ? "supplyAreaId=" + supplyAreaId + ", " : "") +
                (supplyAreaManagerId != null ? "supplyAreaManagerId=" + supplyAreaManagerId + ", " : "") +
                (supplySalesRepresentativeId != null ? "supplySalesRepresentativeId=" + supplySalesRepresentativeId + ", " : "") +
                (supplyShopId != null ? "supplyShopId=" + supplyShopId + ", " : "") +
                (supplyOrderId != null ? "supplyOrderId=" + supplyOrderId + ", " : "") +
            "}";
    }

}
