package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.FinancialAccountYearDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FinancialAccountYear and its DTO FinancialAccountYearDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FinancialAccountYearMapper extends EntityMapper<FinancialAccountYearDTO, FinancialAccountYear> {



    default FinancialAccountYear fromId(Long id) {
        if (id == null) {
            return null;
        }
        FinancialAccountYear financialAccountYear = new FinancialAccountYear();
        financialAccountYear.setId(id);
        return financialAccountYear;
    }
}
