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
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the SupplyOrder entity. This class is used in SupplyOrderResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /supply-orders?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplyOrderCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter orderNo;

    private LocalDateFilter dateOfOrder;

    private StringFilter offer;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private LongFilter supplyZoneId;

    private LongFilter supplyAreaId;

    private LongFilter supplyAreaManagerId;

    private LongFilter supplySalesRepresentativeId;

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

    public StringFilter getOffer() {
        return offer;
    }

    public void setOffer(StringFilter offer) {
        this.offer = offer;
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
            Objects.equals(offer, that.offer) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(supplyZoneId, that.supplyZoneId) &&
            Objects.equals(supplyAreaId, that.supplyAreaId) &&
            Objects.equals(supplyAreaManagerId, that.supplyAreaManagerId) &&
            Objects.equals(supplySalesRepresentativeId, that.supplySalesRepresentativeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        orderNo,
        dateOfOrder,
        offer,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        supplyZoneId,
        supplyAreaId,
        supplyAreaManagerId,
        supplySalesRepresentativeId
        );
    }

    @Override
    public String toString() {
        return "SupplyOrderCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orderNo != null ? "orderNo=" + orderNo + ", " : "") +
                (dateOfOrder != null ? "dateOfOrder=" + dateOfOrder + ", " : "") +
                (offer != null ? "offer=" + offer + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (supplyZoneId != null ? "supplyZoneId=" + supplyZoneId + ", " : "") +
                (supplyAreaId != null ? "supplyAreaId=" + supplyAreaId + ", " : "") +
                (supplyAreaManagerId != null ? "supplyAreaManagerId=" + supplyAreaManagerId + ", " : "") +
                (supplySalesRepresentativeId != null ? "supplySalesRepresentativeId=" + supplySalesRepresentativeId + ", " : "") +
            "}";
    }

}
