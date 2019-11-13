package org.soptorshi.service.dto;

public class ChartsOfAccountsDTO  {
    private String groupName;
    private String accountName;

    public ChartsOfAccountsDTO() {
    }

    public ChartsOfAccountsDTO(String groupName, String accountName) {
        this.groupName = groupName;
        this.accountName = accountName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
