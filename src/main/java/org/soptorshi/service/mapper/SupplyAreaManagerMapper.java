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
    @Mapping(source = "supplyZone.name", target = "supplyZoneName")
    @Mapping(source = "supplyArea.id", target = "supplyAreaId")
    @Mapping(source = "supplyArea.name", target = "supplyAreaName")
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullName", target = "employeeFullName")
    @Mapping(source = "supplyZoneManager.id", target = "supplyZoneManagerId")
    SupplyAreaManagerDTO toDto(SupplyAreaManager supplyAreaManager);

    @Mapping(source = "supplyZoneId", target = "supplyZone")
    @Mapping(source = "supplyAreaId", target = "supplyArea")
    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "supplyZoneManagerId", target = "supplyZoneManager")
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
