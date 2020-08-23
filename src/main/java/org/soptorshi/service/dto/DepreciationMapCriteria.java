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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the DepreciationMap entity. This class is used in DepreciationMapResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /depreciation-maps?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DepreciationMapCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter depreciationAccountId;

    private StringFilter depreciationAccountName;

    private LongFilter accountId;

    private StringFilter accountName;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter modifiedBy;

    private InstantFilter modifiedOn;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getDepreciationAccountId() {
        return depreciationAccountId;
    }

    public void setDepreciationAccountId(LongFilter depreciationAccountId) {
        this.depreciationAccountId = depreciationAccountId;
    }

    public StringFilter getDepreciationAccountName() {
        return depreciationAccountName;
    }

    public void setDepreciationAccountName(StringFilter depreciationAccountName) {
        this.depreciationAccountName = depreciationAccountName;
    }

    public LongFilter getAccountId() {
        return accountId;
    }

    public void setAccountId(LongFilter accountId) {
        this.accountId = accountId;
    }

    public StringFilter getAccountName() {
        return accountName;
    }

    public void setAccountName(StringFilter accountName) {
        this.accountName = accountName;
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

    public StringFilter getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(StringFilter modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public InstantFilter getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(InstantFilter modifiedOn) {
        this.modifiedOn = modifiedOn;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DepreciationMapCriteria that = (DepreciationMapCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(depreciationAccountId, that.depreciationAccountId) &&
            Objects.equals(depreciationAccountName, that.depreciationAccountName) &&
            Objects.equals(accountId, that.accountId) &&
            Objects.equals(accountName, that.accountName) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        depreciationAccountId,
        depreciationAccountName,
        accountId,
        accountName,
        createdBy,
        createdOn,
        modifiedBy,
        modifiedOn
        );
    }

    @Override
    public String toString() {
        return "DepreciationMapCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (depreciationAccountId != null ? "depreciationAccountId=" + depreciationAccountId + ", " : "") +
                (depreciationAccountName != null ? "depreciationAccountName=" + depreciationAccountName + ", " : "") +
                (accountId != null ? "accountId=" + accountId + ", " : "") +
                (accountName != null ? "accountName=" + accountName + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
            "}";
    }

}
