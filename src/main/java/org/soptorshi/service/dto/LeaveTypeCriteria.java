package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.PaidOrUnPaid;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

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
            Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        paidLeave,
        maximumNumberOfDays,
        description
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
            "}";
    }

}
