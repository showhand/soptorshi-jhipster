package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.SupplyArea;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.domain.enumeration.SupplyZoneManagerStatus;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.extended.SupplyAreaExtendedRepository;
import org.soptorshi.repository.search.SupplyAreaSearchRepository;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyAreaService;
import org.soptorshi.service.dto.SupplyAreaDTO;
import org.soptorshi.service.dto.SupplyZoneManagerDTO;
import org.soptorshi.service.mapper.SupplyAreaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SupplyArea.
 */
@Service
@Transactional
public class SupplyAreaExtendedService extends SupplyAreaService {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaExtendedService.class);

    private final SupplyAreaExtendedRepository supplyAreaExtendedRepository;

    private final SupplyAreaMapper supplyAreaMapper;

    private final SupplyAreaSearchRepository supplyAreaSearchRepository;

    private final EmployeeExtendedRepository employeeExtendedRepository;

    private final SupplyZoneManagerExtendedService supplyZoneManagerExtendedService;

    public SupplyAreaExtendedService(SupplyAreaExtendedRepository supplyAreaExtendedRepository, SupplyAreaMapper supplyAreaMapper, SupplyAreaSearchRepository supplyAreaSearchRepository, EmployeeExtendedRepository employeeExtendedRepository, SupplyZoneManagerExtendedService supplyZoneManagerExtendedService) {
        super(supplyAreaExtendedRepository, supplyAreaMapper, supplyAreaSearchRepository);
        this.supplyAreaExtendedRepository = supplyAreaExtendedRepository;
        this.supplyAreaMapper = supplyAreaMapper;
        this.supplyAreaSearchRepository = supplyAreaSearchRepository;
        this.employeeExtendedRepository = employeeExtendedRepository;
        this.supplyZoneManagerExtendedService = supplyZoneManagerExtendedService;
    }

    public SupplyAreaDTO save(SupplyAreaDTO supplyAreaDTO) {
        log.debug("Request to save SupplyArea : {}", supplyAreaDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if(supplyAreaDTO.getId() == null) {
            supplyAreaDTO.setCreatedBy(currentUser);
            supplyAreaDTO.setCreatedOn(currentDateTime);
        }
        else {
            supplyAreaDTO.setUpdatedBy(currentUser);
            supplyAreaDTO.setUpdatedOn(currentDateTime);
        }

        SupplyArea supplyArea = supplyAreaMapper.toEntity(supplyAreaDTO);
        supplyArea = supplyAreaExtendedRepository.save(supplyArea);
        SupplyAreaDTO result = supplyAreaMapper.toDto(supplyArea);
        supplyAreaSearchRepository.save(supplyArea);
        return result;
    }

    public boolean isValidInput(SupplyAreaDTO supplyAreaDTO) {
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER)) {
            return isValidZoneAsPerZoneManagerRole(supplyAreaDTO, currentUser) && isValidZoneManager(supplyAreaDTO);
        }
        return isValidZoneManager(supplyAreaDTO);
    }

    private boolean isValidZoneAsPerZoneManagerRole(SupplyAreaDTO supplyAreaDTO, String currentUser) {
        Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
        if (currentEmployee.isPresent()) {
            List<SupplyZoneManager> supplyZoneManagers = supplyZoneManagerExtendedService.getZoneManagers(currentEmployee.get(), SupplyZoneManagerStatus.ACTIVE);
            for (SupplyZoneManager supplyZoneManager : supplyZoneManagers) {
                if (supplyZoneManager.getSupplyZone().getId().equals(supplyAreaDTO.getSupplyZoneId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidZoneManager(SupplyAreaDTO supplyAreaDTO) {
        Optional<SupplyZoneManagerDTO> selectedZoneManager = supplyZoneManagerExtendedService.findOne(supplyAreaDTO.getSupplyZoneManagerId());
        return selectedZoneManager.map(supplyZoneManagerDTO -> supplyZoneManagerDTO.getSupplyZoneId().equals(supplyAreaDTO.getSupplyZoneId()) && supplyZoneManagerDTO.getStatus().equals(SupplyZoneManagerStatus.ACTIVE)).orElse(false);
    }
}
