package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.MonthType;

/**
 * A MonthlyBalance.
 */
@Entity
@Table(name = "monthly_balance")
@Document(indexName = "monthlybalance")
public class MonthlyBalance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "month_type")
    private MonthType monthType;

    @Column(name = "tot_month_db_bal", precision = 10, scale = 2)
    private BigDecimal totMonthDbBal;

    @Column(name = "tot_month_cr_bal", precision = 10, scale = 2)
    private BigDecimal totMonthCrBal;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @ManyToOne
    @JsonIgnoreProperties("monthlyBalances")
    private AccountBalance accountBalance;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonthType getMonthType() {
        return monthType;
    }

    public MonthlyBalance monthType(MonthType monthType) {
        this.monthType = monthType;
        return this;
    }

    public void setMonthType(MonthType monthType) {
        this.monthType = monthType;
    }

    public BigDecimal getTotMonthDbBal() {
        return totMonthDbBal;
    }

    public MonthlyBalance totMonthDbBal(BigDecimal totMonthDbBal) {
        this.totMonthDbBal = totMonthDbBal;
        return this;
    }

    public void setTotMonthDbBal(BigDecimal totMonthDbBal) {
        this.totMonthDbBal = totMonthDbBal;
    }

    public BigDecimal getTotMonthCrBal() {
        return totMonthCrBal;
    }

    public MonthlyBalance totMonthCrBal(BigDecimal totMonthCrBal) {
        this.totMonthCrBal = totMonthCrBal;
        return this;
    }

    public void setTotMonthCrBal(BigDecimal totMonthCrBal) {
        this.totMonthCrBal = totMonthCrBal;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public MonthlyBalance modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public MonthlyBalance modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public AccountBalance getAccountBalance() {
        return accountBalance;
    }

    public MonthlyBalance accountBalance(AccountBalance accountBalance) {
        this.accountBalance = accountBalance;
        return this;
    }

    public void setAccountBalance(AccountBalance accountBalance) {
        this.accountBalance = accountBalance;
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
        MonthlyBalance monthlyBalance = (MonthlyBalance) o;
        if (monthlyBalance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monthlyBalance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MonthlyBalance{" +
            "id=" + getId() +
            ", monthType='" + getMonthType() + "'" +
            ", totMonthDbBal=" + getTotMonthDbBal() +
            ", totMonthCrBal=" + getTotMonthCrBal() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            "}";
    }
}
