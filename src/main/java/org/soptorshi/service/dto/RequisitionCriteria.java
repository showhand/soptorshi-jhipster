package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.RequisitionType;
import org.soptorshi.domain.enumeration.RequisitionStatus;
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
 * Criteria class for the Requisition entity. This class is used in RequisitionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /requisitions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RequisitionCriteria implements Serializable {
    /**
     * Class for filtering RequisitionType
     */
    public static class RequisitionTypeFilter extends Filter<RequisitionType> {
    }
    /**
     * Class for filtering RequisitionStatus
     */
    public static class RequisitionStatusFilter extends Filter<RequisitionStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter requisitionNo;

    private RequisitionTypeFilter requisitionType;

    private LocalDateFilter requisitionDate;

    private BigDecimalFilter amount;

    private RequisitionStatusFilter status;

    private BooleanFilter selected;

    private LongFilter refToHead;

    private LongFilter refToPurchaseCommittee;

    private LongFilter refToCfo;

    private LongFilter commercialId;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter commentsId;

    private LongFilter employeeId;

    private LongFilter officeId;

    private LongFilter productCategoryId;

    private LongFilter departmentId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRequisitionNo() {
        return requisitionNo;
    }

    public void setRequisitionNo(StringFilter requisitionNo) {
        this.requisitionNo = requisitionNo;
    }

    public RequisitionTypeFilter getRequisitionType() {
        return requisitionType;
    }

    public void setRequisitionType(RequisitionTypeFilter requisitionType) {
        this.requisitionType = requisitionType;
    }

    public LocalDateFilter getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(LocalDateFilter requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public RequisitionStatusFilter getStatus() {
        return status;
    }

    public void setStatus(RequisitionStatusFilter status) {
        this.status = status;
    }

    public BooleanFilter getSelected() {
        return selected;
    }

    public void setSelected(BooleanFilter selected) {
        this.selected = selected;
    }

    public LongFilter getRefToHead() {
        return refToHead;
    }

    public void setRefToHead(LongFilter refToHead) {
        this.refToHead = refToHead;
    }

    public LongFilter getRefToPurchaseCommittee() {
        return refToPurchaseCommittee;
    }

    public void setRefToPurchaseCommittee(LongFilter refToPurchaseCommittee) {
        this.refToPurchaseCommittee = refToPurchaseCommittee;
    }

    public LongFilter getRefToCfo() {
        return refToCfo;
    }

    public void setRefToCfo(LongFilter refToCfo) {
        this.refToCfo = refToCfo;
    }

    public LongFilter getCommercialId() {
        return commercialId;
    }

    public void setCommercialId(LongFilter commercialId) {
        this.commercialId = commercialId;
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

    public LongFilter getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(LongFilter commentsId) {
        this.commentsId = commentsId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getOfficeId() {
        return officeId;
    }

    public void setOfficeId(LongFilter officeId) {
        this.officeId = officeId;
    }

    public LongFilter getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(LongFilter productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public LongFilter getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(LongFilter departmentId) {
        this.departmentId = departmentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RequisitionCriteria that = (RequisitionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(requisitionNo, that.requisitionNo) &&
            Objects.equals(requisitionType, that.requisitionType) &&
            Objects.equals(requisitionDate, that.requisitionDate) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(status, that.status) &&
            Objects.equals(selected, that.selected) &&
            Objects.equals(refToHead, that.refToHead) &&
            Objects.equals(refToPurchaseCommittee, that.refToPurchaseCommittee) &&
            Objects.equals(refToCfo, that.refToCfo) &&
            Objects.equals(commercialId, that.commercialId) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(commentsId, that.commentsId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(officeId, that.officeId) &&
            Objects.equals(productCategoryId, that.productCategoryId) &&
            Objects.equals(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        requisitionNo,
        requisitionType,
        requisitionDate,
        amount,
        status,
        selected,
        refToHead,
        refToPurchaseCommittee,
        refToCfo,
        commercialId,
        modifiedBy,
        modifiedOn,
        commentsId,
        employeeId,
        officeId,
        productCategoryId,
        departmentId
        );
    }

    @Override
    public String toString() {
        return "RequisitionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (requisitionNo != null ? "requisitionNo=" + requisitionNo + ", " : "") +
                (requisitionType != null ? "requisitionType=" + requisitionType + ", " : "") +
                (requisitionDate != null ? "requisitionDate=" + requisitionDate + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (selected != null ? "selected=" + selected + ", " : "") +
                (refToHead != null ? "refToHead=" + refToHead + ", " : "") +
                (refToPurchaseCommittee != null ? "refToPurchaseCommittee=" + refToPurchaseCommittee + ", " : "") +
                (refToCfo != null ? "refToCfo=" + refToCfo + ", " : "") +
                (commercialId != null ? "commercialId=" + commercialId + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (commentsId != null ? "commentsId=" + commentsId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (officeId != null ? "officeId=" + officeId + ", " : "") +
                (productCategoryId != null ? "productCategoryId=" + productCategoryId + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            "}";
    }

}
