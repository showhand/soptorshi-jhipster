package org.soptorshi.service.dto.extended;

import java.math.BigDecimal;
import java.util.List;

public class CashFlowBalanceDTO {
    private List<BigDecimal> openingBalances;
    private List<BigDecimal> closingBalances;

    public CashFlowBalanceDTO(List<BigDecimal> openingBalances, List<BigDecimal> closingBalances) {
        this.openingBalances = openingBalances;
        this.closingBalances = closingBalances;
    }

    public List<BigDecimal> getOpeningBalances() {
        return openingBalances;
    }

    public List<BigDecimal> getClosingBalances() {
        return closingBalances;
    }
}
