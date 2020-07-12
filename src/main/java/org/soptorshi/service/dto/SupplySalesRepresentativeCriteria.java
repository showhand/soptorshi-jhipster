package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.soptorshi.domain.enumeration.SupplySalesRepresentativeStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the SupplySalesRepresentative entity. This class is used in SupplySalesRepresentativeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /supply-sales-representatives?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplySalesRepresentativeCriteria implements Serializable {
    /**
     * Class for filtering SupplySalesRepresentativeStatus
     */
    public static class SupplySalesRepresentativeStatusFilter extends Filter<SupplySalesRepresentativeStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter contact;

    private StringFilter email;

    private StringFilter additionalInformation;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private SupplySalesRepresentativeStatusFilter status;

    private LongFilter supplyZoneId;

    private LongFilter supplyAreaId;

    private LongFilter supplyZoneManagerId;

    private LongFilter supplyAreaManagerId;

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

    public StringFilter getContact() {
        return contact;
    }

    public void setContact(StringFilter contact) {
        this.contact = contact;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(StringFilter additionalInformation) {
        this.additionalInformation = additionalInformation;
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

    public SupplySalesRepresentativeStatusFilter getStatus() {
        return status;
    }

    public void setStatus(SupplySalesRepresentativeStatusFilter status) {
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

    public LongFilter getSupplyZoneManagerId() {
        return supplyZoneManagerId;
    }

    public void setSupplyZoneManagerId(LongFilter supplyZoneManagerId) {
        this.supplyZoneManagerId = supplyZoneManagerId;
    }

    public LongFilter getSupplyAreaManagerId() {
        return supplyAreaManagerId;
    }

    public void setSupplyAreaManagerId(LongFilter supplyAreaManagerId) {
        this.supplyAreaManagerId = supplyAreaManagerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplySalesRepresentativeCriteria that = (SupplySalesRepresentativeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(email, that.email) &&
            Objects.equals(additionalInformation, that.additionalInformation) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(status, that.status) &&
            Objects.equals(supplyZoneId, that.supplyZoneId) &&
            Objects.equals(supplyAreaId, that.supplyAreaId) &&
            Objects.equals(supplyZoneManagerId, that.supplyZoneManagerId) &&
            Objects.equals(supplyAreaManagerId, that.supplyAreaManagerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        contact,
        email,
        additionalInformation,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        status,
        supplyZoneId,
        supplyAreaId,
        supplyZoneManagerId,
        supplyAreaManagerId
        );
    }

    @Override
    public String toString() {
        return "SupplySalesRepresentativeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (contact != null ? "contact=" + contact + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (additionalInformation != null ? "additionalInformation=" + additionalInformation + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (supplyZoneId != null ? "supplyZoneId=" + supplyZoneId + ", " : "") +
                (supplyAreaId != null ? "supplyAreaId=" + supplyAreaId + ", " : "") +
                (supplyZoneManagerId != null ? "supplyZoneManagerId=" + supplyZoneManagerId + ", " : "") +
                (supplyAreaManagerId != null ? "supplyAreaManagerId=" + supplyAreaManagerId + ", " : "") +
            "}";
    }

}
