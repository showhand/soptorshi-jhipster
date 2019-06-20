package org.soptorshi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.soptorshi.domain.enumeration.MonthType;

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

    private BigDecimal houseRent;

    private BigDecimal medicalAllowance;

    private BigDecimal otherAllowance;

    private Integer absent;

    private BigDecimal fine;

    private BigDecimal advanceHO;

    private BigDecimal advanceFactory;

    private BigDecimal providentFund;

    private BigDecimal tax;

    private BigDecimal loanAmount;

    private BigDecimal payable;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long employeeId;

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

    public BigDecimal getOtherAllowance() {
        return otherAllowance;
    }

    public void setOtherAllowance(BigDecimal otherAllowance) {
        this.otherAllowance = otherAllowance;
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

    public BigDecimal getPayable() {
        return payable;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
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
            ", houseRent=" + getHouseRent() +
            ", medicalAllowance=" + getMedicalAllowance() +
            ", otherAllowance=" + getOtherAllowance() +
            ", absent=" + getAbsent() +
            ", fine=" + getFine() +
            ", advanceHO=" + getAdvanceHO() +
            ", advanceFactory=" + getAdvanceFactory() +
            ", providentFund=" + getProvidentFund() +
            ", tax=" + getTax() +
            ", loanAmount=" + getLoanAmount() +
            ", payable=" + getPayable() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
