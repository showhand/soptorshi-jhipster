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
 * Criteria class for the PurchaseOrderMessages entity. This class is used in PurchaseOrderMessagesResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /purchase-order-messages?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PurchaseOrderMessagesCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter commentedOn;

    private LongFilter commenterId;

    private LongFilter purchaseOrderId;

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

    public LongFilter getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(LongFilter purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PurchaseOrderMessagesCriteria that = (PurchaseOrderMessagesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(commentedOn, that.commentedOn) &&
            Objects.equals(commenterId, that.commenterId) &&
            Objects.equals(purchaseOrderId, that.purchaseOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        commentedOn,
        commenterId,
        purchaseOrderId
        );
    }

    @Override
    public String toString() {
        return "PurchaseOrderMessagesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (commentedOn != null ? "commentedOn=" + commentedOn + ", " : "") +
                (commenterId != null ? "commenterId=" + commenterId + ", " : "") +
                (purchaseOrderId != null ? "purchaseOrderId=" + purchaseOrderId + ", " : "") +
            "}";
    }

}
