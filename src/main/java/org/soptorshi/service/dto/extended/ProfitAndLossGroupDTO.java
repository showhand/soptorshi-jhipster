package org.soptorshi.service.dto.extended;

import java.util.List;

public class ProfitAndLossGroupDTO {
    private String groupName;
    private List<String> accounts;
    private String totalAmount;

    public ProfitAndLossGroupDTO() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<String> accounts) {
        this.accounts = accounts;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
