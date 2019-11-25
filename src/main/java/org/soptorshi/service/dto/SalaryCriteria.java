package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.SalaryStatus;
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
    /**
     * Class for filtering SalaryStatus
     */
    public static class SalaryStatusFilter extends Filter<SalaryStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter basic;

    private BigDecimalFilter gross;

    private LocalDateFilter startedOn;

    private LocalDateFilter endedOn;

    private SalaryStatusFilter salaryStatus;

    private StringFilter modifiedBy;

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

    public BigDecimalFilter getGross() {
        return gross;
    }

    public void setGross(BigDecimalFilter gross) {
        this.gross = gross;
    }

    public LocalDateFilter getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(LocalDateFilter startedOn) {
        this.startedOn = startedOn;
    }

    public LocalDateFilter getEndedOn() {
        return endedOn;
    }

    public void setEndedOn(LocalDateFilter endedOn) {
        this.endedOn = endedOn;
    }

    public SalaryStatusFilter getSalaryStatus() {
        return salaryStatus;
    }

    public void setSalaryStatus(SalaryStatusFilter salaryStatus) {
        this.salaryStatus = salaryStatus;
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
            Objects.equals(gross, that.gross) &&
            Objects.equals(startedOn, that.startedOn) &&
            Objects.equals(endedOn, that.endedOn) &&
            Objects.equals(salaryStatus, that.salaryStatus) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        basic,
        gross,
        startedOn,
        endedOn,
        salaryStatus,
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
                (gross != null ? "gross=" + gross + ", " : "") +
                (startedOn != null ? "startedOn=" + startedOn + ", " : "") +
                (endedOn != null ? "endedOn=" + endedOn + ", " : "") +
                (salaryStatus != null ? "salaryStatus=" + salaryStatus + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
