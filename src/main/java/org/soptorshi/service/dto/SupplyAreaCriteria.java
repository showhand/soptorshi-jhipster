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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the SupplyArea entity. This class is used in SupplyAreaResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /supply-areas?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplyAreaCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter areaName;

    private StringFilter areaCode;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private LongFilter supplyZoneId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAreaName() {
        return areaName;
    }

    public void setAreaName(StringFilter areaName) {
        this.areaName = areaName;
    }

    public StringFilter getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(StringFilter areaCode) {
        this.areaCode = areaCode;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public StringFilter getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(StringFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public InstantFilter getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(InstantFilter updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LongFilter getSupplyZoneId() {
        return supplyZoneId;
    }

    public void setSupplyZoneId(LongFilter supplyZoneId) {
        this.supplyZoneId = supplyZoneId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplyAreaCriteria that = (SupplyAreaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(areaName, that.areaName) &&
            Objects.equals(areaCode, that.areaCode) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(supplyZoneId, that.supplyZoneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        areaName,
        areaCode,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        supplyZoneId
        );
    }

    @Override
    public String toString() {
        return "SupplyAreaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (areaName != null ? "areaName=" + areaName + ", " : "") +
                (areaCode != null ? "areaCode=" + areaCode + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (supplyZoneId != null ? "supplyZoneId=" + supplyZoneId + ", " : "") +
            "}";
    }

}
