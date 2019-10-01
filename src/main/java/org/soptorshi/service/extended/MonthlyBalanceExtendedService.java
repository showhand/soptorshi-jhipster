package org.soptorshi.service.extended;

import org.soptorshi.domain.enumeration.BalanceType;
import org.soptorshi.repository.MonthlyBalanceRepository;
import org.soptorshi.repository.search.MonthlyBalanceSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.MonthlyBalanceService;
import org.soptorshi.service.dto.AccountBalanceDTO;
import org.soptorshi.service.dto.MonthlyBalanceDTO;
import org.soptorshi.service.dto.MstAccountDTO;
import org.soptorshi.service.mapper.MonthlyBalanceMapper;
import org.soptorshi.utils.SoptorshiUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
public class MonthlyBalanceExtendedService extends MonthlyBalanceService {
    public MonthlyBalanceExtendedService(MonthlyBalanceRepository monthlyBalanceRepository, MonthlyBalanceMapper monthlyBalanceMapper, MonthlyBalanceSearchRepository monthlyBalanceSearchRepository) {
        super(monthlyBalanceRepository, monthlyBalanceMapper, monthlyBalanceSearchRepository);
    }

    @Override
    public MonthlyBalanceDTO save(MonthlyBalanceDTO monthlyBalanceDTO) {
        monthlyBalanceDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        monthlyBalanceDTO.setModifiedOn(LocalDate.now());
        return super.save(monthlyBalanceDTO);
    }

    public MonthlyBalanceDTO createMonthlyBalanceForNewAccount(AccountBalanceDTO accountBalanceDTO, MstAccountDTO mstAccountDTO){
        MonthlyBalanceDTO monthlyBalanceDTO = new MonthlyBalanceDTO();
        monthlyBalanceDTO.setAccountBalanceId(accountBalanceDTO.getId());
        monthlyBalanceDTO.setMonthType(SoptorshiUtils.getMonthType());
        if(mstAccountDTO.getYearOpenBalanceType().equals(BalanceType.DEBIT)){
            monthlyBalanceDTO.setTotMonthDbBal(mstAccountDTO.getYearOpenBalance());
            monthlyBalanceDTO.setTotMonthCrBal(BigDecimal.ZERO);
        }else{
            monthlyBalanceDTO.setTotMonthCrBal(mstAccountDTO.getYearCloseBalance());
            monthlyBalanceDTO.setTotMonthDbBal(BigDecimal.ZERO);
        }
        return save(monthlyBalanceDTO);
    }
}
