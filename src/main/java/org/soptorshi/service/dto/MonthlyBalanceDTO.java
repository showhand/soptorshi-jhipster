package org.soptorshi.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import org.soptorshi.domain.enumeration.MonthType;

/**
 * A DTO for the MonthlyBalance entity.
 */
public class MonthlyBalanceDTO implements Serializable {

    private Long id;

    private MonthType monthType;

    private BigDecimal totMonthDbBal;

    private BigDecimal totMonthCrBal;

    private String modifiedBy;

    private LocalDate modifiedOn;


    private Long accountBalanceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonthType getMonthType() {
        return monthType;
    }

    public void setMonthType(MonthType monthType) {
        this.monthType = monthType;
    }

    public BigDecimal getTotMonthDbBal() {
        return totMonthDbBal;
    }

    public void setTotMonthDbBal(BigDecimal totMonthDbBal) {
        this.totMonthDbBal = totMonthDbBal;
    }

    public BigDecimal getTotMonthCrBal() {
        return totMonthCrBal;
    }

    public void setTotMonthCrBal(BigDecimal totMonthCrBal) {
        this.totMonthCrBal = totMonthCrBal;
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

    public Long getAccountBalanceId() {
        return accountBalanceId;
    }

    public void setAccountBalanceId(Long accountBalanceId) {
        this.accountBalanceId = accountBalanceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MonthlyBalanceDTO monthlyBalanceDTO = (MonthlyBalanceDTO) o;
        if (monthlyBalanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), monthlyBalanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MonthlyBalanceDTO{" +
            "id=" + getId() +
            ", monthType='" + getMonthType() + "'" +
            ", totMonthDbBal=" + getTotMonthDbBal() +
            ", totMonthCrBal=" + getTotMonthCrBal() +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", modifiedOn='" + getModifiedOn() + "'" +
            ", accountBalance=" + getAccountBalanceId() +
            "}";
    }
}
