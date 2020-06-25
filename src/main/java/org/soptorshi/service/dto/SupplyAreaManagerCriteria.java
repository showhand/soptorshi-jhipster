package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.SupplyAreaManagerStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the SupplyAreaManager entity. This class is used in SupplyAreaManagerResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /supply-area-managers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplyAreaManagerCriteria implements Serializable {
    /**
     * Class for filtering SupplyAreaManagerStatus
     */
    public static class SupplyAreaManagerStatusFilter extends Filter<SupplyAreaManagerStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private LocalDateFilter endDate;

    private SupplyAreaManagerStatusFilter status;

    private LongFilter supplyZoneId;

    private LongFilter supplyAreaId;

    private LongFilter employeeId;

    private LongFilter supplyZoneManagerId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public SupplyAreaManagerStatusFilter getStatus() {
        return status;
    }

    public void setStatus(SupplyAreaManagerStatusFilter status) {
        this.status = status;
    }

    public LongFilter getSupplyZoneId() {
        return supplyZoneId;
    }

    public void setSupplyZoneId(LongFilter supplyZoneId) {
        this.supplyZoneId = supplyZoneId;
    }

    public LongFilter getSupplyAreaId() {
        return supplyAreaId;
    }

    public void setSupplyAreaId(LongFilter supplyAreaId) {
        this.supplyAreaId = supplyAreaId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getSupplyZoneManagerId() {
        return supplyZoneManagerId;
    }

    public void setSupplyZoneManagerId(LongFilter supplyZoneManagerId) {
        this.supplyZoneManagerId = supplyZoneManagerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplyAreaManagerCriteria that = (SupplyAreaManagerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(supplyZoneId, that.supplyZoneId) &&
            Objects.equals(supplyAreaId, that.supplyAreaId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(supplyZoneManagerId, that.supplyZoneManagerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        endDate,
        status,
        supplyZoneId,
        supplyAreaId,
        employeeId,
        supplyZoneManagerId
        );
    }

    @Override
    public String toString() {
        return "SupplyAreaManagerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (supplyZoneId != null ? "supplyZoneId=" + supplyZoneId + ", " : "") +
                (supplyAreaId != null ? "supplyAreaId=" + supplyAreaId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
                (supplyZoneManagerId != null ? "supplyZoneManagerId=" + supplyZoneManagerId + ", " : "") +
            "}";
    }

}
