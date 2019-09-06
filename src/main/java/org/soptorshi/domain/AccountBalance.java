package org.soptorshi.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import org.soptorshi.domain.enumeration.BalanceType;

/**
 * A AccountBalance.
 */
@Entity
@Table(name = "account_balance")
@Document(indexName = "accountbalance")
public class AccountBalance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year_open_balance", precision = 10, scale = 2)
    private BigDecimal yearOpenBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "year_open_balance_type")
    private BalanceType yearOpenBalanceType;

    @Column(name = "tot_debit_trans", precision = 10, scale = 2)
    private BigDecimal totDebitTrans;

    @Column(name = "tot_credit_trans", precision = 10, scale = 2)
    private BigDecimal totCreditTrans;

    @Column(name = "modified_on")
    private LocalDate modifiedOn;

    @Column(name = "modified_by")
    private String modifiedBy;

    @ManyToOne
    @JsonIgnoreProperties("accountBalances")
    private FinancialAccountYear financialAccountYear;

    @ManyToOne
    @JsonIgnoreProperties("accountBalances")
    private MstAccount account;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getYearOpenBalance() {
        return yearOpenBalance;
    }

    public AccountBalance yearOpenBalance(BigDecimal yearOpenBalance) {
        this.yearOpenBalance = yearOpenBalance;
        return this;
    }

    public void setYearOpenBalance(BigDecimal yearOpenBalance) {
        this.yearOpenBalance = yearOpenBalance;
    }

    public BalanceType getYearOpenBalanceType() {
        return yearOpenBalanceType;
    }

    public AccountBalance yearOpenBalanceType(BalanceType yearOpenBalanceType) {
        this.yearOpenBalanceType = yearOpenBalanceType;
        return this;
    }

    public void setYearOpenBalanceType(BalanceType yearOpenBalanceType) {
        this.yearOpenBalanceType = yearOpenBalanceType;
    }

    public BigDecimal getTotDebitTrans() {
        return totDebitTrans;
    }

    public AccountBalance totDebitTrans(BigDecimal totDebitTrans) {
        this.totDebitTrans = totDebitTrans;
        return this;
    }

    public void setTotDebitTrans(BigDecimal totDebitTrans) {
        this.totDebitTrans = totDebitTrans;
    }

    public BigDecimal getTotCreditTrans() {
        return totCreditTrans;
    }

    public AccountBalance totCreditTrans(BigDecimal totCreditTrans) {
        this.totCreditTrans = totCreditTrans;
        return this;
    }

    public void setTotCreditTrans(BigDecimal totCreditTrans) {
        this.totCreditTrans = totCreditTrans;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public AccountBalance modifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public AccountBalance modifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public FinancialAccountYear getFinancialAccountYear() {
        return financialAccountYear;
    }

    public AccountBalance financialAccountYear(FinancialAccountYear financialAccountYear) {
        this.financialAccountYear = financialAccountYear;
        return this;
    }

    public void setFinancialAccountYear(FinancialAccountYear financialAccountYear) {
        this.financialAccountYear = financialAccountYear;
    }

    public MstAccount getAccount() {
        return account;
    }

    public AccountBalance account(MstAccount mstAccount) {
        this.account = mstAccount;
        return this;
    }

    public void setAccount(MstAccount mstAccount) {
        this.account = mstAccount;
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
        AccountBalance accountBalance = (AccountBalance) o;
        if (accountBalance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountBalance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountBalance{" +
            "id=" + getId() +
            ", yearOpenBalance=" + getYearOpenBalance() +
            ", yearOpenBalanceType='" + getYearOpenBalanceType() + "'" +
            ", totDebitTrans=" + getTotDebitTrans() +
            ", totCreditTrans=" + getTotCreditTrans() +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            "}";
    }
}
