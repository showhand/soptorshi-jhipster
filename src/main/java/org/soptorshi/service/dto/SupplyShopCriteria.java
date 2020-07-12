package org.soptorshi.service.dto;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the SupplyShop entity. This class is used in SupplyShopResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /supply-shops?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplyShopCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter contact;

    private StringFilter email;

    private StringFilter address;

    private StringFilter additionalInformation;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter updatedBy;

    private InstantFilter updatedOn;

    private LongFilter supplyZoneId;

    private LongFilter supplyAreaId;

    private LongFilter supplyZoneManagerId;

    private LongFilter supplyAreaManagerId;

    private LongFilter supplySalesRepresentativeId;

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

    public StringFilter getAddress() {
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
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

    public LongFilter getSupplySalesRepresentativeId() {
        return supplySalesRepresentativeId;
    }

    public void setSupplySalesRepresentativeId(LongFilter supplySalesRepresentativeId) {
        this.supplySalesRepresentativeId = supplySalesRepresentativeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplyShopCriteria that = (SupplyShopCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(email, that.email) &&
            Objects.equals(address, that.address) &&
            Objects.equals(additionalInformation, that.additionalInformation) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updatedOn, that.updatedOn) &&
            Objects.equals(supplyZoneId, that.supplyZoneId) &&
            Objects.equals(supplyAreaId, that.supplyAreaId) &&
            Objects.equals(supplyZoneManagerId, that.supplyZoneManagerId) &&
            Objects.equals(supplyAreaManagerId, that.supplyAreaManagerId) &&
            Objects.equals(supplySalesRepresentativeId, that.supplySalesRepresentativeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        contact,
        email,
        address,
        additionalInformation,
        createdBy,
        createdOn,
        updatedBy,
        updatedOn,
        supplyZoneId,
        supplyAreaId,
        supplyZoneManagerId,
        supplyAreaManagerId,
        supplySalesRepresentativeId
        );
    }

    @Override
    public String toString() {
        return "SupplyShopCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (contact != null ? "contact=" + contact + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (address != null ? "address=" + address + ", " : "") +
                (additionalInformation != null ? "additionalInformation=" + additionalInformation + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
                (updatedOn != null ? "updatedOn=" + updatedOn + ", " : "") +
                (supplyZoneId != null ? "supplyZoneId=" + supplyZoneId + ", " : "") +
                (supplyAreaId != null ? "supplyAreaId=" + supplyAreaId + ", " : "") +
                (supplyZoneManagerId != null ? "supplyZoneManagerId=" + supplyZoneManagerId + ", " : "") +
                (supplyAreaManagerId != null ? "supplyAreaManagerId=" + supplyAreaManagerId + ", " : "") +
                (supplySalesRepresentativeId != null ? "supplySalesRepresentativeId=" + supplySalesRepresentativeId + ", " : "") +
            "}";
    }

}
