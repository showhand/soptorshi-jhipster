package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.SupplyAreaManager;
import org.soptorshi.service.dto.SupplyAreaManagerDTO;

/**
 * Mapper for the entity SupplyAreaManager and its DTO SupplyAreaManagerDTO.
 */
@Mapper(componentModel = "spring", uses = {SupplyZoneMapper.class, SupplyAreaMapper.class, EmployeeMapper.class, SupplyZoneManagerMapper.class})
public interface SupplyAreaManagerMapper extends EntityMapper<SupplyAreaManagerDTO, SupplyAreaManager> {

    @Mapping(source = "supplyZone.id", target = "supplyZoneId")
    @Mapping(source = "supplyZone.zoneName", target = "supplyZoneZoneName")
    @Mapping(source = "supplyArea.id", target = "supplyAreaId")
    @Mapping(source = "supplyArea.areaName", target = "supplyAreaAreaName")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullName", target = "employeeFullName")
    @Mapping(source = "supplyZoneManagers.id", target = "supplyZoneManagersId")
    SupplyAreaManagerDTO toDto(SupplyAreaManager supplyAreaManager);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    @Mapping(source = "supplyAreaId", target = "supplyArea")
    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "supplyZoneManagersId", target = "supplyZoneManagers")
    SupplyAreaManager toEntity(SupplyAreaManagerDTO supplyAreaManagerDTO);

    default SupplyAreaManager fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplyAreaManager supplyAreaManager = new SupplyAreaManager();
        supplyAreaManager.setId(id);
        return supplyAreaManager;
    }
}
