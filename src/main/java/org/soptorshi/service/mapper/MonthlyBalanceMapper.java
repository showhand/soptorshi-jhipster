package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.MonthlyBalanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MonthlyBalance and its DTO MonthlyBalanceDTO.
 */
@Mapper(componentModel = "spring", uses = {AccountBalanceMapper.class})
public interface MonthlyBalanceMapper extends EntityMapper<MonthlyBalanceDTO, MonthlyBalance> {

    @Mapping(source = "accountBalance.id", target = "accountBalanceId")
    MonthlyBalanceDTO toDto(MonthlyBalance monthlyBalance);

    @Mapping(source = "accountBalanceId", target = "accountBalance")
    MonthlyBalance toEntity(MonthlyBalanceDTO monthlyBalanceDTO);

    default MonthlyBalance fromId(Long id) {
        if (id == null) {
            return null;
        }
        MonthlyBalance monthlyBalance = new MonthlyBalance();
        monthlyBalance.setId(id);
        return monthlyBalance;
    }
}
