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
 * Criteria class for the ConversionFactor entity. This class is used in ConversionFactorResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /conversion-factors?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConversionFactorCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter covFactor;

    private BigDecimalFilter rcovFactor;

    private BigDecimalFilter bcovFactor;

    private BigDecimalFilter rbcovFactor;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter currencyId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getCovFactor() {
        return covFactor;
    }

    public void setCovFactor(BigDecimalFilter covFactor) {
        this.covFactor = covFactor;
    }

    public BigDecimalFilter getRcovFactor() {
        return rcovFactor;
    }

    public void setRcovFactor(BigDecimalFilter rcovFactor) {
        this.rcovFactor = rcovFactor;
    }

    public BigDecimalFilter getBcovFactor() {
        return bcovFactor;
    }

    public void setBcovFactor(BigDecimalFilter bcovFactor) {
        this.bcovFactor = bcovFactor;
    }

    public BigDecimalFilter getRbcovFactor() {
        return rbcovFactor;
    }

    public void setRbcovFactor(BigDecimalFilter rbcovFactor) {
        this.rbcovFactor = rbcovFactor;
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
        final ConversionFactorCriteria that = (ConversionFactorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(covFactor, that.covFactor) &&
            Objects.equals(rcovFactor, that.rcovFactor) &&
            Objects.equals(bcovFactor, that.bcovFactor) &&
            Objects.equals(rbcovFactor, that.rbcovFactor) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(currencyId, that.currencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        covFactor,
        rcovFactor,
        bcovFactor,
        rbcovFactor,
        modifiedBy,
        modifiedOn,
        currencyId
        );
    }

    @Override
    public String toString() {
        return "ConversionFactorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (covFactor != null ? "covFactor=" + covFactor + ", " : "") +
                (rcovFactor != null ? "rcovFactor=" + rcovFactor + ", " : "") +
                (bcovFactor != null ? "bcovFactor=" + bcovFactor + ", " : "") +
                (rbcovFactor != null ? "rbcovFactor=" + rbcovFactor + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (currencyId != null ? "currencyId=" + currencyId + ", " : "") +
            "}";
    }

}
