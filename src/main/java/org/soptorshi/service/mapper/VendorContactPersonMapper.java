package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.VendorContactPersonDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VendorContactPerson and its DTO VendorContactPersonDTO.
 */
@Mapper(componentModel = "spring", uses = {VendorMapper.class})
public interface VendorContactPersonMapper extends EntityMapper<VendorContactPersonDTO, VendorContactPerson> {

    @Mapping(source = "vendor.id", target = "vendorId")
    @Mapping(source = "vendor.companyName", target = "vendorCompanyName")
    VendorContactPersonDTO toDto(VendorContactPerson vendorContactPerson);

    @Mapping(source = "vendorId", target = "vendor")
    VendorContactPerson toEntity(VendorContactPersonDTO vendorContactPersonDTO);

    default VendorContactPerson fromId(Long id) {
        if (id == null) {
            return null;
        }
        VendorContactPerson vendorContactPerson = new VendorContactPerson();
        vendorContactPerson.setId(id);
        return vendorContactPerson;
    }
}
