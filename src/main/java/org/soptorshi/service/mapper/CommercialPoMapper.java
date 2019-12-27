package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.CommercialPo;
import org.soptorshi.service.dto.CommercialPoDTO;

/**
 * Mapper for the entity CommercialPo and its DTO CommercialPoDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialPiMapper.class})
public interface CommercialPoMapper extends EntityMapper<CommercialPoDTO, CommercialPo> {

    @Mapping(source = "commercialPi.id", target = "commercialPiId")
    @Mapping(source = "commercialPi.proformaNo", target = "commercialPiProformaNo")
    CommercialPoDTO toDto(CommercialPo commercialPo);

    @Mapping(source = "commercialPiId", target = "commercialPi")
    CommercialPo toEntity(CommercialPoDTO commercialPoDTO);

    default CommercialPo fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialPo commercialPo = new CommercialPo();
        commercialPo.setId(id);
        return commercialPo;
    }
}
