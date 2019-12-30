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
 * Criteria class for the RequisitionMessages entity. This class is used in RequisitionMessagesResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /requisition-messages?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RequisitionMessagesCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter commentedOn;

    private LongFilter commenterId;

    private LongFilter requisitionId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getCommentedOn() {
        return commentedOn;
    }

    public void setCommentedOn(LocalDateFilter commentedOn) {
        this.commentedOn = commentedOn;
    }

    public LongFilter getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(LongFilter commenterId) {
        this.commenterId = commenterId;
    }

    public LongFilter getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(LongFilter requisitionId) {
        this.requisitionId = requisitionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RequisitionMessagesCriteria that = (RequisitionMessagesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(commentedOn, that.commentedOn) &&
            Objects.equals(commenterId, that.commenterId) &&
            Objects.equals(requisitionId, that.requisitionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        commentedOn,
        commenterId,
        requisitionId
        );
    }

    @Override
    public String toString() {
        return "RequisitionMessagesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (commentedOn != null ? "commentedOn=" + commentedOn + ", " : "") +
                (commenterId != null ? "commenterId=" + commenterId + ", " : "") +
                (requisitionId != null ? "requisitionId=" + requisitionId + ", " : "") +
            "}";
    }

}
