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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Salary entity. This class is used in SalaryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /salaries?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SalaryCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter basic;

    private DoubleFilter houseRent;

    private DoubleFilter medicalAllowance;

    private DoubleFilter incrementRate;

    private DoubleFilter otherAllowance;

    private LongFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter employeeId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getBasic() {
        return basic;
    }

    public void setBasic(BigDecimalFilter basic) {
        this.basic = basic;
    }

    public DoubleFilter getHouseRent() {
        return houseRent;
    }

    public void setHouseRent(DoubleFilter houseRent) {
        this.houseRent = houseRent;
    }

    public DoubleFilter getMedicalAllowance() {
        return medicalAllowance;
    }

    public void setMedicalAllowance(DoubleFilter medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public DoubleFilter getIncrementRate() {
        return incrementRate;
    }

    public void setIncrementRate(DoubleFilter incrementRate) {
        this.incrementRate = incrementRate;
    }

    public DoubleFilter getOtherAllowance() {
        return otherAllowance;
    }

    public void setOtherAllowance(DoubleFilter otherAllowance) {
        this.otherAllowance = otherAllowance;
    }

    public LongFilter getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(LongFilter modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDateFilter getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateFilter modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SalaryCriteria that = (SalaryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(basic, that.basic) &&
            Objects.equals(houseRent, that.houseRent) &&
            Objects.equals(medicalAllowance, that.medicalAllowance) &&
            Objects.equals(incrementRate, that.incrementRate) &&
            Objects.equals(otherAllowance, that.otherAllowance) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        basic,
        houseRent,
        medicalAllowance,
        incrementRate,
        otherAllowance,
        modifiedBy,
        modifiedOn,
        employeeId
        );
    }

    @Override
    public String toString() {
        return "SalaryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (basic != null ? "basic=" + basic + ", " : "") +
                (houseRent != null ? "houseRent=" + houseRent + ", " : "") +
                (medicalAllowance != null ? "medicalAllowance=" + medicalAllowance + ", " : "") +
                (incrementRate != null ? "incrementRate=" + incrementRate + ", " : "") +
                (otherAllowance != null ? "otherAllowance=" + otherAllowance + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
