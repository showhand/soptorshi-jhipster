package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.DebtorLedgerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DebtorLedger and its DTO DebtorLedgerDTO.
 */
@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface DebtorLedgerMapper extends EntityMapper<DebtorLedgerDTO, DebtorLedger> {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.name", target = "customerName")
    DebtorLedgerDTO toDto(DebtorLedger debtorLedger);

    @Mapping(source = "customerId", target = "customer")
    DebtorLedger toEntity(DebtorLedgerDTO debtorLedgerDTO);

    default DebtorLedger fromId(Long id) {
        if (id == null) {
            return null;
        }
        DebtorLedger debtorLedger = new DebtorLedger();
        debtorLedger.setId(id);
        return debtorLedger;
    }
}
