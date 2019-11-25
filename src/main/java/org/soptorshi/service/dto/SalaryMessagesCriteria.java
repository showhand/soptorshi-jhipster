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
 * Criteria class for the SalaryMessages entity. This class is used in SalaryMessagesResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /salary-messages?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SalaryMessagesCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter commentedOn;

    private LongFilter commenterId;

    private LongFilter monthlySalaryId;

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

    public LongFilter getMonthlySalaryId() {
        return monthlySalaryId;
    }

    public void setMonthlySalaryId(LongFilter monthlySalaryId) {
        this.monthlySalaryId = monthlySalaryId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SalaryMessagesCriteria that = (SalaryMessagesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(commentedOn, that.commentedOn) &&
            Objects.equals(commenterId, that.commenterId) &&
            Objects.equals(monthlySalaryId, that.monthlySalaryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        commentedOn,
        commenterId,
        monthlySalaryId
        );
    }

    @Override
    public String toString() {
        return "SalaryMessagesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (commentedOn != null ? "commentedOn=" + commentedOn + ", " : "") +
                (commenterId != null ? "commenterId=" + commenterId + ", " : "") +
                (monthlySalaryId != null ? "monthlySalaryId=" + monthlySalaryId + ", " : "") +
            "}";
    }

}
