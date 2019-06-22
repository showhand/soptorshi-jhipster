package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.VendorRemarks;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Vendor entity. This class is used in VendorResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /vendors?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VendorCriteria implements Serializable {
    /**
     * Class for filtering VendorRemarks
     */
    public static class VendorRemarksFilter extends Filter<VendorRemarks> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter companyName;

    private VendorRemarksFilter remarks;

    private LongFilter vendorContactPersonId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCompanyName() {
        return companyName;
    }

    public void setCompanyName(StringFilter companyName) {
        this.companyName = companyName;
    }

    public VendorRemarksFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(VendorRemarksFilter remarks) {
        this.remarks = remarks;
    }

    public LongFilter getVendorContactPersonId() {
        return vendorContactPersonId;
    }

    public void setVendorContactPersonId(LongFilter vendorContactPersonId) {
        this.vendorContactPersonId = vendorContactPersonId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VendorCriteria that = (VendorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(companyName, that.companyName) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(vendorContactPersonId, that.vendorContactPersonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        companyName,
        remarks,
        vendorContactPersonId
        );
    }

    @Override
    public String toString() {
        return "VendorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (companyName != null ? "companyName=" + companyName + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (vendorContactPersonId != null ? "vendorContactPersonId=" + vendorContactPersonId + ", " : "") +
            "}";
    }

}
