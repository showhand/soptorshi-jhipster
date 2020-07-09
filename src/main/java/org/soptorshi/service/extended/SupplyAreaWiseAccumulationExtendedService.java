package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.SupplyAreaManager;
import org.soptorshi.domain.SupplyAreaWiseAccumulation;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.domain.enumeration.SupplyAreaManagerStatus;
import org.soptorshi.domain.enumeration.SupplyAreaWiseAccumulationStatus;
import org.soptorshi.domain.enumeration.SupplyZoneManagerStatus;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.extended.SupplyAreaWiseAccumulationExtendedRepository;
import org.soptorshi.repository.search.SupplyAreaWiseAccumulationSearchRepository;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyAreaWiseAccumulationService;
import org.soptorshi.service.dto.SupplyAreaDTO;
import org.soptorshi.service.dto.SupplyAreaManagerDTO;
import org.soptorshi.service.dto.SupplyAreaWiseAccumulationDTO;
import org.soptorshi.service.dto.SupplyZoneManagerDTO;
import org.soptorshi.service.mapper.SupplyAreaWiseAccumulationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SupplyAreaWiseAccumulation.
 */
@Service
@Transactional
public class SupplyAreaWiseAccumulationExtendedService extends SupplyAreaWiseAccumulationService {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaWiseAccumulationExtendedService.class);

    private final SupplyAreaWiseAccumulationExtendedRepository supplyAreaWiseAccumulationExtendedRepository;

    private final SupplyAreaWiseAccumulationMapper supplyAreaWiseAccumulationMapper;

    private final SupplyAreaWiseAccumulationSearchRepository supplyAreaWiseAccumulationSearchRepository;

    private final SupplyAreaManagerExtendedService supplyAreaManagerExtendedService;

    private final SupplyZoneManagerExtendedService supplyZoneManagerExtendedService;

    private final SupplyAreaExtendedService supplyAreaExtendedService;

    private final EmployeeExtendedRepository employeeExtendedRepository;

    public SupplyAreaWiseAccumulationExtendedService(SupplyAreaWiseAccumulationExtendedRepository supplyAreaWiseAccumulationExtendedRepository, SupplyAreaWiseAccumulationMapper supplyAreaWiseAccumulationMapper, SupplyAreaWiseAccumulationSearchRepository supplyAreaWiseAccumulationSearchRepository,
                                                     SupplyAreaManagerExtendedService supplyAreaManagerExtendedService, SupplyZoneManagerExtendedService supplyZoneManagerExtendedService, SupplyAreaExtendedService supplyAreaExtendedService, EmployeeExtendedRepository employeeExtendedRepository) {
        super(supplyAreaWiseAccumulationExtendedRepository, supplyAreaWiseAccumulationMapper, supplyAreaWiseAccumulationSearchRepository);
        this.supplyAreaWiseAccumulationExtendedRepository = supplyAreaWiseAccumulationExtendedRepository;
        this.supplyAreaWiseAccumulationMapper = supplyAreaWiseAccumulationMapper;
        this.supplyAreaWiseAccumulationSearchRepository = supplyAreaWiseAccumulationSearchRepository;
        this.supplyAreaManagerExtendedService = supplyAreaManagerExtendedService;
        this.supplyZoneManagerExtendedService = supplyZoneManagerExtendedService;
        this.supplyAreaExtendedService = supplyAreaExtendedService;
        this.employeeExtendedRepository = employeeExtendedRepository;
    }

    public SupplyAreaWiseAccumulationDTO save(SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO) {
        log.debug("Request to save SupplyAreaWiseAccumulation : {}", supplyAreaWiseAccumulationDTO);
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if(supplyAreaWiseAccumulationDTO.getId() == null) {
            supplyAreaWiseAccumulationDTO.setCreatedBy(currentUser);
            supplyAreaWiseAccumulationDTO.setCreatedOn(currentDateTime);
        }
        else {
            supplyAreaWiseAccumulationDTO.setUpdatedBy(currentUser);
            supplyAreaWiseAccumulationDTO.setUpdatedOn(currentDateTime);
        }

        SupplyAreaWiseAccumulation supplyAreaWiseAccumulation = supplyAreaWiseAccumulationMapper.toEntity(supplyAreaWiseAccumulationDTO);
        supplyAreaWiseAccumulation = supplyAreaWiseAccumulationExtendedRepository.save(supplyAreaWiseAccumulation);
        SupplyAreaWiseAccumulationDTO result = supplyAreaWiseAccumulationMapper.toDto(supplyAreaWiseAccumulation);
        supplyAreaWiseAccumulationSearchRepository.save(supplyAreaWiseAccumulation);
        return result;
    }

    public boolean isValidInput(SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO) {
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER)) {
            return isValidZoneAsPerZoneManagerRole(supplyAreaWiseAccumulationDTO, currentUser) && isValidZoneManager(supplyAreaWiseAccumulationDTO) && isValidArea(supplyAreaWiseAccumulationDTO) && isValidAreaManager(supplyAreaWiseAccumulationDTO);
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER)) {
            return isValidZoneAsPerAreaManagerRole(supplyAreaWiseAccumulationDTO, currentUser) && isValidZoneManager(supplyAreaWiseAccumulationDTO) && isValidArea(supplyAreaWiseAccumulationDTO) && isValidAreaManager(supplyAreaWiseAccumulationDTO);
        }
        return isValidZoneManager(supplyAreaWiseAccumulationDTO) && isValidArea(supplyAreaWiseAccumulationDTO) && isValidAreaManager(supplyAreaWiseAccumulationDTO);
    }

    private boolean isValidZoneAsPerZoneManagerRole(SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO, String currentUser) {
        Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
        if (currentEmployee.isPresent()) {
            List<SupplyZoneManager> supplyZoneManagers = supplyZoneManagerExtendedService.getZoneManagers(currentEmployee.get(), SupplyZoneManagerStatus.ACTIVE);
            for (SupplyZoneManager supplyZoneManager : supplyZoneManagers) {
                if (supplyZoneManager.getSupplyZone().getId().equals(supplyAreaWiseAccumulationDTO.getSupplyZoneId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidZoneAsPerAreaManagerRole(SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO, String currentUser) {
        Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
        if (currentEmployee.isPresent()) {
            List<SupplyAreaManager> supplyAreaManagers = supplyAreaManagerExtendedService.getAreaManagers(currentEmployee.get(), SupplyAreaManagerStatus.ACTIVE);
            for (SupplyAreaManager supplyAreaManager : supplyAreaManagers) {
                if (supplyAreaManager.getSupplyZone().getId().equals(supplyAreaWiseAccumulationDTO.getSupplyZoneId())
                    && supplyAreaManager.getSupplyZoneManager().getId().equals(supplyAreaWiseAccumulationDTO.getSupplyZoneManagerId())
                    && supplyAreaManager.getSupplyArea().getId().equals(supplyAreaWiseAccumulationDTO.getSupplyAreaId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidZoneManager(SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO) {
        Optional<SupplyZoneManagerDTO> selectedZoneManager = supplyZoneManagerExtendedService.findOne(supplyAreaWiseAccumulationDTO.getSupplyZoneManagerId());
        return selectedZoneManager.map(supplyZoneManagerDTO -> supplyZoneManagerDTO.getSupplyZoneId().equals(supplyAreaWiseAccumulationDTO.getSupplyZoneId()) && supplyZoneManagerDTO.getStatus().equals(SupplyZoneManagerStatus.ACTIVE)).orElse(false);
    }

    private boolean isValidArea(SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO) {
        Optional<SupplyAreaDTO> selectedArea = supplyAreaExtendedService.findOne(supplyAreaWiseAccumulationDTO.getSupplyAreaId());
        return selectedArea.map(supplyAreaDTO -> supplyAreaDTO.getSupplyZoneId().equals(supplyAreaWiseAccumulationDTO.getSupplyZoneId()) &&
            supplyAreaDTO.getSupplyZoneManagerId().equals(supplyAreaWiseAccumulationDTO.getSupplyZoneManagerId())).orElse(false);
    }

    private boolean isValidAreaManager(SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO) {
        Optional<SupplyAreaManagerDTO> selectedAreaManager = supplyAreaManagerExtendedService.findOne(supplyAreaWiseAccumulationDTO.getSupplyAreaManagerId());
        return selectedAreaManager.filter(supplyAreaManagerDTO -> supplyAreaManagerDTO.getSupplyZoneId().equals(supplyAreaWiseAccumulationDTO.getSupplyZoneId()) &&
            supplyAreaManagerDTO.getSupplyZoneManagerId().equals(supplyAreaWiseAccumulationDTO.getSupplyZoneManagerId()) &&
            supplyAreaManagerDTO.getSupplyAreaId().equals(supplyAreaWiseAccumulationDTO.getSupplyAreaId())).isPresent();
    }

    public boolean isValidStatus(SupplyAreaWiseAccumulationDTO supplyAreaWiseAccumulationDTO) {
        Optional<SupplyAreaWiseAccumulationDTO> supplyAreaWiseAccumulationDTO1 = findOne(supplyAreaWiseAccumulationDTO.getId());
        return supplyAreaWiseAccumulationDTO1.map(areaWiseAccumulationDTO -> areaWiseAccumulationDTO.getStatus().equals(SupplyAreaWiseAccumulationStatus.FORWARDED)).orElse(false);
    }

    public List<SupplyAreaWiseAccumulation> getAllByZoneWiseAccumulationRefNo(String refNo) {
        return supplyAreaWiseAccumulationExtendedRepository.getAllByZoneWiseAccumulationRefNo(refNo);
    }
}
