package org.soptorshi.service.dto.extended;

import java.math.BigDecimal;
import java.util.List;

public class AccountWithMonthlyBalances {
    String accountName;
    List<BigDecimal> monthlyBalances;

    public AccountWithMonthlyBalances() {
    }

    public AccountWithMonthlyBalances(String accountName, List<BigDecimal> monthlyBalances) {
        this.accountName = accountName;
        this.monthlyBalances = monthlyBalances;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public List<BigDecimal> getMonthlyBalances() {
        return monthlyBalances;
    }

    public void setMonthlyBalances(List<BigDecimal> monthlyBalances) {
        this.monthlyBalances = monthlyBalances;
    }
}
