package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.SelectionType;
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
 * Criteria class for the Quotation entity. This class is used in QuotationResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /quotations?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuotationCriteria implements Serializable {
    /**
     * Class for filtering SelectionType
     */
    public static class SelectionTypeFilter extends Filter<SelectionType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter quotationNo;

    private SelectionTypeFilter selectionStatus;

    private BigDecimalFilter totalAmount;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter requisitionId;

    private LongFilter vendorId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getQuotationNo() {
        return quotationNo;
    }

    public void setQuotationNo(StringFilter quotationNo) {
        this.quotationNo = quotationNo;
    }

    public SelectionTypeFilter getSelectionStatus() {
        return selectionStatus;
    }

    public void setSelectionStatus(SelectionTypeFilter selectionStatus) {
        this.selectionStatus = selectionStatus;
    }

    public BigDecimalFilter getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimalFilter totalAmount) {
        this.totalAmount = totalAmount;
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

    public LongFilter getVendorId() {
        return vendorId;
    }

    public void setVendorId(LongFilter vendorId) {
        this.vendorId = vendorId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QuotationCriteria that = (QuotationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quotationNo, that.quotationNo) &&
            Objects.equals(selectionStatus, that.selectionStatus) &&
            Objects.equals(totalAmount, that.totalAmount) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(requisitionId, that.requisitionId) &&
            Objects.equals(vendorId, that.vendorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quotationNo,
        selectionStatus,
        totalAmount,
        modifiedBy,
        modifiedOn,
        requisitionId,
        vendorId
        );
    }

    @Override
    public String toString() {
        return "QuotationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quotationNo != null ? "quotationNo=" + quotationNo + ", " : "") +
                (selectionStatus != null ? "selectionStatus=" + selectionStatus + ", " : "") +
                (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (requisitionId != null ? "requisitionId=" + requisitionId + ", " : "") +
                (vendorId != null ? "vendorId=" + vendorId + ", " : "") +
            "}";
    }

}
