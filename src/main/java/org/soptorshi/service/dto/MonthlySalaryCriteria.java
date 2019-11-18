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
import io.github.jhipster.service.filter.LocalDateFilter;

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

    private IntegerFilter year;

    private MonthTypeFilter month;

    private BigDecimalFilter basic;

    private BigDecimalFilter gross;

    private BigDecimalFilter houseRent;

    private BigDecimalFilter medicalAllowance;

    private BigDecimalFilter otherAllowance;

    private BigDecimalFilter festivalAllowance;

    private IntegerFilter absent;

    private BigDecimalFilter fine;

    private BigDecimalFilter advanceHO;

    private BigDecimalFilter advanceFactory;

    private BigDecimalFilter providentFund;

    private BigDecimalFilter tax;

    private BigDecimalFilter loanAmount;

    private BigDecimalFilter billPayable;

    private BigDecimalFilter billReceivable;

    private BigDecimalFilter payable;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private LongFilter employeeId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getYear() {
        return year;
    }

    public void setYear(IntegerFilter year) {
        this.year = year;
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

    public BigDecimalFilter getGross() {
        return gross;
    }

    public void setGross(BigDecimalFilter gross) {
        this.gross = gross;
    }

    public BigDecimalFilter getHouseRent() {
        return houseRent;
    }

    public void setHouseRent(BigDecimalFilter houseRent) {
        this.houseRent = houseRent;
    }

    public BigDecimalFilter getMedicalAllowance() {
        return medicalAllowance;
    }

    public void setMedicalAllowance(BigDecimalFilter medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public BigDecimalFilter getOtherAllowance() {
        return otherAllowance;
    }

    public void setOtherAllowance(BigDecimalFilter otherAllowance) {
        this.otherAllowance = otherAllowance;
    }

    public BigDecimalFilter getFestivalAllowance() {
        return festivalAllowance;
    }

    public void setFestivalAllowance(BigDecimalFilter festivalAllowance) {
        this.festivalAllowance = festivalAllowance;
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

    public BigDecimalFilter getProvidentFund() {
        return providentFund;
    }

    public void setProvidentFund(BigDecimalFilter providentFund) {
        this.providentFund = providentFund;
    }

    public BigDecimalFilter getTax() {
        return tax;
    }

    public void setTax(BigDecimalFilter tax) {
        this.tax = tax;
    }

    public BigDecimalFilter getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimalFilter loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimalFilter getBillPayable() {
        return billPayable;
    }

    public void setBillPayable(BigDecimalFilter billPayable) {
        this.billPayable = billPayable;
    }

    public BigDecimalFilter getBillReceivable() {
        return billReceivable;
    }

    public void setBillReceivable(BigDecimalFilter billReceivable) {
        this.billReceivable = billReceivable;
    }

    public BigDecimalFilter getPayable() {
        return payable;
    }

    public void setPayable(BigDecimalFilter payable) {
        this.payable = payable;
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
        final MonthlySalaryCriteria that = (MonthlySalaryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(year, that.year) &&
            Objects.equals(month, that.month) &&
            Objects.equals(basic, that.basic) &&
            Objects.equals(gross, that.gross) &&
            Objects.equals(houseRent, that.houseRent) &&
            Objects.equals(medicalAllowance, that.medicalAllowance) &&
            Objects.equals(otherAllowance, that.otherAllowance) &&
            Objects.equals(festivalAllowance, that.festivalAllowance) &&
            Objects.equals(absent, that.absent) &&
            Objects.equals(fine, that.fine) &&
            Objects.equals(advanceHO, that.advanceHO) &&
            Objects.equals(advanceFactory, that.advanceFactory) &&
            Objects.equals(providentFund, that.providentFund) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(loanAmount, that.loanAmount) &&
            Objects.equals(billPayable, that.billPayable) &&
            Objects.equals(billReceivable, that.billReceivable) &&
            Objects.equals(payable, that.payable) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        year,
        month,
        basic,
        gross,
        houseRent,
        medicalAllowance,
        otherAllowance,
        festivalAllowance,
        absent,
        fine,
        advanceHO,
        advanceFactory,
        providentFund,
        tax,
        loanAmount,
        billPayable,
        billReceivable,
        payable,
        modifiedBy,
        modifiedOn,
        employeeId
        );
    }

    @Override
    public String toString() {
        return "MonthlySalaryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (year != null ? "year=" + year + ", " : "") +
                (month != null ? "month=" + month + ", " : "") +
                (basic != null ? "basic=" + basic + ", " : "") +
                (gross != null ? "gross=" + gross + ", " : "") +
                (houseRent != null ? "houseRent=" + houseRent + ", " : "") +
                (medicalAllowance != null ? "medicalAllowance=" + medicalAllowance + ", " : "") +
                (otherAllowance != null ? "otherAllowance=" + otherAllowance + ", " : "") +
                (festivalAllowance != null ? "festivalAllowance=" + festivalAllowance + ", " : "") +
                (absent != null ? "absent=" + absent + ", " : "") +
                (fine != null ? "fine=" + fine + ", " : "") +
                (advanceHO != null ? "advanceHO=" + advanceHO + ", " : "") +
                (advanceFactory != null ? "advanceFactory=" + advanceFactory + ", " : "") +
                (providentFund != null ? "providentFund=" + providentFund + ", " : "") +
                (tax != null ? "tax=" + tax + ", " : "") +
                (loanAmount != null ? "loanAmount=" + loanAmount + ", " : "") +
                (billPayable != null ? "billPayable=" + billPayable + ", " : "") +
                (billReceivable != null ? "billReceivable=" + billReceivable + ", " : "") +
                (payable != null ? "payable=" + payable + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
