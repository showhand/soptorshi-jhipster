package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.AccountBalanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AccountBalance and its DTO AccountBalanceDTO.
 */
@Mapper(componentModel = "spring", uses = {FinancialAccountYearMapper.class, MstAccountMapper.class})
public interface AccountBalanceMapper extends EntityMapper<AccountBalanceDTO, AccountBalance> {

    @Mapping(source = "financialAccountYear.id", target = "financialAccountYearId")
    @Mapping(source = "financialAccountYear.durationStr", target = "financialAccountYearDurationStr")
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "account.name", target = "accountName")
    AccountBalanceDTO toDto(AccountBalance accountBalance);

    @Mapping(source = "financialAccountYearId", target = "financialAccountYear")
    @Mapping(source = "accountId", target = "account")
    AccountBalance toEntity(AccountBalanceDTO accountBalanceDTO);

    default AccountBalance fromId(Long id) {
        if (id == null) {
            return null;
        }
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setId(id);
        return accountBalance;
    }
}
