package org.soptorshi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.domain.enumeration.MonthlySalaryStatus;

/**
 * A DTO for the MonthlySalary entity.
 */
public class MonthlySalaryDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer year;

    @NotNull
    private MonthType month;

    @NotNull
    private BigDecimal basic;

    @NotNull
    private BigDecimal gross;

    private BigDecimal houseRent;

    private BigDecimal medicalAllowance;

    private BigDecimal overTimeAllowance;

    private BigDecimal foodAllowance;

    private BigDecimal arrearAllowance;

    private BigDecimal driverAllowance;

    private BigDecimal fuelLubAllowance;

    private BigDecimal mobileAllowance;

    private BigDecimal travelAllowance;

    private BigDecimal otherAllowance;

    private BigDecimal festivalAllowance;

    private Integer absent;

    private BigDecimal fine;

    private BigDecimal advanceHO;

    private BigDecimal advanceFactory;

    private BigDecimal providentFund;

    private BigDecimal tax;

    private BigDecimal loanAmount;

    private BigDecimal billPayable;

    private BigDecimal billReceivable;

    private BigDecimal payable;

    private Boolean approved;

    private Boolean onHold;

    private MonthlySalaryStatus status;

    private String modifiedBy;

    private LocalDate modifiedOn;

    private Boolean voucherGenerated;


    private Long employeeId;

    private String employeeFullName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public MonthType getMonth() {
        return month;
    }

    public void setMonth(MonthType month) {
        this.month = month;
    }

    public BigDecimal getBasic() {
        return basic;
    }

    public void setBasic(BigDecimal basic) {
        this.basic = basic;
    }

    public BigDecimal getGross() {
        return gross;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public BigDecimal getHouseRent() {
        return houseRent;
    }

    public void setHouseRent(BigDecimal houseRent) {
        this.houseRent = houseRent;
    }

    public BigDecimal getMedicalAllowance() {
        return medicalAllowance;
    }

    public void setMedicalAllowance(BigDecimal medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public BigDecimal getOverTimeAllowance() {
        return overTimeAllowance;
    }

    public void setOverTimeAllowance(BigDecimal overTimeAllowance) {
        this.overTimeAllowance = overTimeAllowance;
    }

    public BigDecimal getFoodAllowance() {
        return foodAllowance;
    }

    public void setFoodAllowance(BigDecimal foodAllowance) {
        this.foodAllowance = foodAllowance;
    }

    public BigDecimal getArrearAllowance() {
        return arrearAllowance;
    }

    public void setArrearAllowance(BigDecimal arrearAllowance) {
        this.arrearAllowance = arrearAllowance;
    }

    public BigDecimal getDriverAllowance() {
        return driverAllowance;
    }

    public void setDriverAllowance(BigDecimal driverAllowance) {
        this.driverAllowance = driverAllowance;
    }

    public BigDecimal getFuelLubAllowance() {
        return fuelLubAllowance;
    }

    public void setFuelLubAllowance(BigDecimal fuelLubAllowance) {
        this.fuelLubAllowance = fuelLubAllowance;
    }

    public BigDecimal getMobileAllowance() {
        return mobileAllowance;
    }

    public void setMobileAllowance(BigDecimal mobileAllowance) {
        this.mobileAllowance = mobileAllowance;
    }

    public BigDecimal getTravelAllowance() {
        return travelAllowance;
    }

    public void setTravelAllowance(BigDecimal travelAllowance) {
        this.travelAllowance = travelAllowance;
    }

    public BigDecimal getOtherAllowance() {
        return otherAllowance;
    }

    public void setOtherAllowance(BigDecimal otherAllowance) {
        this.otherAllowance = otherAllowance;
    }

    public BigDecimal getFestivalAllowance() {
        return festivalAllowance;
    }

    public void setFestivalAllowance(BigDecimal festivalAllowance) {
        this.festivalAllowance = festivalAllowance;
    }

    public Integer getAbsent() {
        return absent;
    }

    public void setAbsent(Integer absent) {
        this.absent = absent;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }

    public BigDecimal getAdvanceHO() {
        return advanceHO;
    }

    public void setAdvanceHO(BigDecimal advanceHO) {
        this.advanceHO = advanceHO;
    }

    public BigDecimal getAdvanceFactory() {
        return advanceFactory;
    }

    public void setAdvanceFactory(BigDecimal advanceFactory) {
        this.advanceFactory = advanceFactory;
    }

    public BigDecimal getProvidentFund() {
        return providentFund;
    }

    public void setProvidentFund(BigDecimal providentFund) {
        this.providentFund = providentFund;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getBillPayable() {
        return billPayable;
    }

    public void setBillPayable(BigDecimal billPayable) {
        this.billPayable = billPayable;
    }

    public BigDecimal getBillReceivable() {
        return billReceivable;
    }

    public void setBillReceivable(BigDecimal billReceivable) {
        this.billReceivable = billReceivable;
    }

    public BigDecimal getPayable() {
        return payable;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isOnHold() {
        return onHold;
    }

    public void setOnHold(Boolean onHold) {
        this.onHold = onHold;
    }

    public MonthlySalaryStatus getStatus() {
        return status;
    }

    public void setStatus(MonthlySalaryStatus status) {
        this.status = status;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Boolean isVoucherGenerated() {
        return voucherGenerated;
    }

    public void setVoucherGenerated(Boolean voucherGenerated) {
        this.voucherGenerated = voucherGenerated;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeFullName() {
        return employeeFullName;
    }

    public void setEmployeeFullName(String employeeFullName) {
        this.employeeFullName = employeeFullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MonthlySalaryDTO monthlySalaryDTO = (MonthlySalaryDTO) o;
        if (monthlySalaryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monthlySalaryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MonthlySalaryDTO{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", month='" + getMonth() + "'" +
            ", basic=" + getBasic() +
            ", gross=" + getGross() +
            ", houseRent=" + getHouseRent() +
            ", medicalAllowance=" + getMedicalAllowance() +
            ", overTimeAllowance=" + getOverTimeAllowance() +
            ", foodAllowance=" + getFoodAllowance() +
            ", arrearAllowance=" + getArrearAllowance() +
            ", driverAllowance=" + getDriverAllowance() +
            ", fuelLubAllowance=" + getFuelLubAllowance() +
            ", mobileAllowance=" + getMobileAllowance() +
            ", travelAllowance=" + getTravelAllowance() +
            ", otherAllowance=" + getOtherAllowance() +
            ", festivalAllowance=" + getFestivalAllowance() +
            ", absent=" + getAbsent() +
            ", fine=" + getFine() +
            ", advanceHO=" + getAdvanceHO() +
            ", advanceFactory=" + getAdvanceFactory() +
            ", providentFund=" + getProvidentFund() +
            ", tax=" + getTax() +
            ", loanAmount=" + getLoanAmount() +
            ", billPayable=" + getBillPayable() +
            ", billReceivable=" + getBillReceivable() +
            ", payable=" + getPayable() +
            ", approved='" + isApproved() + "'" +
            ", onHold='" + isOnHold() + "'" +
            ", status='" + getStatus() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", voucherGenerated='" + isVoucherGenerated() + "'" +
            ", employee=" + getEmployeeId() +
            ", employee='" + getEmployeeFullName() + "'" +
            "}";
    }
}
