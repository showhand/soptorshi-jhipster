package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.SupplyAreaManager;
import org.soptorshi.domain.SupplyShop;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.extended.SupplyShopExtendedRepository;
import org.soptorshi.repository.search.SupplyShopSearchRepository;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyShopService;
import org.soptorshi.service.dto.SupplyAreaDTO;
import org.soptorshi.service.dto.SupplyAreaManagerDTO;
import org.soptorshi.service.dto.SupplySalesRepresentativeDTO;
import org.soptorshi.service.dto.SupplyShopDTO;
import org.soptorshi.service.mapper.SupplyShopMapper;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
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

        checkZoneValidityForRoleZoneManager(supplyShopDTO, currentUser);
        checkZoneValidityForRoleAreaManager(supplyShopDTO, currentUser);
        checkValidityForArea(supplyShopDTO);
        checkValidityForAreaManager(supplyShopDTO);
        checkValidityForSalesRepresentative(supplyShopDTO);

        SupplyShop supplyShop = supplyShopMapper.toEntity(supplyShopDTO);
        supplyShop = supplyShopExtendedRepository.save(supplyShop);
        SupplyShopDTO result = supplyShopMapper.toDto(supplyShop);
        supplyShopSearchRepository.save(supplyShop);
        return result;
    }

    private void checkZoneValidityForRoleAreaManager(SupplyShopDTO supplyShopDTO, String currentUser) {
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER)) {
            Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
            if(currentEmployee.isPresent()) {
                List<SupplyAreaManager> supplyAreaManagers = supplyAreaManagerExtendedService.getAreaManagers(currentEmployee.get());
                if(supplyAreaManagers.size() > 0) {
                    boolean found = false;
                    for(SupplyAreaManager supplyAreaManager: supplyAreaManagers) {
                        if (supplyAreaManager.getSupplyZone().getId().equals(supplyShopDTO.getSupplyZoneId())) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        throw new BadRequestAlertException("Invalid Zone Selected", "supplyShop", "invalidaccess");
                    }
                }
                else {
                    throw new BadRequestAlertException("Permission Denied", "supplyShop", "invalidaccess");
                }
            }
            else {
                throw new BadRequestAlertException("Permission Denied", "supplyShop", "invalidaccess");
            }
        }
    }

    private void checkZoneValidityForRoleZoneManager(SupplyShopDTO supplyShopDTO, String currentUser) {
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER)) {
            Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
            if(currentEmployee.isPresent()) {
                List<SupplyZoneManager> supplyZoneManagers = supplyZoneManagerExtendedService.getZoneManagers(currentEmployee.get());
                if(supplyZoneManagers.size() > 0) {
                    boolean found = false;
                    for(SupplyZoneManager supplyZoneManager: supplyZoneManagers) {
                        if (supplyZoneManager.getSupplyZone().getId().equals(supplyShopDTO.getSupplyZoneId())) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        throw new BadRequestAlertException("Invalid Zone Selected", "supplyShop", "invalidaccess");
                    }
                }
                else {
                    throw new BadRequestAlertException("Permission Denied", "supplyShop", "invalidaccess");
                }
            }
            else {
                throw new BadRequestAlertException("Permission Denied", "supplyShop", "invalidaccess");
            }
        }
    }

    private void checkValidityForArea(SupplyShopDTO supplyShopDTO) {
        Optional<SupplyAreaDTO> selectedArea = supplyAreaExtendedService.findOne(supplyShopDTO.getSupplyAreaId());
        if(selectedArea.isPresent()) {
            if(!selectedArea.get().getSupplyZoneId().equals(supplyShopDTO.getSupplyZoneId())) {
                throw new BadRequestAlertException("Invalid Area Selected", "supplyShop", "invalidaccess");
            }
        }
        else {
            throw new BadRequestAlertException("Invalid Area Selected", "supplyShop", "invalidaccess");
        }
    }

    private void checkValidityForAreaManager(SupplyShopDTO supplyShopDTO) {
        Optional<SupplyAreaManagerDTO> selectedAreaManager = supplyAreaManagerExtendedService.findOne(supplyShopDTO.getSupplyAreaManagerId());
        if(selectedAreaManager.isPresent()) {
            if(!selectedAreaManager.get().getSupplyZoneId().equals(supplyShopDTO.getSupplyZoneId())) {
                throw new BadRequestAlertException("Invalid Area Manager Selected", "supplyShop", "invalidaccess");
            }
            if(!selectedAreaManager.get().getSupplyAreaId().equals(supplyShopDTO.getSupplyAreaId())) {
                throw new BadRequestAlertException("Invalid Area Manager Selected", "supplyShop", "invalidaccess");
            }
        }
        else {
            throw new BadRequestAlertException("Invalid Area Selected", "supplyShop", "invalidaccess");
        }
    }

    private void checkValidityForSalesRepresentative(SupplyShopDTO supplyShopDTO) {
        Optional<SupplySalesRepresentativeDTO> supplySalesRepresentative = supplySalesRepresentativeExtendedService.findOne(supplyShopDTO.getSupplySalesRepresentativeId());
        if(supplySalesRepresentative.isPresent()) {
            if(!supplySalesRepresentative.get().getSupplyZoneId().equals(supplyShopDTO.getSupplyZoneId())) {
                throw new BadRequestAlertException("Invalid Sales Representative Selected", "supplyShop", "invalidaccess");
            }
            if(!supplySalesRepresentative.get().getSupplyAreaId().equals(supplyShopDTO.getSupplyAreaId())) {
                throw new BadRequestAlertException("nvalid Sales Representative Selected", "supplyShop", "invalidaccess");
            }
            if(!supplySalesRepresentative.get().getSupplyAreaManagerId().equals(supplyShopDTO.getSupplyAreaManagerId())) {
                throw new BadRequestAlertException("nvalid Sales Representative Selected", "supplyShop", "invalidaccess");
            }
        }
        else {
            throw new BadRequestAlertException("Invalid Area Selected", "supplyShop", "invalidaccess");
        }
    }
}
