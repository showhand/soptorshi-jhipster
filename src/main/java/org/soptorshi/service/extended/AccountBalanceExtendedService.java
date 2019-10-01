package org.soptorshi.service.extended;

import org.soptorshi.domain.MstAccount;
import org.soptorshi.repository.AccountBalanceRepository;
import org.soptorshi.repository.extended.AccountBalanceExtendedRepository;
import org.soptorshi.repository.search.AccountBalanceSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.AccountBalanceService;
import org.soptorshi.service.dto.AccountBalanceDTO;
import org.soptorshi.service.mapper.AccountBalanceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class AccountBalanceExtendedService extends AccountBalanceService {

    private AccountBalanceExtendedRepository accountBalanceExtendedRepository;

    public AccountBalanceExtendedService(AccountBalanceRepository accountBalanceRepository,
                                         AccountBalanceMapper accountBalanceMapper,
                                         AccountBalanceSearchRepository accountBalanceSearchRepository,
                                         AccountBalanceExtendedRepository accountBalanceExtendedRepository) {
        super(accountBalanceRepository, accountBalanceMapper, accountBalanceSearchRepository);
        this.accountBalanceExtendedRepository = accountBalanceExtendedRepository;
    }

    @Override
    public AccountBalanceDTO save(AccountBalanceDTO accountBalanceDTO) {
        accountBalanceDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        accountBalanceDTO.setModifiedOn(LocalDate.now());
        return super.save(accountBalanceDTO);
    }

    public void createAccountBalanceForNewAccount(MstAccount mstAccount){

    }


}
