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
 * Criteria class for the SupplyChallan entity. This class is used in SupplyChallanResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /supply-challans?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplyChallanCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter challanNo;

    private LocalDateFilter dateOfChallan;

    private StringFilter remarks;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private LongFilter supplyOrderId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(StringFilter challanNo) {
        this.challanNo = challanNo;
    }

    public LocalDateFilter getDateOfChallan() {
        return dateOfChallan;
    }

    public void setDateOfChallan(LocalDateFilter dateOfChallan) {
        this.dateOfChallan = dateOfChallan;
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
        final SupplyChallanCriteria that = (SupplyChallanCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(challanNo, that.challanNo) &&
            Objects.equals(dateOfChallan, that.dateOfChallan) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(supplyOrderId, that.supplyOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        challanNo,
        dateOfChallan,
        remarks,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        supplyOrderId
        );
    }

    @Override
    public String toString() {
        return "SupplyChallanCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (challanNo != null ? "challanNo=" + challanNo + ", " : "") +
                (dateOfChallan != null ? "dateOfChallan=" + dateOfChallan + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (supplyOrderId != null ? "supplyOrderId=" + supplyOrderId + ", " : "") +
            "}";
    }

}
