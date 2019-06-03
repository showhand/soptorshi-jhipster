package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.PaymentStatus;

/**
 * A Loan.
 */
@Entity
@Table(name = "loan")
@Document(indexName = "loan")
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "taken_on")
    private LocalDate takenOn;

    @Column(name = "monthly_payable")
    private Double monthlyPayable;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "jhi_left", precision = 10, scale = 2)
    private BigDecimal left;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_date")
    private LocalDate modifiedDate;

    @ManyToOne
    @JsonIgnoreProperties("loans")
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Loan amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getTakenOn() {
        return takenOn;
    }

    public Loan takenOn(LocalDate takenOn) {
        this.takenOn = takenOn;
        return this;
    }

    public void setTakenOn(LocalDate takenOn) {
        this.takenOn = takenOn;
    }

    public Double getMonthlyPayable() {
        return monthlyPayable;
    }

    public Loan monthlyPayable(Double monthlyPayable) {
        this.monthlyPayable = monthlyPayable;
        return this;
    }

    public void setMonthlyPayable(Double monthlyPayable) {
        this.monthlyPayable = monthlyPayable;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Loan paymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getLeft() {
        return left;
    }

    public Loan left(BigDecimal left) {
        this.left = left;
        return this;
    }

    public void setLeft(BigDecimal left) {
        this.left = left;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Loan modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public Loan modifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Loan employee(Employee employee) {
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
        Loan loan = (Loan) o;
        if (loan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), loan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Loan{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", takenOn='" + getTakenOn() + "'" +
            ", monthlyPayable=" + getMonthlyPayable() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", left=" + getLeft() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
