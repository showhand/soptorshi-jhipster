package org.soptorshi.service.dto.extended;

import java.math.BigDecimal;
import java.util.List;

public class ProfitAndLossGroupAmountDTO {
    private BigDecimal group;
    private List<BigDecimal> accountAmounts;
    private BigDecimal totalAmount;

    public ProfitAndLossGroupAmountDTO() {
    }

    public BigDecimal getGroup() {
        return group;
    }

    public void setGroup(BigDecimal group) {
        this.group = group;
    }

    public List<BigDecimal> getAccountAmounts() {
        return accountAmounts;
    }

    public void setAccountAmounts(List<BigDecimal> accountAmounts) {
        this.accountAmounts = accountAmounts;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
