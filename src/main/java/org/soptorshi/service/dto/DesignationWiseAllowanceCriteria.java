package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.AllowanceType;
import org.soptorshi.domain.enumeration.AllowanceCategory;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the DesignationWiseAllowance entity. This class is used in DesignationWiseAllowanceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /designation-wise-allowances?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DesignationWiseAllowanceCriteria implements Serializable {
    /**
     * Class for filtering AllowanceType
     */
    public static class AllowanceTypeFilter extends Filter<AllowanceType> {
    }
    /**
     * Class for filtering AllowanceCategory
     */
    public static class AllowanceCategoryFilter extends Filter<AllowanceCategory> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private AllowanceTypeFilter allowanceType;

    private AllowanceCategoryFilter allowanceCategory;

    private BigDecimalFilter amount;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter designationId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public AllowanceTypeFilter getAllowanceType() {
        return allowanceType;
    }

    public void setAllowanceType(AllowanceTypeFilter allowanceType) {
        this.allowanceType = allowanceType;
    }

    public AllowanceCategoryFilter getAllowanceCategory() {
        return allowanceCategory;
    }

    public void setAllowanceCategory(AllowanceCategoryFilter allowanceCategory) {
        this.allowanceCategory = allowanceCategory;
    }

    public BigDecimalFilter getAmount() {
        return amount;
    }

    public void setAmount(BigDecimalFilter amount) {
        this.amount = amount;
    }

    public StringFilter getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(StringFilter modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateFilter getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateFilter modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public LongFilter getDesignationId() {
        return designationId;
    }

    public void setDesignationId(LongFilter designationId) {
        this.designationId = designationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DesignationWiseAllowanceCriteria that = (DesignationWiseAllowanceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(allowanceType, that.allowanceType) &&
            Objects.equals(allowanceCategory, that.allowanceCategory) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(designationId, that.designationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        allowanceType,
        allowanceCategory,
        amount,
        modifiedBy,
        modifiedOn,
        designationId
        );
    }

    @Override
    public String toString() {
        return "DesignationWiseAllowanceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (allowanceType != null ? "allowanceType=" + allowanceType + ", " : "") +
                (allowanceCategory != null ? "allowanceCategory=" + allowanceCategory + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (designationId != null ? "designationId=" + designationId + ", " : "") +
            "}";
    }

}
