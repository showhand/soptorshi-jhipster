package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.ProvidentFundDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProvidentFund and its DTO ProvidentFundDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProvidentFundMapper extends EntityMapper<ProvidentFundDTO, ProvidentFund> {



    default ProvidentFund fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProvidentFund providentFund = new ProvidentFund();
        providentFund.setId(id);
        return providentFund;
    }
}
