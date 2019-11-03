package org.soptorshi.repository.extended;

import org.soptorshi.domain.Currency;
import org.soptorshi.domain.enumeration.CurrencyFlag;
import org.soptorshi.repository.CurrencyRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyExtendedRepository extends CurrencyRepository {
    Currency findByFlag(CurrencyFlag currencyFlag);
}
