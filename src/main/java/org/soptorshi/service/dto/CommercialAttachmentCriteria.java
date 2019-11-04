package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the CommercialAttachment entity. This class is used in CommercialAttachmentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /commercial-attachments?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommercialAttachmentCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter commercialPurchaseOrderId;

    private LongFilter commercialPoStatusId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCommercialPurchaseOrderId() {
        return commercialPurchaseOrderId;
    }

    public void setCommercialPurchaseOrderId(LongFilter commercialPurchaseOrderId) {
        this.commercialPurchaseOrderId = commercialPurchaseOrderId;
    }

    public LongFilter getCommercialPoStatusId() {
        return commercialPoStatusId;
    }

    public void setCommercialPoStatusId(LongFilter commercialPoStatusId) {
        this.commercialPoStatusId = commercialPoStatusId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommercialAttachmentCriteria that = (CommercialAttachmentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(commercialPurchaseOrderId, that.commercialPurchaseOrderId) &&
            Objects.equals(commercialPoStatusId, that.commercialPoStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        commercialPurchaseOrderId,
        commercialPoStatusId
        );
    }

    @Override
    public String toString() {
        return "CommercialAttachmentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (commercialPurchaseOrderId != null ? "commercialPurchaseOrderId=" + commercialPurchaseOrderId + ", " : "") +
                (commercialPoStatusId != null ? "commercialPoStatusId=" + commercialPoStatusId + ", " : "") +
            "}";
    }

}
