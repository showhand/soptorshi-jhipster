package org.soptorshi.service.dto;
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
    private MonthType month;

    @NotNull
    private BigDecimal basic;

    private Double houseRent;

    private Double medicalAllowance;

    private Double otherAllowance;

    private Integer absent;

    private BigDecimal fine;

    private BigDecimal advanceHO;

    private BigDecimal advanceFactory;

    private Double providendFund;

    private Double tax;

    private BigDecimal loanAmount;

    private BigDecimal payable;


    private Long employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getHouseRent() {
        return houseRent;
    }

    public void setHouseRent(Double houseRent) {
        this.houseRent = houseRent;
    }

    public Double getMedicalAllowance() {
        return medicalAllowance;
    }

    public void setMedicalAllowance(Double medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public Double getOtherAllowance() {
        return otherAllowance;
    }

    public void setOtherAllowance(Double otherAllowance) {
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

    public Double getProvidendFund() {
        return providendFund;
    }

    public void setProvidendFund(Double providendFund) {
        this.providendFund = providendFund;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
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
            ", month='" + getMonth() + "'" +
            ", basic=" + getBasic() +
            ", houseRent=" + getHouseRent() +
            ", medicalAllowance=" + getMedicalAllowance() +
            ", otherAllowance=" + getOtherAllowance() +
            ", absent=" + getAbsent() +
            ", fine=" + getFine() +
            ", advanceHO=" + getAdvanceHO() +
            ", advanceFactory=" + getAdvanceFactory() +
            ", providendFund=" + getProvidendFund() +
            ", tax=" + getTax() +
            ", loanAmount=" + getLoanAmount() +
            ", payable=" + getPayable() +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
