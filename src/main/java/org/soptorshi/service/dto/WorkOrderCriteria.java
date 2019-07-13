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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the WorkOrder entity. This class is used in WorkOrderResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /work-orders?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WorkOrderCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter referenceNo;

    private LocalDateFilter issueDate;

    private StringFilter referredTo;

    private StringFilter subject;

    private BigDecimalFilter laborOrOtherAmount;

    private DoubleFilter discount;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter requisitionId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(StringFilter referenceNo) {
        this.referenceNo = referenceNo;
    }

    public LocalDateFilter getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateFilter issueDate) {
        this.issueDate = issueDate;
    }

    public StringFilter getReferredTo() {
        return referredTo;
    }

    public void setReferredTo(StringFilter referredTo) {
        this.referredTo = referredTo;
    }

    public StringFilter getSubject() {
        return subject;
    }

    public void setSubject(StringFilter subject) {
        this.subject = subject;
    }

    public BigDecimalFilter getLaborOrOtherAmount() {
        return laborOrOtherAmount;
    }

    public void setLaborOrOtherAmount(BigDecimalFilter laborOrOtherAmount) {
        this.laborOrOtherAmount = laborOrOtherAmount;
    }

    public DoubleFilter getDiscount() {
        return discount;
    }

    public void setDiscount(DoubleFilter discount) {
        this.discount = discount;
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

    public LongFilter getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(LongFilter requisitionId) {
        this.requisitionId = requisitionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final WorkOrderCriteria that = (WorkOrderCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(referenceNo, that.referenceNo) &&
            Objects.equals(issueDate, that.issueDate) &&
            Objects.equals(referredTo, that.referredTo) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(laborOrOtherAmount, that.laborOrOtherAmount) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(requisitionId, that.requisitionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        referenceNo,
        issueDate,
        referredTo,
        subject,
        laborOrOtherAmount,
        discount,
        modifiedBy,
        modifiedOn,
        requisitionId
        );
    }

    @Override
    public String toString() {
        return "WorkOrderCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (referenceNo != null ? "referenceNo=" + referenceNo + ", " : "") +
                (issueDate != null ? "issueDate=" + issueDate + ", " : "") +
                (referredTo != null ? "referredTo=" + referredTo + ", " : "") +
                (subject != null ? "subject=" + subject + ", " : "") +
                (laborOrOtherAmount != null ? "laborOrOtherAmount=" + laborOrOtherAmount + ", " : "") +
                (discount != null ? "discount=" + discount + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (requisitionId != null ? "requisitionId=" + requisitionId + ", " : "") +
            "}";
    }

}
