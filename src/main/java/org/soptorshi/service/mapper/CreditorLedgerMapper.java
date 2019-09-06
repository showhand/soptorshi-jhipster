package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.CreditorLedgerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CreditorLedger and its DTO CreditorLedgerDTO.
 */
@Mapper(componentModel = "spring", uses = {VendorMapper.class})
public interface CreditorLedgerMapper extends EntityMapper<CreditorLedgerDTO, CreditorLedger> {

    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.companyName", target = "vendorCompanyName")
    CreditorLedgerDTO toDto(CreditorLedger creditorLedger);

    @Mapping(source = "vendorId", target = "vendor")
    CreditorLedger toEntity(CreditorLedgerDTO creditorLedgerDTO);

    default CreditorLedger fromId(Long id) {
        if (id == null) {
            return null;
        }
        CreditorLedger creditorLedger = new CreditorLedger();
        creditorLedger.setId(id);
        return creditorLedger;
    }
}
