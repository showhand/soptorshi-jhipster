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
 * Criteria class for the CommercialWorkOrder entity. This class is used in CommercialWorkOrderResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /commercial-work-orders?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommercialWorkOrderCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter refNo;

    private LocalDateFilter workOrderDate;

    private LocalDateFilter deliveryDate;

    private StringFilter remarks;

    private StringFilter createdBy;

    private LocalDateFilter createdOn;

    private StringFilter updatedBy;

    private LocalDateFilter updatedOn;

    private LongFilter commercialPurchaseOrderId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRefNo() {
        return refNo;
    }

    public void setRefNo(StringFilter refNo) {
        this.refNo = refNo;
    }

    public LocalDateFilter getWorkOrderDate() {
        return workOrderDate;
    }

    public void setWorkOrderDate(LocalDateFilter workOrderDate) {
        this.workOrderDate = workOrderDate;
    }

    public LocalDateFilter getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateFilter deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    public LongFilter getCommercialPurchaseOrderId() {
        return commercialPurchaseOrderId;
    }

    public void setCommercialPurchaseOrderId(LongFilter commercialPurchaseOrderId) {
        this.commercialPurchaseOrderId = commercialPurchaseOrderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommercialWorkOrderCriteria that = (CommercialWorkOrderCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(refNo, that.refNo) &&
            Objects.equals(workOrderDate, that.workOrderDate) &&
            Objects.equals(deliveryDate, that.deliveryDate) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(commercialPurchaseOrderId, that.commercialPurchaseOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        refNo,
        workOrderDate,
        deliveryDate,
        remarks,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        commercialPurchaseOrderId
        );
    }

    @Override
    public String toString() {
        return "CommercialWorkOrderCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (refNo != null ? "refNo=" + refNo + ", " : "") +
                (workOrderDate != null ? "workOrderDate=" + workOrderDate + ", " : "") +
                (deliveryDate != null ? "deliveryDate=" + deliveryDate + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (commercialPurchaseOrderId != null ? "commercialPurchaseOrderId=" + commercialPurchaseOrderId + ", " : "") +
            "}";
    }

}
