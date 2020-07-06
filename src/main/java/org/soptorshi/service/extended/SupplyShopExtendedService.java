package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.SupplyAreaManager;
import org.soptorshi.domain.SupplyShop;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.domain.enumeration.SupplyAreaManagerStatus;
import org.soptorshi.domain.enumeration.SupplyZoneManagerStatus;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.extended.SupplyShopExtendedRepository;
import org.soptorshi.repository.search.SupplyShopSearchRepository;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyShopService;
import org.soptorshi.service.dto.*;
import org.soptorshi.service.mapper.SupplyShopMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SupplyShop.
 */
@Service
@Transactional
public class SupplyShopExtendedService extends SupplyShopService {

    private final Logger log = LoggerFactory.getLogger(SupplyShopExtendedService.class);

    private final SupplyShopExtendedRepository supplyShopExtendedRepository;

    private final SupplyShopMapper supplyShopMapper;

    private final SupplyShopSearchRepository supplyShopSearchRepository;

    private final SupplyZoneManagerExtendedService supplyZoneManagerExtendedService;

    private final SupplyAreaExtendedService supplyAreaExtendedService;

    private final EmployeeExtendedRepository employeeExtendedRepository;

    private final SupplyZoneExtendedService supplyZoneExtendedService;

    private final SupplyAreaManagerExtendedService supplyAreaManagerExtendedService;

    private final SupplySalesRepresentativeExtendedService supplySalesRepresentativeExtendedService;

    public SupplyShopExtendedService(SupplyShopExtendedRepository supplyShopExtendedRepository, SupplyShopMapper supplyShopMapper, SupplyShopSearchRepository supplyShopSearchRepository, SupplyZoneManagerExtendedService supplyZoneManagerExtendedService, SupplyAreaExtendedService supplyAreaExtendedService, EmployeeExtendedRepository employeeExtendedRepository,  SupplyZoneExtendedService supplyZoneExtendedService, SupplyAreaManagerExtendedService supplyAreaManagerExtendedService, SupplySalesRepresentativeExtendedService supplySalesRepresentativeExtendedService) {
        super(supplyShopExtendedRepository, supplyShopMapper, supplyShopSearchRepository);
        this.supplyShopExtendedRepository = supplyShopExtendedRepository;
        this.supplyShopMapper = supplyShopMapper;
        this.supplyShopSearchRepository = supplyShopSearchRepository;
        this.supplyZoneManagerExtendedService = supplyZoneManagerExtendedService;
        this.supplyAreaExtendedService = supplyAreaExtendedService;
        this.employeeExtendedRepository = employeeExtendedRepository;
        this.supplyZoneExtendedService = supplyZoneExtendedService;
        this.supplyAreaManagerExtendedService = supplyAreaManagerExtendedService;
        this.supplySalesRepresentativeExtendedService = supplySalesRepresentativeExtendedService;
    }

