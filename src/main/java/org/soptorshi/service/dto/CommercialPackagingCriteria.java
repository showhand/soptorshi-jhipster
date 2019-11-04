package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the CommercialPackaging entity. This class is used in CommercialPackagingResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /commercial-packagings?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommercialPackagingCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter consignmentNo;

    private LocalDateFilter consignmentDate;

    private StringFilter brand;

    private StringFilter createdBy;

    private LocalDateFilter createOn;

    private StringFilter updatedBy;

    private LocalDateFilter updatedOn;

    private LongFilter commercialPurchaseOrderId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getConsignmentNo() {
        return consignmentNo;
    }

    public void setConsignmentNo(StringFilter consignmentNo) {
        this.consignmentNo = consignmentNo;
    }

    public LocalDateFilter getConsignmentDate() {
        return consignmentDate;
    }

    public void setConsignmentDate(LocalDateFilter consignmentDate) {
        this.consignmentDate = consignmentDate;
    }

    public StringFilter getBrand() {
        return brand;
    }

    public void setBrand(StringFilter brand) {
        this.brand = brand;
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

    public LocalDateFilter getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateFilter updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LongFilter getCommercialPurchaseOrderId() {
        return commercialPurchaseOrderId;
    }

    public void setCommercialPurchaseOrderId(LongFilter commercialPurchaseOrderId) {
        this.commercialPurchaseOrderId = commercialPurchaseOrderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommercialPackagingCriteria that = (CommercialPackagingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(consignmentNo, that.consignmentNo) &&
            Objects.equals(consignmentDate, that.consignmentDate) &&
            Objects.equals(brand, that.brand) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createOn, that.createOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(commercialPurchaseOrderId, that.commercialPurchaseOrderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        consignmentNo,
        consignmentDate,
        brand,
        createdBy,
        createOn,
        updatedBy,
        updatedOn,
        commercialPurchaseOrderId
        );
    }

    @Override
    public String toString() {
        return "CommercialPackagingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (consignmentNo != null ? "consignmentNo=" + consignmentNo + ", " : "") +
                (consignmentDate != null ? "consignmentDate=" + consignmentDate + ", " : "") +
                (brand != null ? "brand=" + brand + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createOn != null ? "createOn=" + createOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (commercialPurchaseOrderId != null ? "commercialPurchaseOrderId=" + commercialPurchaseOrderId + ", " : "") +
            "}";
    }

}
