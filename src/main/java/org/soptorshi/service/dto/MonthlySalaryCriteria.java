package org.soptorshi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.domain.enumeration.MonthlySalaryStatus;
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
    /**
     * Class for filtering MonthlySalaryStatus
     */
    public static class MonthlySalaryStatusFilter extends Filter<MonthlySalaryStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter year;

    private MonthTypeFilter month;

    private BigDecimalFilter basic;

    private BigDecimalFilter gross;

    private BigDecimalFilter houseRent;

    private BigDecimalFilter medicalAllowance;

    private BigDecimalFilter overTimeAllowance;

    private BigDecimalFilter foodAllowance;

    private BigDecimalFilter arrearAllowance;

    private BigDecimalFilter driverAllowance;

    private BigDecimalFilter fuelLubAllowance;

    private BigDecimalFilter mobileAllowance;

    private BigDecimalFilter travelAllowance;

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

    private BooleanFilter approved;

    private BooleanFilter onHold;

    private MonthlySalaryStatusFilter status;

    private StringFilter modifiedBy;

    private LocalDateFilter modifiedOn;

    private BooleanFilter voucherGenerated;

    private LongFilter commentsId;

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

    public BigDecimalFilter getOverTimeAllowance() {
        return overTimeAllowance;
    }

    public void setOverTimeAllowance(BigDecimalFilter overTimeAllowance) {
        this.overTimeAllowance = overTimeAllowance;
    }

    public BigDecimalFilter getFoodAllowance() {
        return foodAllowance;
    }

    public void setFoodAllowance(BigDecimalFilter foodAllowance) {
        this.foodAllowance = foodAllowance;
    }

    public BigDecimalFilter getArrearAllowance() {
        return arrearAllowance;
    }

    public void setArrearAllowance(BigDecimalFilter arrearAllowance) {
        this.arrearAllowance = arrearAllowance;
    }

    public BigDecimalFilter getDriverAllowance() {
        return driverAllowance;
    }

    public void setDriverAllowance(BigDecimalFilter driverAllowance) {
        this.driverAllowance = driverAllowance;
    }

    public BigDecimalFilter getFuelLubAllowance() {
        return fuelLubAllowance;
    }

    public void setFuelLubAllowance(BigDecimalFilter fuelLubAllowance) {
        this.fuelLubAllowance = fuelLubAllowance;
    }

    public BigDecimalFilter getMobileAllowance() {
        return mobileAllowance;
    }

    public void setMobileAllowance(BigDecimalFilter mobileAllowance) {
        this.mobileAllowance = mobileAllowance;
    }

    public BigDecimalFilter getTravelAllowance() {
        return travelAllowance;
    }

    public void setTravelAllowance(BigDecimalFilter travelAllowance) {
        this.travelAllowance = travelAllowance;
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

    public BooleanFilter getApproved() {
        return approved;
    }

    public void setApproved(BooleanFilter approved) {
        this.approved = approved;
    }

    public BooleanFilter getOnHold() {
        return onHold;
    }

    public void setOnHold(BooleanFilter onHold) {
        this.onHold = onHold;
    }

    public MonthlySalaryStatusFilter getStatus() {
        return status;
    }

    public void setStatus(MonthlySalaryStatusFilter status) {
        this.status = status;
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

    public BooleanFilter getVoucherGenerated() {
        return voucherGenerated;
    }

    public void setVoucherGenerated(BooleanFilter voucherGenerated) {
        this.voucherGenerated = voucherGenerated;
    }

    public LongFilter getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(LongFilter commentsId) {
        this.commentsId = commentsId;
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
            Objects.equals(overTimeAllowance, that.overTimeAllowance) &&
            Objects.equals(foodAllowance, that.foodAllowance) &&
            Objects.equals(arrearAllowance, that.arrearAllowance) &&
            Objects.equals(driverAllowance, that.driverAllowance) &&
            Objects.equals(fuelLubAllowance, that.fuelLubAllowance) &&
            Objects.equals(mobileAllowance, that.mobileAllowance) &&
            Objects.equals(travelAllowance, that.travelAllowance) &&
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
            Objects.equals(approved, that.approved) &&
            Objects.equals(onHold, that.onHold) &&
            Objects.equals(status, that.status) &&
            Objects.equals(modifiedBy, that.modifiedBy) &&
            Objects.equals(modifiedOn, that.modifiedOn) &&
            Objects.equals(voucherGenerated, that.voucherGenerated) &&
            Objects.equals(commentsId, that.commentsId) &&
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
        overTimeAllowance,
        foodAllowance,
        arrearAllowance,
        driverAllowance,
        fuelLubAllowance,
        mobileAllowance,
        travelAllowance,
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
        approved,
        onHold,
        status,
        modifiedBy,
        modifiedOn,
        voucherGenerated,
        commentsId,
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
                (overTimeAllowance != null ? "overTimeAllowance=" + overTimeAllowance + ", " : "") +
                (foodAllowance != null ? "foodAllowance=" + foodAllowance + ", " : "") +
                (arrearAllowance != null ? "arrearAllowance=" + arrearAllowance + ", " : "") +
                (driverAllowance != null ? "driverAllowance=" + driverAllowance + ", " : "") +
                (fuelLubAllowance != null ? "fuelLubAllowance=" + fuelLubAllowance + ", " : "") +
                (mobileAllowance != null ? "mobileAllowance=" + mobileAllowance + ", " : "") +
                (travelAllowance != null ? "travelAllowance=" + travelAllowance + ", " : "") +
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
                (approved != null ? "approved=" + approved + ", " : "") +
                (onHold != null ? "onHold=" + onHold + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "") +
                (modifiedOn != null ? "modifiedOn=" + modifiedOn + ", " : "") +
                (voucherGenerated != null ? "voucherGenerated=" + voucherGenerated + ", " : "") +
                (commentsId != null ? "commentsId=" + commentsId + ", " : "") +
                (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            "}";
    }

}
