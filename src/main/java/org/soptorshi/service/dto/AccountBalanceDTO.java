package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.soptorshi.domain.enumeration.BalanceType;

/**
 * A DTO for the AccountBalance entity.
 */
public class AccountBalanceDTO implements Serializable {

    private Long id;

    private BigDecimal yearOpenBalance;

    private BalanceType yearOpenBalanceType;

    private BigDecimal totDebitTrans;

    private BigDecimal totCreditTrans;

    private LocalDate modifiedOn;

    private String modifiedBy;


    private Long financialAccountYearId;

    private String financialAccountYearDurationStr;

    private Long accountId;

    private String accountName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getYearOpenBalance() {
        return yearOpenBalance;
    }

    public void setYearOpenBalance(BigDecimal yearOpenBalance) {
        this.yearOpenBalance = yearOpenBalance;
    }

    public BalanceType getYearOpenBalanceType() {
        return yearOpenBalanceType;
    }

    public void setYearOpenBalanceType(BalanceType yearOpenBalanceType) {
        this.yearOpenBalanceType = yearOpenBalanceType;
    }

    public BigDecimal getTotDebitTrans() {
        return totDebitTrans;
    }

    public void setTotDebitTrans(BigDecimal totDebitTrans) {
        this.totDebitTrans = totDebitTrans;
    }

    public BigDecimal getTotCreditTrans() {
        return totCreditTrans;
    }

    public void setTotCreditTrans(BigDecimal totCreditTrans) {
        this.totCreditTrans = totCreditTrans;
    }

    public LocalDate getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDate modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Long getFinancialAccountYearId() {
        return financialAccountYearId;
    }

    public void setFinancialAccountYearId(Long financialAccountYearId) {
        this.financialAccountYearId = financialAccountYearId;
    }

    public String getFinancialAccountYearDurationStr() {
        return financialAccountYearDurationStr;
    }

    public void setFinancialAccountYearDurationStr(String financialAccountYearDurationStr) {
        this.financialAccountYearDurationStr = financialAccountYearDurationStr;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long mstAccountId) {
        this.accountId = mstAccountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String mstAccountName) {
        this.accountName = mstAccountName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountBalanceDTO accountBalanceDTO = (AccountBalanceDTO) o;
        if (accountBalanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountBalanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountBalanceDTO{" +
            "id=" + getId() +
            ", yearOpenBalance=" + getYearOpenBalance() +
            ", yearOpenBalanceType='" + getYearOpenBalanceType() + "'" +
            ", totDebitTrans=" + getTotDebitTrans() +
            ", totCreditTrans=" + getTotCreditTrans() +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", financialAccountYear=" + getFinancialAccountYearId() +
            ", financialAccountYear='" + getFinancialAccountYearDurationStr() + "'" +
            ", account=" + getAccountId() +
            ", account='" + getAccountName() + "'" +
            "}";
    }
}
