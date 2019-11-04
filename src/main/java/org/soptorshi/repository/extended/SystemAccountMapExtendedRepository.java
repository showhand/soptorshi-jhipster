package org.soptorshi.repository.extended;

import org.soptorshi.domain.SystemAccountMap;
import org.soptorshi.domain.enumeration.AccountType;
import org.soptorshi.repository.SystemAccountMapRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemAccountMapExtendedRepository extends SystemAccountMapRepository {
    SystemAccountMap findByAccountType(AccountType accountType);
}
