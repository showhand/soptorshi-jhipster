package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.TaxDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Tax and its DTO TaxDTO.
 */
@Mapper(componentModel = "spring", uses = {FinancialAccountYearMapper.class})
public interface TaxMapper extends EntityMapper<TaxDTO, Tax> {

    @Mapping(source = "financialAccountYear.id", target = "financialAccountYearId")
    TaxDTO toDto(Tax tax);

    @Mapping(source = "financialAccountYearId", target = "financialAccountYear")
    Tax toEntity(TaxDTO taxDTO);

    default Tax fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tax tax = new Tax();
        tax.setId(id);
        return tax;
    }
}
