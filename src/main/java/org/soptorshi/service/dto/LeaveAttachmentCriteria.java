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

/**
 * Criteria class for the LeaveAttachment entity. This class is used in LeaveAttachmentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /leave-attachments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LeaveAttachmentCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter leaveApplicationId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getLeaveApplicationId() {
        return leaveApplicationId;
    }

    public void setLeaveApplicationId(LongFilter leaveApplicationId) {
        this.leaveApplicationId = leaveApplicationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LeaveAttachmentCriteria that = (LeaveAttachmentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(leaveApplicationId, that.leaveApplicationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        leaveApplicationId
        );
    }

    @Override
    public String toString() {
        return "LeaveAttachmentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (leaveApplicationId != null ? "leaveApplicationId=" + leaveApplicationId + ", " : "") +
            "}";
    }

}
