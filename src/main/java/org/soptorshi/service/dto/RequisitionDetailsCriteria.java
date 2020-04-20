package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.UnitOfMeasurements;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the RequisitionDetails entity. This class is used in RequisitionDetailsResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /requisition-details?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RequisitionDetailsCriteria implements Serializable {
    /**
     * Class for filtering UnitOfMeasurements
     */
    public static class UnitOfMeasurementsFilter extends Filter<UnitOfMeasurements> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter requiredOn;

    private LocalDateFilter estimatedDate;

    private UnitOfMeasurementsFilter uom;

    private IntegerFilter unit;

    private BigDecimalFilter unitPrice;

    private BigDecimalFilter quantity;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter productCategoryId;

    private LongFilter requisitionId;

    private LongFilter productId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getRequiredOn() {
        return requiredOn;
    }

    public void setRequiredOn(LocalDateFilter requiredOn) {
        this.requiredOn = requiredOn;
    }

    public LocalDateFilter getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(LocalDateFilter estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public UnitOfMeasurementsFilter getUom() {
        return uom;
    }

    public void setUom(UnitOfMeasurementsFilter uom) {
        this.uom = uom;
    }

    public IntegerFilter getUnit() {
        return unit;
    }

    public void setUnit(IntegerFilter unit) {
        this.unit = unit;
    }

    public BigDecimalFilter getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimalFilter unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimalFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimalFilter quantity) {
        this.quantity = quantity;
    }

    public StringFilter getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(StringFilter modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateFilter getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateFilter modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public LongFilter getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(LongFilter requisitionId) {
        this.requisitionId = requisitionId;
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
        final RequisitionDetailsCriteria that = (RequisitionDetailsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(requiredOn, that.requiredOn) &&
            Objects.equals(estimatedDate, that.estimatedDate) &&
            Objects.equals(uom, that.uom) &&
            Objects.equals(unit, that.unit) &&
            Objects.equals(unitPrice, that.unitPrice) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(requisitionId, that.requisitionId) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        requiredOn,
        estimatedDate,
        uom,
        unit,
        unitPrice,
        quantity,
        modifiedBy,
        modifiedOn,
        productCategoryId,
        requisitionId,
        productId
        );
    }

    @Override
    public String toString() {
        return "RequisitionDetailsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (requiredOn != null ? "requiredOn=" + requiredOn + ", " : "") +
                (estimatedDate != null ? "estimatedDate=" + estimatedDate + ", " : "") +
                (uom != null ? "uom=" + uom + ", " : "") +
                (unit != null ? "unit=" + unit + ", " : "") +
                (unitPrice != null ? "unitPrice=" + unitPrice + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (requisitionId != null ? "requisitionId=" + requisitionId + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
            "}";
    }

}
