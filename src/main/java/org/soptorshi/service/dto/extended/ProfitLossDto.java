package org.soptorshi.service.dto.extended;

import java.math.BigDecimal;
import java.util.List;

public class ProfitLossDto {
    private String groupName;
    private String months;
    private List<AccountWithMonthlyBalances> accountWithMonthlyBalances;
    private List<BigDecimal> totalBalances;

    public ProfitLossDto() {
    }

    public ProfitLossDto(String groupName, String months, List<AccountWithMonthlyBalances> accountWithMonthlyBalances, List<BigDecimal> totalBalances) {
        this.groupName = groupName;
        this.months = months;
        this.accountWithMonthlyBalances = accountWithMonthlyBalances;
        this.totalBalances = totalBalances;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<AccountWithMonthlyBalances> getAccountWithMonthlyBalances() {
        return accountWithMonthlyBalances;
    }

    public void setAccountWithMonthlyBalances(List<AccountWithMonthlyBalances> accountWithMonthlyBalances) {
        this.accountWithMonthlyBalances = accountWithMonthlyBalances;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public List<BigDecimal> getTotalBalances() {
        return totalBalances;
    }

    public void setTotalBalances(List<BigDecimal> totalBalances) {
        this.totalBalances = totalBalances;
    }
}
