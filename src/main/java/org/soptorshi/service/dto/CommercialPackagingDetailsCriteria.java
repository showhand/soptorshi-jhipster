package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the CommercialPackagingDetails entity. This class is used in CommercialPackagingDetailsResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /commercial-packaging-details?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommercialPackagingDetailsCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter proDate;

    private LocalDateFilter expDate;

    private StringFilter shift1;

    private DoubleFilter shift1Total;

    private StringFilter shift2;

    private DoubleFilter shift2Total;

    private DoubleFilter dayTotal;

    private DoubleFilter total;

    private StringFilter createdBy;

    private LocalDateFilter createOn;

    private StringFilter updatedBy;

    private StringFilter updatedOn;

    private LongFilter commercialPackagingId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getProDate() {
        return proDate;
    }

    public void setProDate(LocalDateFilter proDate) {
        this.proDate = proDate;
    }

    public LocalDateFilter getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDateFilter expDate) {
        this.expDate = expDate;
    }

    public StringFilter getShift1() {
        return shift1;
    }

    public void setShift1(StringFilter shift1) {
        this.shift1 = shift1;
    }

    public DoubleFilter getShift1Total() {
        return shift1Total;
    }

    public void setShift1Total(DoubleFilter shift1Total) {
        this.shift1Total = shift1Total;
    }

    public StringFilter getShift2() {
        return shift2;
    }

    public void setShift2(StringFilter shift2) {
        this.shift2 = shift2;
    }

    public DoubleFilter getShift2Total() {
        return shift2Total;
    }

    public void setShift2Total(DoubleFilter shift2Total) {
        this.shift2Total = shift2Total;
    }

    public DoubleFilter getDayTotal() {
        return dayTotal;
    }

    public void setDayTotal(DoubleFilter dayTotal) {
        this.dayTotal = dayTotal;
    }

    public DoubleFilter getTotal() {
        return total;
    }

    public void setTotal(DoubleFilter total) {
        this.total = total;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateFilter getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateFilter createOn) {
        this.createOn = createOn;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public StringFilter getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(StringFilter updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LongFilter getCommercialPackagingId() {
        return commercialPackagingId;
    }

    public void setCommercialPackagingId(LongFilter commercialPackagingId) {
        this.commercialPackagingId = commercialPackagingId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommercialPackagingDetailsCriteria that = (CommercialPackagingDetailsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(proDate, that.proDate) &&
            Objects.equals(expDate, that.expDate) &&
            Objects.equals(shift1, that.shift1) &&
            Objects.equals(shift1Total, that.shift1Total) &&
            Objects.equals(shift2, that.shift2) &&
            Objects.equals(shift2Total, that.shift2Total) &&
            Objects.equals(dayTotal, that.dayTotal) &&
            Objects.equals(total, that.total) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createOn, that.createOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(commercialPackagingId, that.commercialPackagingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        proDate,
        expDate,
        shift1,
        shift1Total,
        shift2,
        shift2Total,
        dayTotal,
        total,
        createdBy,
        createOn,
        updatedBy,
        updatedOn,
        commercialPackagingId
        );
    }

    @Override
    public String toString() {
        return "CommercialPackagingDetailsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (proDate != null ? "proDate=" + proDate + ", " : "") +
                (expDate != null ? "expDate=" + expDate + ", " : "") +
                (shift1 != null ? "shift1=" + shift1 + ", " : "") +
                (shift1Total != null ? "shift1Total=" + shift1Total + ", " : "") +
                (shift2 != null ? "shift2=" + shift2 + ", " : "") +
                (shift2Total != null ? "shift2Total=" + shift2Total + ", " : "") +
                (dayTotal != null ? "dayTotal=" + dayTotal + ", " : "") +
                (total != null ? "total=" + total + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createOn != null ? "createOn=" + createOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (commercialPackagingId != null ? "commercialPackagingId=" + commercialPackagingId + ", " : "") +
            "}";
    }

}
