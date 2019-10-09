package org.soptorshi.repository.extended;

import io.undertow.security.idm.Account;
import org.soptorshi.domain.FinancialAccountYear;
import org.soptorshi.repository.AccountBalanceRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBalanceExtendedRepository extends AccountBalanceRepository {
    Boolean existsAccountBalanceByAccountAndFinancialAccountYear(Account account, FinancialAccountYear financialAccountYear);
}
