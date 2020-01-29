package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.ApplicationType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the PaymentVoucher entity. This class is used in PaymentVoucherResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /payment-vouchers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PaymentVoucherCriteria implements Serializable {
    /**
     * Class for filtering ApplicationType
     */
    public static class ApplicationTypeFilter extends Filter<ApplicationType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter voucherNo;

    private LocalDateFilter voucherDate;

    private LocalDateFilter postDate;

    private ApplicationTypeFilter applicationType;

    private LongFilter applicationId;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter accountId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(StringFilter voucherNo) {
        this.voucherNo = voucherNo;
    }

    public LocalDateFilter getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(LocalDateFilter voucherDate) {
        this.voucherDate = voucherDate;
    }

    public LocalDateFilter getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateFilter postDate) {
        this.postDate = postDate;
    }

    public ApplicationTypeFilter getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(ApplicationTypeFilter applicationType) {
        this.applicationType = applicationType;
    }

    public LongFilter getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(LongFilter applicationId) {
        this.applicationId = applicationId;
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
        final PaymentVoucherCriteria that = (PaymentVoucherCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(voucherNo, that.voucherNo) &&
            Objects.equals(voucherDate, that.voucherDate) &&
            Objects.equals(postDate, that.postDate) &&
            Objects.equals(applicationType, that.applicationType) &&
            Objects.equals(applicationId, that.applicationId) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(accountId, that.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        voucherNo,
        voucherDate,
        postDate,
        applicationType,
        applicationId,
        modifiedBy,
        modifiedOn,
        accountId
        );
    }

    @Override
    public String toString() {
        return "PaymentVoucherCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (voucherNo != null ? "voucherNo=" + voucherNo + ", " : "") +
                (voucherDate != null ? "voucherDate=" + voucherDate + ", " : "") +
                (postDate != null ? "postDate=" + postDate + ", " : "") +
                (applicationType != null ? "applicationType=" + applicationType + ", " : "") +
                (applicationId != null ? "applicationId=" + applicationId + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (accountId != null ? "accountId=" + accountId + ", " : "") +
            "}";
    }

}
