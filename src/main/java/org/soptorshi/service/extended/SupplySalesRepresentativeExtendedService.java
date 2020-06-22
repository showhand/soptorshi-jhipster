package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.SupplyAreaManager;
import org.soptorshi.domain.SupplySalesRepresentative;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.extended.SupplySalesRepresentativeExtendedRepository;
import org.soptorshi.repository.search.SupplySalesRepresentativeSearchRepository;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplySalesRepresentativeService;
import org.soptorshi.service.dto.SupplyAreaDTO;
import org.soptorshi.service.dto.SupplyAreaManagerDTO;
import org.soptorshi.service.dto.SupplySalesRepresentativeDTO;
import org.soptorshi.service.mapper.SupplySalesRepresentativeMapper;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SupplySalesRepresentative.
 */
@Service
@Transactional
public class SupplySalesRepresentativeExtendedService extends SupplySalesRepresentativeService {

    private final Logger log = LoggerFactory.getLogger(SupplySalesRepresentativeExtendedService.class);

    private final SupplySalesRepresentativeExtendedRepository supplySalesRepresentativeExtendedRepository;

    private final SupplySalesRepresentativeMapper supplySalesRepresentativeMapper;

    private final SupplySalesRepresentativeSearchRepository supplySalesRepresentativeSearchRepository;

    private final SupplyZoneManagerExtendedService supplyZoneManagerExtendedService;

    private final SupplyAreaExtendedService supplyAreaExtendedService;

    private final EmployeeExtendedRepository employeeExtendedRepository;

    private final SupplyZoneExtendedService supplyZoneExtendedService;

    private final SupplyAreaManagerExtendedService supplyAreaManagerExtendedService;

    public SupplySalesRepresentativeExtendedService(SupplySalesRepresentativeExtendedRepository supplySalesRepresentativeExtendedRepository, SupplySalesRepresentativeMapper supplySalesRepresentativeMapper, SupplySalesRepresentativeSearchRepository supplySalesRepresentativeSearchRepository, SupplyZoneManagerExtendedService supplyZoneManagerExtendedService, SupplyAreaExtendedService supplyAreaExtendedService, EmployeeExtendedRepository employeeExtendedRepository,  SupplyZoneExtendedService supplyZoneExtendedService, SupplyAreaManagerExtendedService supplyAreaManagerExtendedService) {
        super(supplySalesRepresentativeExtendedRepository, supplySalesRepresentativeMapper, supplySalesRepresentativeSearchRepository);
        this.supplySalesRepresentativeExtendedRepository = supplySalesRepresentativeExtendedRepository;
        this.supplySalesRepresentativeMapper = supplySalesRepresentativeMapper;
        this.supplySalesRepresentativeSearchRepository = supplySalesRepresentativeSearchRepository;
        this.supplyZoneManagerExtendedService = supplyZoneManagerExtendedService;
        this.supplyAreaExtendedService = supplyAreaExtendedService;
        this.employeeExtendedRepository = employeeExtendedRepository;
        this.supplyZoneExtendedService = supplyZoneExtendedService;
        this.supplyAreaManagerExtendedService = supplyAreaManagerExtendedService;
    }

