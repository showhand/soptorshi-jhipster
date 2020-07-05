package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.SupplyAreaManager;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.domain.enumeration.SupplyAreaManagerStatus;
import org.soptorshi.domain.enumeration.SupplyZoneManagerStatus;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.extended.SupplyAreaManagerExtendedRepository;
import org.soptorshi.repository.search.SupplyAreaManagerSearchRepository;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyAreaManagerService;
import org.soptorshi.service.dto.SupplyAreaDTO;
import org.soptorshi.service.dto.SupplyAreaManagerDTO;
import org.soptorshi.service.dto.SupplyZoneManagerDTO;
import org.soptorshi.service.mapper.SupplyAreaManagerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SupplyAreaManager.
 */
@Service
@Transactional
public class SupplyAreaManagerExtendedService extends SupplyAreaManagerService {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaManagerExtendedService.class);

    private final SupplyAreaManagerExtendedRepository supplyAreaManagerExtendedRepository;

    private final SupplyAreaManagerMapper supplyAreaManagerMapper;

    private final SupplyAreaManagerSearchRepository supplyAreaManagerSearchRepository;

    private final SupplyZoneManagerExtendedService supplyZoneManagerExtendedService;

    private final SupplyAreaExtendedService supplyAreaExtendedService;

    private final EmployeeExtendedRepository employeeExtendedRepository;

    private final SupplyZoneExtendedService supplyZoneExtendedService;

    public SupplyAreaManagerExtendedService(SupplyAreaManagerExtendedRepository supplyAreaManagerExtendedRepository, SupplyAreaManagerMapper supplyAreaManagerMapper, SupplyAreaManagerSearchRepository supplyAreaManagerSearchRepository, SupplyZoneManagerExtendedService supplyZoneManagerExtendedService, SupplyAreaExtendedService supplyAreaExtendedService, EmployeeExtendedRepository employeeExtendedRepository, SupplyZoneExtendedService supplyZoneExtendedService) {
        super(supplyAreaManagerExtendedRepository, supplyAreaManagerMapper, supplyAreaManagerSearchRepository);

        this.supplyAreaManagerExtendedRepository = supplyAreaManagerExtendedRepository;
        this.supplyAreaManagerMapper = supplyAreaManagerMapper;
        this.supplyAreaManagerSearchRepository = supplyAreaManagerSearchRepository;
        this.supplyZoneManagerExtendedService = supplyZoneManagerExtendedService;
        this.supplyAreaExtendedService = supplyAreaExtendedService;
        this.employeeExtendedRepository = employeeExtendedRepository;
        this.supplyZoneExtendedService = supplyZoneExtendedService;
    }

    public SupplyAreaManagerDTO save(SupplyAreaManagerDTO supplyAreaManagerDTO) {
        log.debug("Request to save SupplyAreaManager : {}", supplyAreaManagerDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if (supplyAreaManagerDTO.getId() == null) {
            supplyAreaManagerDTO.setCreatedBy(currentUser);
            supplyAreaManagerDTO.setCreatedOn(currentDateTime);
        } else {
            supplyAreaManagerDTO.setUpdatedBy(currentUser);
            supplyAreaManagerDTO.setUpdatedOn(currentDateTime);
        }

        SupplyAreaManager supplyAreaManager = supplyAreaManagerMapper.toEntity(supplyAreaManagerDTO);
        supplyAreaManager = supplyAreaManagerExtendedRepository.save(supplyAreaManager);
        SupplyAreaManagerDTO result = supplyAreaManagerMapper.toDto(supplyAreaManager);
        supplyAreaManagerSearchRepository.save(supplyAreaManager);
        return result;
    }

    public boolean isValidInput(SupplyAreaManagerDTO supplyAreaManagerDTO) {
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER)) {
            return isValidZoneAsPerZoneManagerRole(supplyAreaManagerDTO, currentUser) && isValidZoneManager(supplyAreaManagerDTO) && isValidArea(supplyAreaManagerDTO);
        }
        return isValidZoneManager(supplyAreaManagerDTO) && isValidArea(supplyAreaManagerDTO);
    }

    private boolean isValidZoneAsPerZoneManagerRole(SupplyAreaManagerDTO supplyAreaManagerDTO, String currentUser) {
        Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
        if (currentEmployee.isPresent()) {
            List<SupplyZoneManager> supplyZoneManagers = supplyZoneManagerExtendedService.getZoneManagers(currentEmployee.get(), SupplyZoneManagerStatus.ACTIVE);
            for (SupplyZoneManager supplyZoneManager : supplyZoneManagers) {
                if (supplyZoneManager.getSupplyZone().getId().equals(supplyAreaManagerDTO.getSupplyZoneId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidZoneManager(SupplyAreaManagerDTO supplyAreaManagerDTO) {
        Optional<SupplyZoneManagerDTO> selectedZoneManager = supplyZoneManagerExtendedService.findOne(supplyAreaManagerDTO.getSupplyZoneManagerId());
        return selectedZoneManager.map(supplyZoneManagerDTO -> supplyZoneManagerDTO.getSupplyZoneId().equals(supplyAreaManagerDTO.getSupplyZoneId()) && supplyZoneManagerDTO.getStatus().equals(SupplyZoneManagerStatus.ACTIVE)).orElse(false);
    }

    private boolean isValidArea(SupplyAreaManagerDTO supplyAreaManagerDTO) {
        Optional<SupplyAreaDTO> selectedArea = supplyAreaExtendedService.findOne(supplyAreaManagerDTO.getSupplyAreaId());
        return selectedArea.map(supplyAreaDTO -> supplyAreaDTO.getSupplyZoneId().equals(supplyAreaManagerDTO.getSupplyZoneId()) && supplyAreaDTO.getSupplyZoneManagerId().equals(supplyAreaManagerDTO.getSupplyZoneManagerId())).orElse(false);
    }

    public List<SupplyAreaManager> getAreaManagers(Employee employee) {
        return supplyAreaManagerExtendedRepository.getAllByEmployee(employee);
    }

    public List<SupplyAreaManager> getAreaManagers(Employee employee, SupplyAreaManagerStatus supplyAreaManagerStatus) {
        return supplyAreaManagerExtendedRepository.getAllByEmployeeAndStatus(employee, supplyAreaManagerStatus);
    }
}
