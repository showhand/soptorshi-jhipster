package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.AccountType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the SystemAccountMap entity. This class is used in SystemAccountMapResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /system-account-maps?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SystemAccountMapCriteria implements Serializable {
    /**
     * Class for filtering AccountType
     */
    public static class AccountTypeFilter extends Filter<AccountType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private AccountTypeFilter accountType;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter accountId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public AccountTypeFilter getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountTypeFilter accountType) {
        this.accountType = accountType;
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

    public LongFilter getAccountId() {
        return accountId;
    }

    public void setAccountId(LongFilter accountId) {
        this.accountId = accountId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SystemAccountMapCriteria that = (SystemAccountMapCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(accountType, that.accountType) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        accountType,
        modifiedBy,
        modifiedOn,
        accountId
        );
    }

    @Override
    public String toString() {
        return "SystemAccountMapCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (accountType != null ? "accountType=" + accountType + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (accountId != null ? "accountId=" + accountId + ", " : "") +
            "}";
    }

}
