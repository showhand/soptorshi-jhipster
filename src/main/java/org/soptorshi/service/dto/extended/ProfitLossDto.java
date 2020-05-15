package org.soptorshi.service.dto.extended;

import java.util.List;

public class ProfitLossDto {
    private String groupName;
    private List<AccountWithMonthlyBalances> accountWithMonthlyBalances;
    private AccountWithMonthlyBalances totalBalance;

    public ProfitLossDto() {
    }

    public ProfitLossDto(String groupName, List<AccountWithMonthlyBalances> accountWithMonthlyBalances, AccountWithMonthlyBalances totalBalance) {
        this.groupName = groupName;
        this.accountWithMonthlyBalances = accountWithMonthlyBalances;
        this.totalBalance = totalBalance;
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

    public AccountWithMonthlyBalances getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(AccountWithMonthlyBalances totalBalance) {
        this.totalBalance = totalBalance;
    }
}
