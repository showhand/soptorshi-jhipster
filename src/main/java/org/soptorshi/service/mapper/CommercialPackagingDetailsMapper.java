package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.CommercialPackagingDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CommercialPackagingDetails and its DTO CommercialPackagingDetailsDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialPackagingMapper.class})
public interface CommercialPackagingDetailsMapper extends EntityMapper<CommercialPackagingDetailsDTO, CommercialPackagingDetails> {

    @Mapping(source = "commercialPackaging.id", target = "commercialPackagingId")
    @Mapping(source = "commercialPackaging.consignmentNo", target = "commercialPackagingConsignmentNo")
    CommercialPackagingDetailsDTO toDto(CommercialPackagingDetails commercialPackagingDetails);

    @Mapping(source = "commercialPackagingId", target = "commercialPackaging")
    CommercialPackagingDetails toEntity(CommercialPackagingDetailsDTO commercialPackagingDetailsDTO);

    default CommercialPackagingDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialPackagingDetails commercialPackagingDetails = new CommercialPackagingDetails();
        commercialPackagingDetails.setId(id);
        return commercialPackagingDetails;
    }
}
