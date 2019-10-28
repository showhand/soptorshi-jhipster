package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.VoucherType;
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
 * Criteria class for the JournalVoucher entity. This class is used in JournalVoucherResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /journal-vouchers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class JournalVoucherCriteria implements Serializable {
    /**
     * Class for filtering VoucherType
     */
    public static class VoucherTypeFilter extends Filter<VoucherType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter voucherNo;

    private LocalDateFilter voucherDate;

    private LocalDateFilter postDate;

    private VoucherTypeFilter type;

    private BigDecimalFilter conversionFactor;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter currencyId;

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

    public VoucherTypeFilter getType() {
        return type;
    }

    public void setType(VoucherTypeFilter type) {
        this.type = type;
    }

    public BigDecimalFilter getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(BigDecimalFilter conversionFactor) {
        this.conversionFactor = conversionFactor;
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

    public LongFilter getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(LongFilter currencyId) {
        this.currencyId = currencyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JournalVoucherCriteria that = (JournalVoucherCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(voucherNo, that.voucherNo) &&
            Objects.equals(voucherDate, that.voucherDate) &&
            Objects.equals(postDate, that.postDate) &&
            Objects.equals(type, that.type) &&
            Objects.equals(conversionFactor, that.conversionFactor) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(currencyId, that.currencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        voucherNo,
        voucherDate,
        postDate,
        type,
        conversionFactor,
        modifiedBy,
        modifiedOn,
        currencyId
        );
    }

    @Override
    public String toString() {
        return "JournalVoucherCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (voucherNo != null ? "voucherNo=" + voucherNo + ", " : "") +
                (voucherDate != null ? "voucherDate=" + voucherDate + ", " : "") +
                (postDate != null ? "postDate=" + postDate + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (conversionFactor != null ? "conversionFactor=" + conversionFactor + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
            "}";
    }

}
