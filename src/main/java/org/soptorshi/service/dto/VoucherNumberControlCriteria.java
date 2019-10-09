package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.VoucherResetBasis;
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
 * Criteria class for the VoucherNumberControl entity. This class is used in VoucherNumberControlResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /voucher-number-controls?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VoucherNumberControlCriteria implements Serializable {
    /**
     * Class for filtering VoucherResetBasis
     */
    public static class VoucherResetBasisFilter extends Filter<VoucherResetBasis> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private VoucherResetBasisFilter resetBasis;

    private IntegerFilter startVoucherNo;

    private BigDecimalFilter voucherLimit;

    private LocalDateFilter modifiedOn;

    private StringFilter modifiedBy;

    private LongFilter financialAccountYearId;

    private LongFilter voucherId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public VoucherResetBasisFilter getResetBasis() {
        return resetBasis;
    }

    public void setResetBasis(VoucherResetBasisFilter resetBasis) {
        this.resetBasis = resetBasis;
    }

    public IntegerFilter getStartVoucherNo() {
        return startVoucherNo;
    }

    public void setStartVoucherNo(IntegerFilter startVoucherNo) {
        this.startVoucherNo = startVoucherNo;
    }

    public BigDecimalFilter getVoucherLimit() {
        return voucherLimit;
    }

    public void setVoucherLimit(BigDecimalFilter voucherLimit) {
        this.voucherLimit = voucherLimit;
    }

    public LocalDateFilter getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateFilter modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public StringFilter getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(StringFilter modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LongFilter getFinancialAccountYearId() {
        return financialAccountYearId;
    }

    public void setFinancialAccountYearId(LongFilter financialAccountYearId) {
        this.financialAccountYearId = financialAccountYearId;
    }

    public LongFilter getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(LongFilter voucherId) {
        this.voucherId = voucherId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VoucherNumberControlCriteria that = (VoucherNumberControlCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(resetBasis, that.resetBasis) &&
            Objects.equals(startVoucherNo, that.startVoucherNo) &&
            Objects.equals(voucherLimit, that.voucherLimit) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(financialAccountYearId, that.financialAccountYearId) &&
            Objects.equals(voucherId, that.voucherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        resetBasis,
        startVoucherNo,
        voucherLimit,
        modifiedOn,
        modifiedBy,
        financialAccountYearId,
        voucherId
        );
    }

    @Override
    public String toString() {
        return "VoucherNumberControlCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (resetBasis != null ? "resetBasis=" + resetBasis + ", " : "") +
                (startVoucherNo != null ? "startVoucherNo=" + startVoucherNo + ", " : "") +
                (voucherLimit != null ? "voucherLimit=" + voucherLimit + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (financialAccountYearId != null ? "financialAccountYearId=" + financialAccountYearId + ", " : "") +
                (voucherId != null ? "voucherId=" + voucherId + ", " : "") +
            "}";
    }

}
