package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.CommercialPaymentInfo;
import org.soptorshi.service.dto.CommercialPaymentInfoDTO;

/**
 * Mapper for the entity CommercialPaymentInfo and its DTO CommercialPaymentInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialPiMapper.class})
public interface CommercialPaymentInfoMapper extends EntityMapper<CommercialPaymentInfoDTO, CommercialPaymentInfo> {

    @Mapping(source = "commercialPi.id", target = "commercialPiId")
    @Mapping(source = "commercialPi.proformaNo", target = "commercialPiProformaNo")
    CommercialPaymentInfoDTO toDto(CommercialPaymentInfo commercialPaymentInfo);

    @Mapping(source = "commercialPiId", target = "commercialPi")
    CommercialPaymentInfo toEntity(CommercialPaymentInfoDTO commercialPaymentInfoDTO);

    default CommercialPaymentInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialPaymentInfo commercialPaymentInfo = new CommercialPaymentInfo();
        commercialPaymentInfo.setId(id);
        return commercialPaymentInfo;
    }
}
