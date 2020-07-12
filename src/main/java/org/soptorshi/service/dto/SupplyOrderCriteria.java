package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.SupplyOrderStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the SupplyOrder entity. This class is used in SupplyOrderResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /supply-orders?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplyOrderCriteria implements Serializable {
    /**
     * Class for filtering SupplyOrderStatus
     */
    public static class SupplyOrderStatusFilter extends Filter<SupplyOrderStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter orderNo;

    private LocalDateFilter dateOfOrder;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private LocalDateFilter deliveryDate;

    private SupplyOrderStatusFilter status;

    private StringFilter areaWiseAccumulationRefNo;

    private StringFilter remarks;

    private LongFilter supplyZoneId;

    private LongFilter supplyZoneManagerId;

    private LongFilter supplyAreaId;

    private LongFilter supplySalesRepresentativeId;

    private LongFilter supplyAreaManagerId;

    private LongFilter supplyShopId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(StringFilter orderNo) {
        this.orderNo = orderNo;
    }

    public LocalDateFilter getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(LocalDateFilter dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
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

    public LocalDateFilter getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateFilter deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public SupplyOrderStatusFilter getStatus() {
        return status;
    }

    public void setStatus(SupplyOrderStatusFilter status) {
        this.status = status;
    }

    public StringFilter getAreaWiseAccumulationRefNo() {
        return areaWiseAccumulationRefNo;
    }

    public void setAreaWiseAccumulationRefNo(StringFilter areaWiseAccumulationRefNo) {
        this.areaWiseAccumulationRefNo = areaWiseAccumulationRefNo;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
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

    public LongFilter getSupplySalesRepresentativeId() {
        return supplySalesRepresentativeId;
    }

    public void setSupplySalesRepresentativeId(LongFilter supplySalesRepresentativeId) {
        this.supplySalesRepresentativeId = supplySalesRepresentativeId;
    }

    public LongFilter getSupplyAreaManagerId() {
        return supplyAreaManagerId;
    }

    public void setSupplyAreaManagerId(LongFilter supplyAreaManagerId) {
        this.supplyAreaManagerId = supplyAreaManagerId;
    }

    public LongFilter getSupplyShopId() {
        return supplyShopId;
    }

    public void setSupplyShopId(LongFilter supplyShopId) {
        this.supplyShopId = supplyShopId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplyOrderCriteria that = (SupplyOrderCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(orderNo, that.orderNo) &&
            Objects.equals(dateOfOrder, that.dateOfOrder) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(deliveryDate, that.deliveryDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(areaWiseAccumulationRefNo, that.areaWiseAccumulationRefNo) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(supplyZoneId, that.supplyZoneId) &&
            Objects.equals(supplyZoneManagerId, that.supplyZoneManagerId) &&
            Objects.equals(supplyAreaId, that.supplyAreaId) &&
            Objects.equals(supplySalesRepresentativeId, that.supplySalesRepresentativeId) &&
            Objects.equals(supplyAreaManagerId, that.supplyAreaManagerId) &&
            Objects.equals(supplyShopId, that.supplyShopId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        orderNo,
        dateOfOrder,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        deliveryDate,
        status,
        areaWiseAccumulationRefNo,
        remarks,
        supplyZoneId,
        supplyZoneManagerId,
        supplyAreaId,
        supplySalesRepresentativeId,
        supplyAreaManagerId,
        supplyShopId
        );
    }

    @Override
    public String toString() {
        return "SupplyOrderCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orderNo != null ? "orderNo=" + orderNo + ", " : "") +
                (dateOfOrder != null ? "dateOfOrder=" + dateOfOrder + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (deliveryDate != null ? "deliveryDate=" + deliveryDate + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (areaWiseAccumulationRefNo != null ? "areaWiseAccumulationRefNo=" + areaWiseAccumulationRefNo + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (supplyZoneId != null ? "supplyZoneId=" + supplyZoneId + ", " : "") +
                (supplyZoneManagerId != null ? "supplyZoneManagerId=" + supplyZoneManagerId + ", " : "") +
                (supplyAreaId != null ? "supplyAreaId=" + supplyAreaId + ", " : "") +
                (supplySalesRepresentativeId != null ? "supplySalesRepresentativeId=" + supplySalesRepresentativeId + ", " : "") +
                (supplyAreaManagerId != null ? "supplyAreaManagerId=" + supplyAreaManagerId + ", " : "") +
                (supplyShopId != null ? "supplyShopId=" + supplyShopId + ", " : "") +
            "}";
    }

}
