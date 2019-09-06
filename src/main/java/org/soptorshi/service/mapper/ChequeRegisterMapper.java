package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.ChequeRegisterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ChequeRegister and its DTO ChequeRegisterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ChequeRegisterMapper extends EntityMapper<ChequeRegisterDTO, ChequeRegister> {



    default ChequeRegister fromId(Long id) {
        if (id == null) {
            return null;
        }
        ChequeRegister chequeRegister = new ChequeRegister();
        chequeRegister.setId(id);
        return chequeRegister;
    }
}
