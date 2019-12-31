package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.PurchaseOrderStatus;
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
 * Criteria class for the PurchaseOrder entity. This class is used in PurchaseOrderResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /purchase-orders?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PurchaseOrderCriteria implements Serializable {
    /**
     * Class for filtering PurchaseOrderStatus
     */
    public static class PurchaseOrderStatusFilter extends Filter<PurchaseOrderStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter purchaseOrderNo;

    private StringFilter workOrderNo;

    private LocalDateFilter issueDate;

    private StringFilter referredTo;

    private StringFilter subject;

    private BigDecimalFilter laborOrOtherAmount;

    private DoubleFilter discount;

    private PurchaseOrderStatusFilter status;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter commentsId;

    private LongFilter requisitionId;

    private LongFilter quotationId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(StringFilter purchaseOrderNo) {
        this.purchaseOrderNo = purchaseOrderNo;
    }

    public StringFilter getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(StringFilter workOrderNo) {
        this.workOrderNo = workOrderNo;
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

    public PurchaseOrderStatusFilter getStatus() {
        return status;
    }

    public void setStatus(PurchaseOrderStatusFilter status) {
        this.status = status;
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

    public LongFilter getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(LongFilter requisitionId) {
        this.requisitionId = requisitionId;
    }

    public LongFilter getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(LongFilter quotationId) {
        this.quotationId = quotationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PurchaseOrderCriteria that = (PurchaseOrderCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(purchaseOrderNo, that.purchaseOrderNo) &&
            Objects.equals(workOrderNo, that.workOrderNo) &&
            Objects.equals(issueDate, that.issueDate) &&
            Objects.equals(referredTo, that.referredTo) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(laborOrOtherAmount, that.laborOrOtherAmount) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(status, that.status) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(commentsId, that.commentsId) &&
            Objects.equals(requisitionId, that.requisitionId) &&
            Objects.equals(quotationId, that.quotationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        purchaseOrderNo,
        workOrderNo,
        issueDate,
        referredTo,
        subject,
        laborOrOtherAmount,
        discount,
        status,
        modifiedBy,
        modifiedOn,
        commentsId,
        requisitionId,
        quotationId
        );
    }

    @Override
    public String toString() {
        return "PurchaseOrderCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (purchaseOrderNo != null ? "purchaseOrderNo=" + purchaseOrderNo + ", " : "") +
                (workOrderNo != null ? "workOrderNo=" + workOrderNo + ", " : "") +
                (issueDate != null ? "issueDate=" + issueDate + ", " : "") +
                (referredTo != null ? "referredTo=" + referredTo + ", " : "") +
                (subject != null ? "subject=" + subject + ", " : "") +
                (laborOrOtherAmount != null ? "laborOrOtherAmount=" + laborOrOtherAmount + ", " : "") +
                (discount != null ? "discount=" + discount + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (commentsId != null ? "commentsId=" + commentsId + ", " : "") +
                (requisitionId != null ? "requisitionId=" + requisitionId + ", " : "") +
                (quotationId != null ? "quotationId=" + quotationId + ", " : "") +
            "}";
    }

}
