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
 * Criteria class for the ReceiptVoucher entity. This class is used in ReceiptVoucherResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /receipt-vouchers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ReceiptVoucherCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter voucherNo;

    private LocalDateFilter voucherDate;

    private LocalDateFilter postDate;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

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

    public LocalDateFilter getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(LocalDateFilter voucherDate) {
        this.voucherDate = voucherDate;
    }

    public LocalDateFilter getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateFilter postDate) {
        this.postDate = postDate;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ReceiptVoucherCriteria that = (ReceiptVoucherCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(voucherNo, that.voucherNo) &&
            Objects.equals(voucherDate, that.voucherDate) &&
            Objects.equals(postDate, that.postDate) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        voucherNo,
        voucherDate,
        postDate,
        modifiedBy,
        modifiedOn
        );
    }

    @Override
    public String toString() {
        return "ReceiptVoucherCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (voucherNo != null ? "voucherNo=" + voucherNo + ", " : "") +
                (voucherDate != null ? "voucherDate=" + voucherDate + ", " : "") +
                (postDate != null ? "postDate=" + postDate + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
            "}";
    }

}
