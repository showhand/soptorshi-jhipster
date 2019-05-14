package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.MonthType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

/**
 * Criteria class for the MonthlySalary entity. This class is used in MonthlySalaryResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /monthly-salaries?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MonthlySalaryCriteria implements Serializable {
    /**
     * Class for filtering MonthType
     */
    public static class MonthTypeFilter extends Filter<MonthType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private MonthTypeFilter month;

    private BigDecimalFilter basic;

    private DoubleFilter houseRent;

    private DoubleFilter medicalAllowance;

    private DoubleFilter otherAllowance;

    private IntegerFilter absent;

    private BigDecimalFilter fine;

    private BigDecimalFilter advanceHO;

    private BigDecimalFilter advanceFactory;

    private DoubleFilter providendFund;

    private DoubleFilter tax;

    private BigDecimalFilter loanAmount;

    private BigDecimalFilter payable;

    private LongFilter employeeId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public MonthTypeFilter getMonth() {
        return month;
    }

    public void setMonth(MonthTypeFilter month) {
        this.month = month;
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

    public DoubleFilter getOtherAllowance() {
        return otherAllowance;
    }

    public void setOtherAllowance(DoubleFilter otherAllowance) {
        this.otherAllowance = otherAllowance;
    }

    public IntegerFilter getAbsent() {
        return absent;
    }

    public void setAbsent(IntegerFilter absent) {
        this.absent = absent;
    }

    public BigDecimalFilter getFine() {
        return fine;
    }

    public void setFine(BigDecimalFilter fine) {
        this.fine = fine;
    }

    public BigDecimalFilter getAdvanceHO() {
        return advanceHO;
    }

    public void setAdvanceHO(BigDecimalFilter advanceHO) {
        this.advanceHO = advanceHO;
    }

    public BigDecimalFilter getAdvanceFactory() {
        return advanceFactory;
    }

    public void setAdvanceFactory(BigDecimalFilter advanceFactory) {
        this.advanceFactory = advanceFactory;
    }

    public DoubleFilter getProvidendFund() {
        return providendFund;
    }

    public void setProvidendFund(DoubleFilter providendFund) {
        this.providendFund = providendFund;
    }

    public DoubleFilter getTax() {
        return tax;
    }

    public void setTax(DoubleFilter tax) {
        this.tax = tax;
    }

    public BigDecimalFilter getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimalFilter loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimalFilter getPayable() {
        return payable;
    }

    public void setPayable(BigDecimalFilter payable) {
        this.payable = payable;
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
        final MonthlySalaryCriteria that = (MonthlySalaryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(month, that.month) &&
            Objects.equals(basic, that.basic) &&
            Objects.equals(houseRent, that.houseRent) &&
            Objects.equals(medicalAllowance, that.medicalAllowance) &&
            Objects.equals(otherAllowance, that.otherAllowance) &&
            Objects.equals(absent, that.absent) &&
            Objects.equals(fine, that.fine) &&
            Objects.equals(advanceHO, that.advanceHO) &&
            Objects.equals(advanceFactory, that.advanceFactory) &&
            Objects.equals(providendFund, that.providendFund) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(loanAmount, that.loanAmount) &&
            Objects.equals(payable, that.payable) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        month,
        basic,
        houseRent,
        medicalAllowance,
        otherAllowance,
        absent,
        fine,
        advanceHO,
        advanceFactory,
        providendFund,
        tax,
        loanAmount,
        payable,
        employeeId
        );
    }

    @Override
    public String toString() {
        return "MonthlySalaryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (month != null ? "month=" + month + ", " : "") +
                (basic != null ? "basic=" + basic + ", " : "") +
                (houseRent != null ? "houseRent=" + houseRent + ", " : "") +
                (medicalAllowance != null ? "medicalAllowance=" + medicalAllowance + ", " : "") +
                (otherAllowance != null ? "otherAllowance=" + otherAllowance + ", " : "") +
                (absent != null ? "absent=" + absent + ", " : "") +
                (fine != null ? "fine=" + fine + ", " : "") +
                (advanceHO != null ? "advanceHO=" + advanceHO + ", " : "") +
                (advanceFactory != null ? "advanceFactory=" + advanceFactory + ", " : "") +
                (providendFund != null ? "providendFund=" + providendFund + ", " : "") +
                (tax != null ? "tax=" + tax + ", " : "") +
                (loanAmount != null ? "loanAmount=" + loanAmount + ", " : "") +
                (payable != null ? "payable=" + payable + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
