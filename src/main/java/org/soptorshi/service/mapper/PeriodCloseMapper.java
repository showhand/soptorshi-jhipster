package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.PeriodCloseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PeriodClose and its DTO PeriodCloseDTO.
 */
@Mapper(componentModel = "spring", uses = {FinancialAccountYearMapper.class})
public interface PeriodCloseMapper extends EntityMapper<PeriodCloseDTO, PeriodClose> {

    @Mapping(source = "financialAccountYear.id", target = "financialAccountYearId")
    @Mapping(source = "financialAccountYear.durationStr", target = "financialAccountYearDurationStr")
    PeriodCloseDTO toDto(PeriodClose periodClose);

    @Mapping(source = "financialAccountYearId", target = "financialAccountYear")
    PeriodClose toEntity(PeriodCloseDTO periodCloseDTO);

    default PeriodClose fromId(Long id) {
        if (id == null) {
            return null;
        }
        PeriodClose periodClose = new PeriodClose();
        periodClose.setId(id);
        return periodClose;
    }
}
