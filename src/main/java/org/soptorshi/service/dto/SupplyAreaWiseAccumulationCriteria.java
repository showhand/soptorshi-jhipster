package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.SupplyAreaWiseAccumulationStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the SupplyAreaWiseAccumulation entity. This class is used in SupplyAreaWiseAccumulationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /supply-area-wise-accumulations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplyAreaWiseAccumulationCriteria implements Serializable {
    /**
     * Class for filtering SupplyAreaWiseAccumulationStatus
     */
    public static class SupplyAreaWiseAccumulationStatusFilter extends Filter<SupplyAreaWiseAccumulationStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter areaWiseAccumulationRefNo;

    private BigDecimalFilter quantity;

    private BigDecimalFilter price;

    private SupplyAreaWiseAccumulationStatusFilter status;

    private StringFilter zoneWiseAccumulationRefNo;

    private StringFilter remarks;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private LongFilter supplyZoneId;

    private LongFilter supplyZoneManagerId;

    private LongFilter supplyAreaId;

    private LongFilter supplyAreaManagerId;

    private LongFilter productCategoryId;

    private LongFilter productId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAreaWiseAccumulationRefNo() {
        return areaWiseAccumulationRefNo;
    }

    public void setAreaWiseAccumulationRefNo(StringFilter areaWiseAccumulationRefNo) {
        this.areaWiseAccumulationRefNo = areaWiseAccumulationRefNo;
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

    public SupplyAreaWiseAccumulationStatusFilter getStatus() {
        return status;
    }

    public void setStatus(SupplyAreaWiseAccumulationStatusFilter status) {
        this.status = status;
    }

    public StringFilter getZoneWiseAccumulationRefNo() {
        return zoneWiseAccumulationRefNo;
    }

    public void setZoneWiseAccumulationRefNo(StringFilter zoneWiseAccumulationRefNo) {
        this.zoneWiseAccumulationRefNo = zoneWiseAccumulationRefNo;
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
        final SupplyAreaWiseAccumulationCriteria that = (SupplyAreaWiseAccumulationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(areaWiseAccumulationRefNo, that.areaWiseAccumulationRefNo) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(price, that.price) &&
            Objects.equals(status, that.status) &&
            Objects.equals(zoneWiseAccumulationRefNo, that.zoneWiseAccumulationRefNo) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(supplyZoneId, that.supplyZoneId) &&
            Objects.equals(supplyZoneManagerId, that.supplyZoneManagerId) &&
            Objects.equals(supplyAreaId, that.supplyAreaId) &&
            Objects.equals(supplyAreaManagerId, that.supplyAreaManagerId) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        areaWiseAccumulationRefNo,
        quantity,
        price,
        status,
        zoneWiseAccumulationRefNo,
        remarks,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        supplyZoneId,
        supplyZoneManagerId,
        supplyAreaId,
        supplyAreaManagerId,
        productCategoryId,
        productId
        );
    }

    @Override
    public String toString() {
        return "SupplyAreaWiseAccumulationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (areaWiseAccumulationRefNo != null ? "areaWiseAccumulationRefNo=" + areaWiseAccumulationRefNo + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (zoneWiseAccumulationRefNo != null ? "zoneWiseAccumulationRefNo=" + zoneWiseAccumulationRefNo + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (supplyZoneId != null ? "supplyZoneId=" + supplyZoneId + ", " : "") +
                (supplyZoneManagerId != null ? "supplyZoneManagerId=" + supplyZoneManagerId + ", " : "") +
                (supplyAreaId != null ? "supplyAreaId=" + supplyAreaId + ", " : "") +
                (supplyAreaManagerId != null ? "supplyAreaManagerId=" + supplyAreaManagerId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
