package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.SupplyZoneWiseAccumulationStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the SupplyZoneWiseAccumulation entity. This class is used in SupplyZoneWiseAccumulationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /supply-zone-wise-accumulations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplyZoneWiseAccumulationCriteria implements Serializable {
    /**
     * Class for filtering SupplyZoneWiseAccumulationStatus
     */
    public static class SupplyZoneWiseAccumulationStatusFilter extends Filter<SupplyZoneWiseAccumulationStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter zoneWiseAccumulationRefNo;

    private BigDecimalFilter quantity;

    private BigDecimalFilter price;

    private SupplyZoneWiseAccumulationStatusFilter status;

    private StringFilter remarks;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private LongFilter supplyZoneId;

    private LongFilter supplyZoneManagerId;

    private LongFilter productCategoryId;

    private LongFilter productId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getZoneWiseAccumulationRefNo() {
        return zoneWiseAccumulationRefNo;
    }

    public void setZoneWiseAccumulationRefNo(StringFilter zoneWiseAccumulationRefNo) {
        this.zoneWiseAccumulationRefNo = zoneWiseAccumulationRefNo;
    }

    public BigDecimalFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimalFilter quantity) {
        this.quantity = quantity;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
    }

    public SupplyZoneWiseAccumulationStatusFilter getStatus() {
        return status;
    }

    public void setStatus(SupplyZoneWiseAccumulationStatusFilter status) {
        this.status = status;
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

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplyZoneWiseAccumulationCriteria that = (SupplyZoneWiseAccumulationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(zoneWiseAccumulationRefNo, that.zoneWiseAccumulationRefNo) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(price, that.price) &&
            Objects.equals(status, that.status) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(supplyZoneId, that.supplyZoneId) &&
            Objects.equals(supplyZoneManagerId, that.supplyZoneManagerId) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        zoneWiseAccumulationRefNo,
        quantity,
        price,
        status,
        remarks,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        supplyZoneId,
        supplyZoneManagerId,
        productCategoryId,
        productId
        );
    }

    @Override
    public String toString() {
        return "SupplyZoneWiseAccumulationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (zoneWiseAccumulationRefNo != null ? "zoneWiseAccumulationRefNo=" + zoneWiseAccumulationRefNo + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (supplyZoneId != null ? "supplyZoneId=" + supplyZoneId + ", " : "") +
                (supplyZoneManagerId != null ? "supplyZoneManagerId=" + supplyZoneManagerId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
