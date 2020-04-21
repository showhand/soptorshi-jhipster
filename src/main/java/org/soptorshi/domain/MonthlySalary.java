package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.soptorshi.domain.enumeration.MonthType;

import org.soptorshi.domain.enumeration.MonthlySalaryStatus;

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
    @Column(name = "jhi_year", nullable = false)
    private Integer year;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "month", nullable = false)
    private MonthType month;

    @NotNull
    @Column(name = "basic", precision = 10, scale = 2, nullable = false)
    private BigDecimal basic;

    @NotNull
    @Column(name = "gross", precision = 10, scale = 2, nullable = false)
    private BigDecimal gross;

    @Column(name = "house_rent", precision = 10, scale = 2)
    private BigDecimal houseRent;

    @Column(name = "medical_allowance", precision = 10, scale = 2)
    private BigDecimal medicalAllowance;

    @Column(name = "over_time_allowance", precision = 10, scale = 2)
    private BigDecimal overTimeAllowance;

    @Column(name = "food_allowance", precision = 10, scale = 2)
    private BigDecimal foodAllowance;

    @Column(name = "arrear_allowance", precision = 10, scale = 2)
    private BigDecimal arrearAllowance;

    @Column(name = "driver_allowance", precision = 10, scale = 2)
    private BigDecimal driverAllowance;

    @Column(name = "fuel_lub_allowance", precision = 10, scale = 2)
    private BigDecimal fuelLubAllowance;

    @Column(name = "mobile_allowance", precision = 10, scale = 2)
    private BigDecimal mobileAllowance;

    @Column(name = "travel_allowance", precision = 10, scale = 2)
    private BigDecimal travelAllowance;

    @Column(name = "other_allowance", precision = 10, scale = 2)
    private BigDecimal otherAllowance;

    @Column(name = "festival_allowance", precision = 10, scale = 2)
    private BigDecimal festivalAllowance;

    @Column(name = "absent")
    private Integer absent;

    @Column(name = "fine", precision = 10, scale = 2)
    private BigDecimal fine;

    @Column(name = "advance_ho", precision = 10, scale = 2)
    private BigDecimal advanceHO;

    @Column(name = "advance_factory", precision = 10, scale = 2)
    private BigDecimal advanceFactory;

    @Column(name = "provident_fund", precision = 10, scale = 2)
    private BigDecimal providentFund;

    @Column(name = "tax", precision = 10, scale = 2)
    private BigDecimal tax;

    @Column(name = "loan_amount", precision = 10, scale = 2)
    private BigDecimal loanAmount;

    @Column(name = "bill_payable", precision = 10, scale = 2)
    private BigDecimal billPayable;

    @Column(name = "bill_receivable", precision = 10, scale = 2)
    private BigDecimal billReceivable;

    @Column(name = "payable", precision = 10, scale = 2)
    private BigDecimal payable;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "on_hold")
    private Boolean onHold;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MonthlySalaryStatus status;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @Column(name = "voucher_generated")
    private Boolean voucherGenerated;

    @OneToMany(mappedBy = "monthlySalary")
    private Set<SalaryMessages> comments = new HashSet<>();
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

    public Integer getYear() {
        return year;
    }

    public MonthlySalary year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public BigDecimal getGross() {
        return gross;
    }

    public MonthlySalary gross(BigDecimal gross) {
        this.gross = gross;
        return this;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public BigDecimal getHouseRent() {
        return houseRent;
    }

    public MonthlySalary houseRent(BigDecimal houseRent) {
        this.houseRent = houseRent;
        return this;
    }

    public void setHouseRent(BigDecimal houseRent) {
        this.houseRent = houseRent;
    }

    public BigDecimal getMedicalAllowance() {
        return medicalAllowance;
    }

    public MonthlySalary medicalAllowance(BigDecimal medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
        return this;
    }

    public void setMedicalAllowance(BigDecimal medicalAllowance) {
        this.medicalAllowance = medicalAllowance;
    }

    public BigDecimal getOverTimeAllowance() {
        return overTimeAllowance;
    }

    public MonthlySalary overTimeAllowance(BigDecimal overTimeAllowance) {
        this.overTimeAllowance = overTimeAllowance;
        return this;
    }

    public void setOverTimeAllowance(BigDecimal overTimeAllowance) {
        this.overTimeAllowance = overTimeAllowance;
    }

    public BigDecimal getFoodAllowance() {
        return foodAllowance;
    }

    public MonthlySalary foodAllowance(BigDecimal foodAllowance) {
        this.foodAllowance = foodAllowance;
        return this;
    }

    public void setFoodAllowance(BigDecimal foodAllowance) {
        this.foodAllowance = foodAllowance;
    }

    public BigDecimal getArrearAllowance() {
        return arrearAllowance;
    }

    public MonthlySalary arrearAllowance(BigDecimal arrearAllowance) {
        this.arrearAllowance = arrearAllowance;
        return this;
    }

    public void setArrearAllowance(BigDecimal arrearAllowance) {
        this.arrearAllowance = arrearAllowance;
    }

    public BigDecimal getDriverAllowance() {
        return driverAllowance;
    }

    public MonthlySalary driverAllowance(BigDecimal driverAllowance) {
        this.driverAllowance = driverAllowance;
        return this;
    }

    public void setDriverAllowance(BigDecimal driverAllowance) {
        this.driverAllowance = driverAllowance;
    }

    public BigDecimal getFuelLubAllowance() {
        return fuelLubAllowance;
    }

    public MonthlySalary fuelLubAllowance(BigDecimal fuelLubAllowance) {
        this.fuelLubAllowance = fuelLubAllowance;
        return this;
    }

    public void setFuelLubAllowance(BigDecimal fuelLubAllowance) {
        this.fuelLubAllowance = fuelLubAllowance;
    }

    public BigDecimal getMobileAllowance() {
        return mobileAllowance;
    }

    public MonthlySalary mobileAllowance(BigDecimal mobileAllowance) {
        this.mobileAllowance = mobileAllowance;
        return this;
    }

    public void setMobileAllowance(BigDecimal mobileAllowance) {
        this.mobileAllowance = mobileAllowance;
    }

    public BigDecimal getTravelAllowance() {
        return travelAllowance;
    }

    public MonthlySalary travelAllowance(BigDecimal travelAllowance) {
        this.travelAllowance = travelAllowance;
        return this;
    }

    public void setTravelAllowance(BigDecimal travelAllowance) {
        this.travelAllowance = travelAllowance;
    }

    public BigDecimal getOtherAllowance() {
        return otherAllowance;
    }

    public MonthlySalary otherAllowance(BigDecimal otherAllowance) {
        this.otherAllowance = otherAllowance;
        return this;
    }

    public void setOtherAllowance(BigDecimal otherAllowance) {
        this.otherAllowance = otherAllowance;
    }

    public BigDecimal getFestivalAllowance() {
        return festivalAllowance;
    }

    public MonthlySalary festivalAllowance(BigDecimal festivalAllowance) {
        this.festivalAllowance = festivalAllowance;
        return this;
    }

    public void setFestivalAllowance(BigDecimal festivalAllowance) {
        this.festivalAllowance = festivalAllowance;
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

    public BigDecimal getProvidentFund() {
        return providentFund;
    }

    public MonthlySalary providentFund(BigDecimal providentFund) {
        this.providentFund = providentFund;
        return this;
    }

    public void setProvidentFund(BigDecimal providentFund) {
        this.providentFund = providentFund;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public MonthlySalary tax(BigDecimal tax) {
        this.tax = tax;
        return this;
    }

    public void setTax(BigDecimal tax) {
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

    public BigDecimal getBillPayable() {
        return billPayable;
    }

    public MonthlySalary billPayable(BigDecimal billPayable) {
        this.billPayable = billPayable;
        return this;
    }

    public void setBillPayable(BigDecimal billPayable) {
        this.billPayable = billPayable;
    }

    public BigDecimal getBillReceivable() {
        return billReceivable;
    }

    public MonthlySalary billReceivable(BigDecimal billReceivable) {
        this.billReceivable = billReceivable;
        return this;
    }

    public void setBillReceivable(BigDecimal billReceivable) {
        this.billReceivable = billReceivable;
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

    public Boolean isApproved() {
        return approved;
    }

    public MonthlySalary approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean isOnHold() {
        return onHold;
    }

    public MonthlySalary onHold(Boolean onHold) {
        this.onHold = onHold;
        return this;
    }

    public void setOnHold(Boolean onHold) {
        this.onHold = onHold;
    }

    public MonthlySalaryStatus getStatus() {
        return status;
    }

    public MonthlySalary status(MonthlySalaryStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(MonthlySalaryStatus status) {
        this.status = status;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public MonthlySalary modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public MonthlySalary modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Boolean isVoucherGenerated() {
        return voucherGenerated;
    }

    public MonthlySalary voucherGenerated(Boolean voucherGenerated) {
        this.voucherGenerated = voucherGenerated;
        return this;
    }

    public void setVoucherGenerated(Boolean voucherGenerated) {
        this.voucherGenerated = voucherGenerated;
    }

    public Set<SalaryMessages> getComments() {
        return comments;
    }

    public MonthlySalary comments(Set<SalaryMessages> salaryMessages) {
        this.comments = salaryMessages;
        return this;
    }

    public MonthlySalary addComments(SalaryMessages salaryMessages) {
        this.comments.add(salaryMessages);
        salaryMessages.setMonthlySalary(this);
        return this;
    }

    public MonthlySalary removeComments(SalaryMessages salaryMessages) {
        this.comments.remove(salaryMessages);
        salaryMessages.setMonthlySalary(null);
        return this;
    }

    public void setComments(Set<SalaryMessages> salaryMessages) {
        this.comments = salaryMessages;
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
            "}";
    }
}
