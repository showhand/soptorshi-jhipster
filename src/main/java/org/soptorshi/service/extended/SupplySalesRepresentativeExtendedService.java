package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.SupplyAreaManager;
import org.soptorshi.domain.SupplySalesRepresentative;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.domain.enumeration.SupplyAreaManagerStatus;
import org.soptorshi.domain.enumeration.SupplyZoneManagerStatus;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.extended.SupplySalesRepresentativeExtendedRepository;
import org.soptorshi.repository.search.SupplySalesRepresentativeSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplySalesRepresentativeService;
import org.soptorshi.service.dto.SupplyAreaDTO;
import org.soptorshi.service.dto.SupplyAreaManagerDTO;
import org.soptorshi.service.dto.SupplySalesRepresentativeDTO;
import org.soptorshi.service.dto.SupplyZoneManagerDTO;
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

    public SupplySalesRepresentativeExtendedService(SupplySalesRepresentativeExtendedRepository supplySalesRepresentativeExtendedRepository, SupplySalesRepresentativeMapper supplySalesRepresentativeMapper, SupplySalesRepresentativeSearchRepository supplySalesRepresentativeSearchRepository, SupplyZoneManagerExtendedService supplyZoneManagerExtendedService, SupplyAreaExtendedService supplyAreaExtendedService, EmployeeExtendedRepository employeeExtendedRepository, SupplyZoneExtendedService supplyZoneExtendedService, SupplyAreaManagerExtendedService supplyAreaManagerExtendedService) {
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

        if (supplySalesRepresentativeDTO.getId() == null) {
            supplySalesRepresentativeDTO.setCreatedBy(currentUser);
            supplySalesRepresentativeDTO.setCreatedOn(currentDateTime);
        } else {
            supplySalesRepresentativeDTO.setUpdatedBy(currentUser);
            supplySalesRepresentativeDTO.setUpdatedOn(currentDateTime);
        }

        SupplySalesRepresentative supplySalesRepresentative = supplySalesRepresentativeMapper.toEntity(supplySalesRepresentativeDTO);
        supplySalesRepresentative = supplySalesRepresentativeExtendedRepository.save(supplySalesRepresentative);
        SupplySalesRepresentativeDTO result = supplySalesRepresentativeMapper.toDto(supplySalesRepresentative);
        supplySalesRepresentativeSearchRepository.save(supplySalesRepresentative);
        return result;
    }

    /*public boolean isValidInput(SupplyAreaManagerDTO supplyAreaManagerDTO) {
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER)) {
            return isValidZoneAsPerZoneManagerRole(supplyAreaManagerDTO, currentUser) && isValidZoneManager(supplyAreaManagerDTO) && isValidArea(supplyAreaManagerDTO);
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER)) {
            return isValidZoneAsPerZoneManagerRole(supplyAreaManagerDTO, currentUser) && isValidZoneManager(supplyAreaManagerDTO) && isValidArea(supplyAreaManagerDTO);
        }
        return isValidZoneManager(supplyAreaManagerDTO) && isValidArea(supplyAreaManagerDTO);
    }*/

    private boolean isValidZoneAsPerZoneManagerRole(SupplySalesRepresentativeDTO supplySalesRepresentativeDTO, String currentUser) {
        Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
        if (currentEmployee.isPresent()) {
            List<SupplyZoneManager> supplyZoneManagers = supplyZoneManagerExtendedService.getZoneManagers(currentEmployee.get(), SupplyZoneManagerStatus.ACTIVE);
            for (SupplyZoneManager supplyZoneManager : supplyZoneManagers) {
                if (supplyZoneManager.getSupplyZone().getId().equals(supplySalesRepresentativeDTO.getSupplyZoneId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidAreaAsPerAreaManagerRole(SupplySalesRepresentativeDTO supplySalesRepresentativeDTO, String currentUser) {
        Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
        if (currentEmployee.isPresent()) {
            List<SupplyAreaManager> supplyAreaManagers = supplyAreaManagerExtendedService.getAreaManagers(currentEmployee.get(), SupplyAreaManagerStatus.ACTIVE);
            for (SupplyAreaManager supplyAreaManager : supplyAreaManagers) {
                if (supplyAreaManager.getSupplyZone().getId().equals(supplySalesRepresentativeDTO.getSupplyZoneId())
                && supplyAreaManager.getSupplyArea().getId().equals(supplySalesRepresentativeDTO.getSupplyAreaId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidZoneManager(SupplySalesRepresentativeDTO supplySalesRepresentativeDTO) {
        Optional<SupplyZoneManagerDTO> selectedZoneManager = supplyZoneManagerExtendedService.findOne(supplySalesRepresentativeDTO.getSupplyZoneManagerId());
        return selectedZoneManager.map(supplyZoneManagerDTO -> supplyZoneManagerDTO.getSupplyZoneId().equals(supplySalesRepresentativeDTO.getSupplyZoneId()) && supplyZoneManagerDTO.getStatus().equals(SupplyZoneManagerStatus.ACTIVE)).orElse(false);
    }

    private boolean isValidArea(SupplySalesRepresentativeDTO supplySalesRepresentativeDTO) {
        Optional<SupplyAreaDTO> selectedArea = supplyAreaExtendedService.findOne(supplySalesRepresentativeDTO.getSupplyAreaId());
        return selectedArea.map(supplyAreaDTO -> supplyAreaDTO.getSupplyZoneId().equals(supplySalesRepresentativeDTO.getSupplyZoneId())).orElse(false);
    }

    private boolean isValidAreaManager(SupplySalesRepresentativeDTO supplySalesRepresentativeDTO) {
        Optional<SupplyAreaManagerDTO> selectedAreaManager = supplyAreaManagerExtendedService.findOne(supplySalesRepresentativeDTO.getSupplyAreaManagerId());
        if (selectedAreaManager.isPresent()) {
            if (!selectedAreaManager.get().getSupplyZoneId().equals(supplySalesRepresentativeDTO.getSupplyZoneId())) {
                throw new BadRequestAlertException("Invalid Area Manager Selected", "supplySalesRepresentative", "invalidaccess");
            }
            if (!selectedAreaManager.get().getSupplyAreaId().equals(supplySalesRepresentativeDTO.getSupplyAreaId())) {
                throw new BadRequestAlertException("Invalid Area Manager Selected", "supplySalesRepresentative", "invalidaccess");
            }
        }
        return false;
    }
}
