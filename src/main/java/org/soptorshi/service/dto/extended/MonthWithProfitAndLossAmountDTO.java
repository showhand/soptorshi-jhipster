package org.soptorshi.service.dto.extended;

import java.math.BigDecimal;
import java.util.List;

public class MonthWithProfitAndLossAmountDTO {
    private String month;
    private List<ProfitAndLossGroupAmountDTO> groupAmounts;
    private BigDecimal groupTypeTotal;

    public MonthWithProfitAndLossAmountDTO() {
    }

    public BigDecimal getGroupTypeTotal() {
        return groupTypeTotal;
    }

    public void setGroupTypeTotal(BigDecimal groupTypeTotal) {
        this.groupTypeTotal = groupTypeTotal;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<ProfitAndLossGroupAmountDTO> getGroupAmounts() {
        return groupAmounts;
    }

    public void setGroupAmounts(List<ProfitAndLossGroupAmountDTO> groupAmounts) {
        this.groupAmounts = groupAmounts;
    }
}
