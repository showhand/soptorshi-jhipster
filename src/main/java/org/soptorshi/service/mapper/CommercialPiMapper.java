package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.CommercialPi;
import org.soptorshi.service.dto.CommercialPiDTO;

/**
 * Mapper for the entity CommercialPi and its DTO CommercialPiDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialBudgetMapper.class})
public interface CommercialPiMapper extends EntityMapper<CommercialPiDTO, CommercialPi> {

    @Mapping(source = "commercialBudget.id", target = "commercialBudgetId")
    @Mapping(source = "commercialBudget.budgetNo", target = "commercialBudgetBudgetNo")
    CommercialPiDTO toDto(CommercialPi commercialPi);

    @Mapping(source = "commercialBudgetId", target = "commercialBudget")
    CommercialPi toEntity(CommercialPiDTO commercialPiDTO);

    default CommercialPi fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialPi commercialPi = new CommercialPi();
        commercialPi.setId(id);
        return commercialPi;
    }
}
