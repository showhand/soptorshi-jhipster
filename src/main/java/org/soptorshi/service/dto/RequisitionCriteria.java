package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
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
     * Class for filtering RequisitionStatus
     */
    public static class RequisitionStatusFilter extends Filter<RequisitionStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter requisitionNo;

    private LocalDateFilter requisitionDate;

    private BigDecimalFilter amount;

    private RequisitionStatusFilter status;

    private LongFilter refToPurchaseCommittee;

    private LongFilter refToCfo;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter employeeId;

    private LongFilter officeId;

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
            Objects.equals(requisitionDate, that.requisitionDate) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(status, that.status) &&
            Objects.equals(refToPurchaseCommittee, that.refToPurchaseCommittee) &&
            Objects.equals(refToCfo, that.refToCfo) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(officeId, that.officeId) &&
            Objects.equals(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        requisitionNo,
        requisitionDate,
        amount,
        status,
        refToPurchaseCommittee,
        refToCfo,
        modifiedBy,
        modifiedOn,
        employeeId,
        officeId,
        departmentId
        );
    }

    @Override
    public String toString() {
        return "RequisitionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (requisitionNo != null ? "requisitionNo=" + requisitionNo + ", " : "") +
                (requisitionDate != null ? "requisitionDate=" + requisitionDate + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (refToPurchaseCommittee != null ? "refToPurchaseCommittee=" + refToPurchaseCommittee + ", " : "") +
                (refToCfo != null ? "refToCfo=" + refToCfo + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (officeId != null ? "officeId=" + officeId + ", " : "") +
                (departmentId != null ? "departmentId=" + departmentId + ", " : "") +
            "}";
    }

}
