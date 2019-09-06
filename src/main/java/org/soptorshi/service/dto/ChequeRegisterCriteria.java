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
 * Criteria class for the ChequeRegister entity. This class is used in ChequeRegisterResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /cheque-registers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChequeRegisterCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter chequeNo;

    private LocalDateFilter chequeDate;

    private StringFilter status;

    private LocalDateFilter realizationDate;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(StringFilter chequeNo) {
        this.chequeNo = chequeNo;
    }

    public LocalDateFilter getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(LocalDateFilter chequeDate) {
        this.chequeDate = chequeDate;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LocalDateFilter getRealizationDate() {
        return realizationDate;
    }

    public void setRealizationDate(LocalDateFilter realizationDate) {
        this.realizationDate = realizationDate;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChequeRegisterCriteria that = (ChequeRegisterCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(chequeNo, that.chequeNo) &&
            Objects.equals(chequeDate, that.chequeDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(realizationDate, that.realizationDate) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        chequeNo,
        chequeDate,
        status,
        realizationDate,
        modifiedBy,
        modifiedOn
        );
    }

    @Override
    public String toString() {
        return "ChequeRegisterCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (chequeNo != null ? "chequeNo=" + chequeNo + ", " : "") +
                (chequeDate != null ? "chequeDate=" + chequeDate + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (realizationDate != null ? "realizationDate=" + realizationDate + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
            "}";
    }

}
