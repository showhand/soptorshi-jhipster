package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import org.soptorshi.domain.enumeration.MonthType;

/**
 * A MonthlySalary.
 */
@Entity
@Table(name = "monthly_salary")
@Document(indexName = "monthlysalary")
public class MonthlySalary implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "month", nullable = false)
    private MonthType month;

    @NotNull
    @Column(name = "basic", precision = 10, scale = 2, nullable = false)
    private BigDecimal basic;

    @Column(name = "house_rent")
    private Double houseRent;

    @Column(name = "medical_allowance")
    private Double medicalAllowance;

    @Column(name = "other_allowance")
    private Double otherAllowance;

    @Column(name = "absent")
    private Integer absent;

    @Column(name = "fine", precision = 10, scale = 2)
    private BigDecimal fine;

    @Column(name = "advance_ho", precision = 10, scale = 2)
    private BigDecimal advanceHO;

    @Column(name = "advance_factory", precision = 10, scale = 2)
    private BigDecimal advanceFactory;

    @Column(name = "providend_fund")
    private Double providendFund;

    @Column(name = "tax")
    private Double tax;

    @Column(name = "loan_amount", precision = 10, scale = 2)
    private BigDecimal loanAmount;

    @Column(name = "payable", precision = 10, scale = 2)
    private BigDecimal payable;

    @ManyToOne
    @JsonIgnoreProperties("monthlySalaries")
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonthType getMonth() {
        return month;
    }

    public MonthlySalary month(MonthType month) {
        this.month = month;
        return this;
    }

    public void setMonth(MonthType month) {
        this.month = month;
    }

    public BigDecimal getBasic() {
        return basic;
    }

    public MonthlySalary basic(BigDecimal basic) {
        this.basic = basic;
        return this;
    }

    public void setBasic(BigDecimal basic) {
        this.basic = basic;
    }

    public Double getHouseRent() {
        return houseRent;
    }

    public MonthlySalary houseRent(Double houseRent) {
        this.houseRent = houseRent;
        return this;
    }

    public void setHouseRent(Double houseRent) {
        this.houseRent = houseRent;
    }

    public Double getMedicalAllowance() {
        return medicalAllowance;
    }

    public MonthlySalary medicalAllowance(Double medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
        return this;
    }

    public void setMedicalAllowance(Double medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public Double getOtherAllowance() {
        return otherAllowance;
    }

    public MonthlySalary otherAllowance(Double otherAllowance) {
        this.otherAllowance = otherAllowance;
        return this;
    }

    public void setOtherAllowance(Double otherAllowance) {
        this.otherAllowance = otherAllowance;
    }

    public Integer getAbsent() {
        return absent;
    }

    public MonthlySalary absent(Integer absent) {
        this.absent = absent;
        return this;
    }

    public void setAbsent(Integer absent) {
        this.absent = absent;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public MonthlySalary fine(BigDecimal fine) {
        this.fine = fine;
        return this;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }

    public BigDecimal getAdvanceHO() {
        return advanceHO;
    }

    public MonthlySalary advanceHO(BigDecimal advanceHO) {
        this.advanceHO = advanceHO;
        return this;
    }

    public void setAdvanceHO(BigDecimal advanceHO) {
        this.advanceHO = advanceHO;
    }

    public BigDecimal getAdvanceFactory() {
        return advanceFactory;
    }

    public MonthlySalary advanceFactory(BigDecimal advanceFactory) {
        this.advanceFactory = advanceFactory;
        return this;
    }

    public void setAdvanceFactory(BigDecimal advanceFactory) {
        this.advanceFactory = advanceFactory;
    }

    public Double getProvidendFund() {
        return providendFund;
    }

    public MonthlySalary providendFund(Double providendFund) {
        this.providendFund = providendFund;
        return this;
    }

    public void setProvidendFund(Double providendFund) {
        this.providendFund = providendFund;
    }

    public Double getTax() {
        return tax;
    }

    public MonthlySalary tax(Double tax) {
        this.tax = tax;
        return this;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public MonthlySalary loanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
        return this;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getPayable() {
        return payable;
    }

    public MonthlySalary payable(BigDecimal payable) {
        this.payable = payable;
        return this;
    }

    public void setPayable(BigDecimal payable) {
        this.payable = payable;
    }

    public Employee getEmployee() {
        return employee;
    }

    public MonthlySalary employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MonthlySalary monthlySalary = (MonthlySalary) o;
        if (monthlySalary.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monthlySalary.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MonthlySalary{" +
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
            "}";
    }
}