    public SupplySalesRepresentativeDTO save(SupplySalesRepresentativeDTO supplySalesRepresentativeDTO) {
        log.debug("Request to save SupplySalesRepresentative : {}", supplySalesRepresentativeDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if(supplySalesRepresentativeDTO.getId() == null) {
            supplySalesRepresentativeDTO.setCreatedBy(currentUser);
            supplySalesRepresentativeDTO.setCreatedOn(currentDateTime);
        }
        else {
            supplySalesRepresentativeDTO.setUpdatedBy(currentUser);
            supplySalesRepresentativeDTO.setUpdatedOn(currentDateTime);
        }

        checkZoneValidityForRoleZoneManager(supplySalesRepresentativeDTO, currentUser);
        checkZoneValidityForRoleAreaManager(supplySalesRepresentativeDTO, currentUser);
        checkValidityForArea(supplySalesRepresentativeDTO);
        checkValidityForAreaManager(supplySalesRepresentativeDTO);

        SupplySalesRepresentative supplySalesRepresentative = supplySalesRepresentativeMapper.toEntity(supplySalesRepresentativeDTO);
        supplySalesRepresentative = supplySalesRepresentativeExtendedRepository.save(supplySalesRepresentative);
        SupplySalesRepresentativeDTO result = supplySalesRepresentativeMapper.toDto(supplySalesRepresentative);
        supplySalesRepresentativeSearchRepository.save(supplySalesRepresentative);
        return result;
    }

    private void checkZoneValidityForRoleAreaManager(SupplySalesRepresentativeDTO supplySalesRepresentativeDTO, String currentUser) {
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER)) {
            Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
            if(currentEmployee.isPresent()) {
                List<SupplyAreaManager> supplyAreaManagers = supplyAreaManagerExtendedService.getAreaManagers(currentEmployee.get());
                if(supplyAreaManagers.size() > 0) {
                    boolean found = false;
                    for(SupplyAreaManager supplyAreaManager: supplyAreaManagers) {
                        if (supplyAreaManager.getSupplyZone().getId().equals(supplySalesRepresentativeDTO.getSupplyZoneId())) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        throw new BadRequestAlertException("Invalid Zone Selected", "supplySalesRepresentative", "invalidaccess");
                    }
                }
                else {
                    throw new BadRequestAlertException("Permission Denied", "supplySalesRepresentative", "invalidaccess");
                }
            }
            else {
                throw new BadRequestAlertException("Permission Denied", "supplySalesRepresentative", "invalidaccess");
            }
        }
    }

    private void checkZoneValidityForRoleZoneManager(SupplySalesRepresentativeDTO supplySalesRepresentativeDTO, String currentUser) {
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER)) {
            Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
            if(currentEmployee.isPresent()) {
                List<SupplyZoneManager> supplyZoneManagers = supplyZoneManagerExtendedService.getZoneManagers(currentEmployee.get());
                if(supplyZoneManagers.size() > 0) {
                    boolean found = false;
                    for(SupplyZoneManager supplyZoneManager: supplyZoneManagers) {
                        if (supplyZoneManager.getSupplyZone().getId().equals(supplySalesRepresentativeDTO.getSupplyZoneId())) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        throw new BadRequestAlertException("Invalid Zone Selected", "supplySalesRepresentative", "invalidaccess");
                    }
                }
                else {
                    throw new BadRequestAlertException("Permission Denied", "supplySalesRepresentative", "invalidaccess");
                }
            }
            else {
                throw new BadRequestAlertException("Permission Denied", "supplySalesRepresentative", "invalidaccess");
            }
        }
    }

    private void checkValidityForArea(SupplySalesRepresentativeDTO supplySalesRepresentativeDTO) {
        Optional<SupplyAreaDTO> selectedArea = supplyAreaExtendedService.findOne(supplySalesRepresentativeDTO.getSupplyAreaId());
        if(selectedArea.isPresent()) {
            if(!selectedArea.get().getSupplyZoneId().equals(supplySalesRepresentativeDTO.getSupplyZoneId())) {
                throw new BadRequestAlertException("Invalid Area Selected", "supplySalesRepresentative", "invalidaccess");
            }
        }
        else {
            throw new BadRequestAlertException("Invalid Area Selected", "supplySalesRepresentative", "invalidaccess");
        }
    }

    private void checkValidityForAreaManager(SupplySalesRepresentativeDTO supplySalesRepresentativeDTO) {
        Optional<SupplyAreaManagerDTO> selectedAreaManager = supplyAreaManagerExtendedService.findOne(supplySalesRepresentativeDTO.getSupplyAreaManagerId());
        if(selectedAreaManager.isPresent()) {
            if(!selectedAreaManager.get().getSupplyZoneId().equals(supplySalesRepresentativeDTO.getSupplyZoneId())) {
                throw new BadRequestAlertException("Invalid Area Manager Selected", "supplySalesRepresentative", "invalidaccess");
            }
            if(!selectedAreaManager.get().getSupplyAreaId().equals(supplySalesRepresentativeDTO.getSupplyAreaId())) {
                throw new BadRequestAlertException("Invalid Area Manager Selected", "supplySalesRepresentative", "invalidaccess");
            }
        }
        else {
            throw new BadRequestAlertException("Invalid Area Selected", "supplySalesRepresentative", "invalidaccess");
        }
    }
}
