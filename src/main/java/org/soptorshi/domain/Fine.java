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
 * A Fine.
 */
@Entity
@Table(name = "fine")
@Document(indexName = "fine")
public class Fine implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @NotNull
    @Column(name = "fine_date", nullable = false)
    private LocalDate fineDate;

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

    @Lob
    @Column(name = "reason")
    private String reason;

    @ManyToOne
    @JsonIgnoreProperties("fines")
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

    public Fine amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getFineDate() {
        return fineDate;
    }

    public Fine fineDate(LocalDate fineDate) {
        this.fineDate = fineDate;
        return this;
    }

    public void setFineDate(LocalDate fineDate) {
        this.fineDate = fineDate;
    }

    public Double getMonthlyPayable() {
        return monthlyPayable;
    }

    public Fine monthlyPayable(Double monthlyPayable) {
        this.monthlyPayable = monthlyPayable;
        return this;
    }

    public void setMonthlyPayable(Double monthlyPayable) {
        this.monthlyPayable = monthlyPayable;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public Fine paymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getLeft() {
        return left;
    }

    public Fine left(BigDecimal left) {
        this.left = left;
        return this;
    }

    public void setLeft(BigDecimal left) {
        this.left = left;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public Fine modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public Fine modifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getReason() {
        return reason;
    }

    public Fine reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Fine employee(Employee employee) {
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
        Fine fine = (Fine) o;
        if (fine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Fine{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", fineDate='" + getFineDate() + "'" +
            ", monthlyPayable=" + getMonthlyPayable() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", left=" + getLeft() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
