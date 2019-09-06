package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.MstAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MstAccount and its DTO MstAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {MstGroupMapper.class})
public interface MstAccountMapper extends EntityMapper<MstAccountDTO, MstAccount> {

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "group.name", target = "groupName")
    MstAccountDTO toDto(MstAccount mstAccount);

    @Mapping(source = "groupId", target = "group")
    MstAccount toEntity(MstAccountDTO mstAccountDTO);

    default MstAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        MstAccount mstAccount = new MstAccount();
        mstAccount.setId(id);
        return mstAccount;
    }
}
