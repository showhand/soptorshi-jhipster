package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.DepreciationCalculationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DepreciationCalculation and its DTO DepreciationCalculationDTO.
 */
@Mapper(componentModel = "spring", uses = {FinancialAccountYearMapper.class})
public interface DepreciationCalculationMapper extends EntityMapper<DepreciationCalculationDTO, DepreciationCalculation> {

    @Mapping(source = "financialAccountYear.id", target = "financialAccountYearId")
    DepreciationCalculationDTO toDto(DepreciationCalculation depreciationCalculation);

    @Mapping(source = "financialAccountYearId", target = "financialAccountYear")
    DepreciationCalculation toEntity(DepreciationCalculationDTO depreciationCalculationDTO);

    default DepreciationCalculation fromId(Long id) {
        if (id == null) {
            return null;
        }
        DepreciationCalculation depreciationCalculation = new DepreciationCalculation();
        depreciationCalculation.setId(id);
        return depreciationCalculation;
    }
}
