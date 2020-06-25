package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.service.dto.SupplyZoneManagerDTO;

/**
 * Mapper for the entity SupplyZoneManager and its DTO SupplyZoneManagerDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyZoneMapper.class, EmployeeMapper.class})
public interface SupplyZoneManagerMapper extends EntityMapper<SupplyZoneManagerDTO, SupplyZoneManager> {

    @Mapping(source = "supplyZone.id", target = "supplyZoneId")
    @Mapping(source = "supplyZone.name", target = "supplyZoneName")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullName", target = "employeeFullName")
    SupplyZoneManagerDTO toDto(SupplyZoneManager supplyZoneManager);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    @Mapping(source = "employeeId", target = "employee")
    SupplyZoneManager toEntity(SupplyZoneManagerDTO supplyZoneManagerDTO);

    default SupplyZoneManager fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplyZoneManager supplyZoneManager = new SupplyZoneManager();
        supplyZoneManager.setId(id);
        return supplyZoneManager;
    }
}