    public SupplyShopDTO save(SupplyShopDTO supplyShopDTO) {
        log.debug("Request to save SupplyShop : {}", supplyShopDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if(supplyShopDTO.getId() == null) {
            supplyShopDTO.setCreatedBy(currentUser);
            supplyShopDTO.setCreatedOn(currentDateTime);
        }
        else {
            supplyShopDTO.setUpdatedBy(currentUser);
            supplyShopDTO.setUpdatedOn(currentDateTime);
        }

        SupplyShop supplyShop = supplyShopMapper.toEntity(supplyShopDTO);
        supplyShop = supplyShopExtendedRepository.save(supplyShop);
        SupplyShopDTO result = supplyShopMapper.toDto(supplyShop);
        supplyShopSearchRepository.save(supplyShop);
        return result;
    }

    public boolean isValidInput(SupplyShopDTO supplyShopDTO) {
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER)) {
            return isValidZoneAsPerZoneManagerRole(supplyShopDTO, currentUser) && isValidZoneManager(supplyShopDTO) && isValidArea(supplyShopDTO) && isValidAreaManager(supplyShopDTO) && isValidSalesRepresentative(supplyShopDTO);
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER)) {
            return isValidZoneAsPerAreaManagerRole(supplyShopDTO, currentUser) && isValidZoneManager(supplyShopDTO) && isValidArea(supplyShopDTO) && isValidAreaManager(supplyShopDTO) && isValidSalesRepresentative(supplyShopDTO);
        }
        return isValidZoneManager(supplyShopDTO) && isValidArea(supplyShopDTO) && isValidAreaManager(supplyShopDTO) && isValidSalesRepresentative(supplyShopDTO);
    }

    private boolean isValidZoneAsPerZoneManagerRole(SupplyShopDTO supplyShopDTO, String currentUser) {
        Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
        if (currentEmployee.isPresent()) {
            List<SupplyZoneManager> supplyZoneManagers = supplyZoneManagerExtendedService.getZoneManagers(currentEmployee.get(), SupplyZoneManagerStatus.ACTIVE);
            for (SupplyZoneManager supplyZoneManager : supplyZoneManagers) {
                if (supplyZoneManager.getSupplyZone().getId().equals(supplyShopDTO.getSupplyZoneId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidZoneAsPerAreaManagerRole(SupplyShopDTO supplyShopDTO, String currentUser) {
        Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
        if (currentEmployee.isPresent()) {
            List<SupplyAreaManager> supplyAreaManagers = supplyAreaManagerExtendedService.getAreaManagers(currentEmployee.get(), SupplyAreaManagerStatus.ACTIVE);
            for (SupplyAreaManager supplyAreaManager : supplyAreaManagers) {
                if (supplyAreaManager.getSupplyZone().getId().equals(supplyShopDTO.getSupplyZoneId())
                    && supplyAreaManager.getSupplyZoneManager().getId().equals(supplyShopDTO.getSupplyZoneManagerId())
                    && supplyAreaManager.getSupplyArea().getId().equals(supplyShopDTO.getSupplyAreaId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidZoneManager(SupplyShopDTO supplyShopDTO) {
        Optional<SupplyZoneManagerDTO> selectedZoneManager = supplyZoneManagerExtendedService.findOne(supplyShopDTO.getSupplyZoneManagerId());
        return selectedZoneManager.map(supplyZoneManagerDTO -> supplyZoneManagerDTO.getSupplyZoneId().equals(supplyShopDTO.getSupplyZoneId()) && supplyZoneManagerDTO.getStatus().equals(SupplyZoneManagerStatus.ACTIVE)).orElse(false);
    }

    private boolean isValidArea(SupplyShopDTO supplyShopDTO) {
        Optional<SupplyAreaDTO> selectedArea = supplyAreaExtendedService.findOne(supplyShopDTO.getSupplyAreaId());
        return selectedArea.map(supplyAreaDTO -> supplyAreaDTO.getSupplyZoneId().equals(supplyShopDTO.getSupplyZoneId()) &&
            supplyAreaDTO.getSupplyZoneManagerId().equals(supplyShopDTO.getSupplyZoneManagerId())).orElse(false);
    }

    private boolean isValidAreaManager(SupplyShopDTO supplyShopDTO) {
        Optional<SupplyAreaManagerDTO> selectedAreaManager = supplyAreaManagerExtendedService.findOne(supplyShopDTO.getSupplyAreaManagerId());
        return selectedAreaManager.filter(supplyAreaManagerDTO -> supplyAreaManagerDTO.getSupplyZoneId().equals(supplyShopDTO.getSupplyZoneId()) &&
            supplyAreaManagerDTO.getSupplyZoneManagerId().equals(supplyShopDTO.getSupplyZoneManagerId()) &&
            supplyAreaManagerDTO.getSupplyAreaId().equals(supplyShopDTO.getSupplyAreaId())).isPresent();
    }

    private boolean isValidSalesRepresentative(SupplyShopDTO supplyShopDTO) {
        Optional<SupplySalesRepresentativeDTO> selectedSalesRepresentative = supplySalesRepresentativeExtendedService.findOne(supplyShopDTO.getSupplySalesRepresentativeId());
        return selectedSalesRepresentative.filter(supplyAreaManagerDTO -> supplyAreaManagerDTO.getSupplyZoneId().equals(supplyShopDTO.getSupplyZoneId()) &&
            supplyAreaManagerDTO.getSupplyZoneManagerId().equals(supplyShopDTO.getSupplyZoneManagerId()) &&
            supplyAreaManagerDTO.getSupplyAreaId().equals(supplyShopDTO.getSupplyAreaId()) &&
            supplyAreaManagerDTO.getSupplyAreaManagerId().equals(supplyShopDTO.getSupplyAreaManagerId())).isPresent();
    }
}
