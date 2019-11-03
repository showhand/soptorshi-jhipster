package org.soptorshi.repository.extended;

import io.undertow.security.idm.Account;
import org.soptorshi.domain.AccountBalance;
import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.domain.enumeration.FinancialYearStatus;
import org.soptorshi.repository.AccountBalanceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBalanceExtendedRepository extends AccountBalanceRepository {
    Boolean existsAccountBalanceByAccountAndFinancialAccountYear(Account account, FinancialAccountYear financialAccountYear);

    AccountBalance findByFinancialAccountYear_StatusAndAccount_Id(FinancialYearStatus financialYearStatus, Long accountId);
}
