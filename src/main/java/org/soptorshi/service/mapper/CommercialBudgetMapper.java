package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.soptorshi.domain.CommercialBudget;
import org.soptorshi.service.dto.CommercialBudgetDTO;

/**
 * Mapper for the entity CommercialBudget and its DTO CommercialBudgetDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommercialBudgetMapper extends EntityMapper<CommercialBudgetDTO, CommercialBudget> {



    default CommercialBudget fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialBudget commercialBudget = new CommercialBudget();
        commercialBudget.setId(id);
        return commercialBudget;
    }
}
