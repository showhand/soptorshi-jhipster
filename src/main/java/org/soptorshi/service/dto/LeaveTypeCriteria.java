package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.*;
import org.soptorshi.domain.enumeration.PaidOrUnPaid;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the LeaveType entity. This class is used in LeaveTypeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /leave-types?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LeaveTypeCriteria implements Serializable {
    /**
     * Class for filtering PaidOrUnPaid
     */
    public static class PaidOrUnPaidFilter extends Filter<PaidOrUnPaid> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private PaidOrUnPaidFilter paidLeave;

    private IntegerFilter maximumNumberOfDays;

    private StringFilter description;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public PaidOrUnPaidFilter getPaidLeave() {
        return paidLeave;
    }

    public void setPaidLeave(PaidOrUnPaidFilter paidLeave) {
        this.paidLeave = paidLeave;
    }

    public IntegerFilter getMaximumNumberOfDays() {
        return maximumNumberOfDays;
    }

    public void setMaximumNumberOfDays(IntegerFilter maximumNumberOfDays) {
        this.maximumNumberOfDays = maximumNumberOfDays;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LeaveTypeCriteria that = (LeaveTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(paidLeave, that.paidLeave) &&
            Objects.equals(maximumNumberOfDays, that.maximumNumberOfDays) &&
            Objects.equals(description, that.description) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        paidLeave,
        maximumNumberOfDays,
        description,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn
        );
    }

    @Override
    public String toString() {
        return "LeaveTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (paidLeave != null ? "paidLeave=" + paidLeave + ", " : "") +
                (maximumNumberOfDays != null ? "maximumNumberOfDays=" + maximumNumberOfDays + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
            "}";
    }

}
