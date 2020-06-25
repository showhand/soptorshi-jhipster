package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.SupplyAreaManager;
import org.soptorshi.domain.SupplyZoneManager;
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
import org.soptorshi.web.rest.errors.BadRequestAlertException;
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

    public SupplyAreaManagerExtendedService(SupplyAreaManagerExtendedRepository supplyAreaManagerExtendedRepository, SupplyAreaManagerMapper supplyAreaManagerMapper, SupplyAreaManagerSearchRepository supplyAreaManagerSearchRepository, SupplyZoneManagerExtendedService supplyZoneManagerExtendedService, SupplyAreaExtendedService supplyAreaExtendedService, EmployeeExtendedRepository employeeExtendedRepository,  SupplyZoneExtendedService supplyZoneExtendedService) {
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

        if(supplyAreaManagerDTO.getId() == null) {
            supplyAreaManagerDTO.setCreatedBy(currentUser);
            supplyAreaManagerDTO.setCreatedOn(currentDateTime);
        }
        else {
            supplyAreaManagerDTO.setUpdatedBy(currentUser);
            supplyAreaManagerDTO.setUpdatedOn(currentDateTime);
        }

        checkZoneValidityForRoleZoneManager(supplyAreaManagerDTO, currentUser);
        checkValidityForZone(supplyAreaManagerDTO);
        checkValidityForArea(supplyAreaManagerDTO);

        SupplyAreaManager supplyAreaManager = supplyAreaManagerMapper.toEntity(supplyAreaManagerDTO);
        supplyAreaManager = supplyAreaManagerExtendedRepository.save(supplyAreaManager);
        SupplyAreaManagerDTO result = supplyAreaManagerMapper.toDto(supplyAreaManager);
        supplyAreaManagerSearchRepository.save(supplyAreaManager);
        return result;
    }

    private void checkZoneValidityForRoleZoneManager(SupplyAreaManagerDTO supplyAreaManagerDTO, String currentUser) {
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER)) {
            Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
            if(currentEmployee.isPresent()) {
                List<SupplyZoneManager> supplyZoneManagers = supplyZoneManagerExtendedService.getZoneManagers(currentEmployee.get());
                if(supplyZoneManagers.size() > 0) {
                    boolean found = false;
                    for(SupplyZoneManager supplyZoneManager: supplyZoneManagers) {
                        if (supplyZoneManager.getSupplyZone().getId().equals(supplyAreaManagerDTO.getSupplyZoneId())) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        throw new BadRequestAlertException("Invalid Zone Selected", "supplyAreaManager", "invalidaccess");
                    }
                }
                else {
                    throw new BadRequestAlertException("Permission Denied", "supplyAreaManager", "invalidaccess");
                }
            }
            else {
                throw new BadRequestAlertException("Permission Denied", "supplyAreaManager", "invalidaccess");
            }
        }
    }

    private void checkValidityForZone(SupplyAreaManagerDTO supplyAreaManagerDTO) {
        Optional<SupplyZoneManagerDTO> selectedZoneManager = supplyZoneManagerExtendedService.findOne(supplyAreaManagerDTO.getSupplyZoneManagerId());
        if(selectedZoneManager.isPresent()) {
            if(!selectedZoneManager.get().getSupplyZoneId().equals(supplyAreaManagerDTO.getSupplyZoneId())) {
                throw new BadRequestAlertException("Invalid Zone Manager Selected", "supplyAreaManager", "invalidaccess");
            }
        }
        else {
            throw new BadRequestAlertException("Invalid Zone Manager Selected", "supplyAreaManager", "invalidaccess");
        }
    }

    private void checkValidityForArea(SupplyAreaManagerDTO supplyAreaManagerDTO) {
        Optional<SupplyAreaDTO> selectedArea = supplyAreaExtendedService.findOne(supplyAreaManagerDTO.getSupplyAreaId());
        if(selectedArea.isPresent()) {
            if(!selectedArea.get().getSupplyZoneId().equals(supplyAreaManagerDTO.getSupplyZoneId())) {
                throw new BadRequestAlertException("Invalid Area Selected", "supplyAreaManager", "invalidaccess");
            }
        }
        else {
            throw new BadRequestAlertException("Invalid Area Selected", "supplyAreaManager", "invalidaccess");
        }
    }

    public List<SupplyAreaManager> getAreaManagers(Employee employee) {
        return supplyAreaManagerExtendedRepository.getAllByEmployee(employee);
    }
}
