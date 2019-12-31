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
 * Criteria class for the RequisitionVoucherRelation entity. This class is used in RequisitionVoucherRelationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /requisition-voucher-relations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RequisitionVoucherRelationCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter voucherNo;

    private BigDecimalFilter amount;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter voucherId;

    private LongFilter requisitionId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(StringFilter voucherNo) {
        this.voucherNo = voucherNo;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
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

    public LongFilter getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(LongFilter voucherId) {
        this.voucherId = voucherId;
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
        final RequisitionVoucherRelationCriteria that = (RequisitionVoucherRelationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(voucherNo, that.voucherNo) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(voucherId, that.voucherId) &&
            Objects.equals(requisitionId, that.requisitionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        voucherNo,
        amount,
        modifiedBy,
        modifiedOn,
        voucherId,
        requisitionId
        );
    }

    @Override
    public String toString() {
        return "RequisitionVoucherRelationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (voucherNo != null ? "voucherNo=" + voucherNo + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (voucherId != null ? "voucherId=" + voucherId + ", " : "") +
                (requisitionId != null ? "requisitionId=" + requisitionId + ", " : "") +
            "}";
    }

}
