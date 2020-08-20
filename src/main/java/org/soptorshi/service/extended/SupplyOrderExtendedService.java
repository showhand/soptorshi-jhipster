package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.SupplyAreaManager;
import org.soptorshi.domain.SupplyOrder;
import org.soptorshi.domain.SupplyZoneManager;
import org.soptorshi.domain.enumeration.SupplyAreaManagerStatus;
import org.soptorshi.domain.enumeration.SupplyOrderStatus;
import org.soptorshi.domain.enumeration.SupplyZoneManagerStatus;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.extended.SupplyOrderExtendedRepository;
import org.soptorshi.repository.search.SupplyOrderSearchRepository;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyOrderService;
import org.soptorshi.service.dto.*;
import org.soptorshi.service.mapper.SupplyOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SupplyOrder.
 */
@Service
@Transactional
public class SupplyOrderExtendedService extends SupplyOrderService {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderExtendedService.class);

    private final SupplyOrderExtendedRepository supplyOrderExtendedRepository;

    private final SupplyOrderMapper supplyOrderMapper;

    private final SupplyOrderSearchRepository supplyOrderSearchRepository;

    private final SupplyZoneManagerExtendedService supplyZoneManagerExtendedService;

    private final SupplyAreaManagerExtendedService supplyAreaManagerExtendedService;

    private final SupplySalesRepresentativeExtendedService supplySalesRepresentativeExtendedService;

    private final SupplyShopExtendedService supplyShopExtendedService;

    private final SupplyAreaExtendedService supplyAreaExtendedService;

    private final EmployeeExtendedRepository employeeExtendedRepository;

    public SupplyOrderExtendedService(SupplyOrderExtendedRepository supplyOrderExtendedRepository, SupplyOrderMapper supplyOrderMapper, SupplyOrderSearchRepository supplyOrderSearchRepository, SupplyZoneManagerExtendedService supplyZoneManagerExtendedService, SupplyAreaManagerExtendedService supplyAreaManagerExtendedService, SupplySalesRepresentativeExtendedService supplySalesRepresentativeExtendedService, SupplyShopExtendedService supplyShopExtendedService, EmployeeExtendedRepository employeeExtendedRepository, SupplyAreaExtendedService supplyAreaExtendedService) {
        super(supplyOrderExtendedRepository, supplyOrderMapper, supplyOrderSearchRepository);
        this.supplyOrderExtendedRepository = supplyOrderExtendedRepository;
        this.supplyOrderMapper = supplyOrderMapper;
        this.supplyOrderSearchRepository = supplyOrderSearchRepository;
        this.supplyZoneManagerExtendedService = supplyZoneManagerExtendedService;
        this.supplyAreaManagerExtendedService = supplyAreaManagerExtendedService;
        this.supplySalesRepresentativeExtendedService = supplySalesRepresentativeExtendedService;
        this.supplyShopExtendedService = supplyShopExtendedService;
        this.supplyAreaExtendedService = supplyAreaExtendedService;
        this.employeeExtendedRepository = employeeExtendedRepository;
    }

    /**
     * Save a supplyOrder.
     *
     * @param supplyOrderDTO the entity to save
     * @return the persisted entity
     */
    @Transactional
    public SupplyOrderDTO save(SupplyOrderDTO supplyOrderDTO) {
        log.debug("Request to save SupplyOrder : {}", supplyOrderDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if(supplyOrderDTO.getId() == null) {
            supplyOrderDTO.setCreatedBy(currentUser);
            supplyOrderDTO.setCreatedOn(currentDateTime);
        }
        else {
            supplyOrderDTO.setUpdatedBy(currentUser);
            supplyOrderDTO.setUpdatedOn(currentDateTime);
        }

        SupplyOrder supplyOrder = supplyOrderMapper.toEntity(supplyOrderDTO);
        supplyOrder = supplyOrderExtendedRepository.save(supplyOrder);
        SupplyOrderDTO result = supplyOrderMapper.toDto(supplyOrder);
        //supplyOrderSearchRepository.save(supplyOrder);

        return result;
    }

    public boolean isValidInput(SupplyOrderDTO supplyOrderDTO) {
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER)) {
            return isValidZoneAsPerZoneManagerRole(supplyOrderDTO, currentUser) && isValidZoneManager(supplyOrderDTO) && isValidArea(supplyOrderDTO) && isValidAreaManager(supplyOrderDTO) && isValidSalesRepresentative(supplyOrderDTO) && isValidShop(supplyOrderDTO);
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER)) {
            return isValidZoneAsPerAreaManagerRole(supplyOrderDTO, currentUser) && isValidZoneManager(supplyOrderDTO) && isValidArea(supplyOrderDTO) && isValidAreaManager(supplyOrderDTO) && isValidSalesRepresentative(supplyOrderDTO) && isValidShop(supplyOrderDTO);
        }
        return isValidZoneManager(supplyOrderDTO) && isValidArea(supplyOrderDTO) && isValidAreaManager(supplyOrderDTO) && isValidSalesRepresentative(supplyOrderDTO) && isValidShop(supplyOrderDTO);
    }

    private boolean isValidZoneAsPerZoneManagerRole(SupplyOrderDTO supplyOrderDTO, String currentUser) {
        Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
        if (currentEmployee.isPresent()) {
            List<SupplyZoneManager> supplyZoneManagers = supplyZoneManagerExtendedService.getZoneManagers(currentEmployee.get(), SupplyZoneManagerStatus.ACTIVE);
            for (SupplyZoneManager supplyZoneManager : supplyZoneManagers) {
                if (supplyZoneManager.getSupplyZone().getId().equals(supplyOrderDTO.getSupplyZoneId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidZoneAsPerAreaManagerRole(SupplyOrderDTO supplyOrderDTO, String currentUser) {
        Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
        if (currentEmployee.isPresent()) {
            List<SupplyAreaManager> supplyAreaManagers = supplyAreaManagerExtendedService.getAreaManagers(currentEmployee.get(), SupplyAreaManagerStatus.ACTIVE);
            for (SupplyAreaManager supplyAreaManager : supplyAreaManagers) {
                if (supplyAreaManager.getSupplyZone().getId().equals(supplyOrderDTO.getSupplyZoneId())
                    && supplyAreaManager.getSupplyZoneManager().getId().equals(supplyOrderDTO.getSupplyZoneManagerId())
                    && supplyAreaManager.getSupplyArea().getId().equals(supplyOrderDTO.getSupplyAreaId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidZoneManager(SupplyOrderDTO supplyOrderDTO) {
        Optional<SupplyZoneManagerDTO> selectedZoneManager = supplyZoneManagerExtendedService.findOne(supplyOrderDTO.getSupplyZoneManagerId());
        return selectedZoneManager.map(supplyZoneManagerDTO -> supplyZoneManagerDTO.getSupplyZoneId().equals(supplyOrderDTO.getSupplyZoneId()) && supplyZoneManagerDTO.getStatus().equals(SupplyZoneManagerStatus.ACTIVE)).orElse(false);
    }

    private boolean isValidArea(SupplyOrderDTO supplyOrderDTO) {
        Optional<SupplyAreaDTO> selectedArea = supplyAreaExtendedService.findOne(supplyOrderDTO.getSupplyAreaId());
        return selectedArea.map(supplyAreaDTO -> supplyAreaDTO.getSupplyZoneId().equals(supplyOrderDTO.getSupplyZoneId()) &&
            supplyAreaDTO.getSupplyZoneManagerId().equals(supplyOrderDTO.getSupplyZoneManagerId())).orElse(false);
    }

    private boolean isValidAreaManager(SupplyOrderDTO supplyOrderDTO) {
        Optional<SupplyAreaManagerDTO> selectedAreaManager = supplyAreaManagerExtendedService.findOne(supplyOrderDTO.getSupplyAreaManagerId());
        return selectedAreaManager.filter(supplyAreaManagerDTO -> supplyAreaManagerDTO.getSupplyZoneId().equals(supplyOrderDTO.getSupplyZoneId()) &&
            supplyAreaManagerDTO.getSupplyZoneManagerId().equals(supplyOrderDTO.getSupplyZoneManagerId()) &&
            supplyAreaManagerDTO.getSupplyAreaId().equals(supplyOrderDTO.getSupplyAreaId())).isPresent();
    }

    private boolean isValidSalesRepresentative(SupplyOrderDTO supplyOrderDTO) {
        Optional<SupplySalesRepresentativeDTO> selectedSalesRepresentative = supplySalesRepresentativeExtendedService.findOne(supplyOrderDTO.getSupplySalesRepresentativeId());
        return selectedSalesRepresentative.filter(supplyAreaManagerDTO -> supplyAreaManagerDTO.getSupplyZoneId().equals(supplyOrderDTO.getSupplyZoneId()) &&
            supplyAreaManagerDTO.getSupplyZoneManagerId().equals(supplyOrderDTO.getSupplyZoneManagerId()) &&
            supplyAreaManagerDTO.getSupplyAreaId().equals(supplyOrderDTO.getSupplyAreaId()) &&
            supplyAreaManagerDTO.getSupplyAreaManagerId().equals(supplyOrderDTO.getSupplyAreaManagerId())).isPresent();
    }

    private boolean isValidShop(SupplyOrderDTO supplyOrderDTO) {
        Optional<SupplyShopDTO> selectedSupplyShop = supplyShopExtendedService.findOne(supplyOrderDTO.getSupplyShopId());
        return selectedSupplyShop.filter(supplyAreaManagerDTO -> supplyAreaManagerDTO.getSupplyZoneId().equals(supplyOrderDTO.getSupplyZoneId()) &&
            supplyAreaManagerDTO.getSupplyZoneManagerId().equals(supplyOrderDTO.getSupplyZoneManagerId()) &&
            supplyAreaManagerDTO.getSupplyAreaId().equals(supplyOrderDTO.getSupplyAreaId()) &&
            supplyAreaManagerDTO.getSupplyAreaManagerId().equals(supplyOrderDTO.getSupplyAreaManagerId()) &&
            supplyAreaManagerDTO.getSupplySalesRepresentativeId().equals(supplyOrderDTO.getSupplySalesRepresentativeId())).isPresent();
    }

    public boolean isValidStatus(SupplyOrderDTO supplyOrderDTO) {
        Optional<SupplyOrderDTO> supplyOrderDTO1 = findOne(supplyOrderDTO.getId());
        return supplyOrderDTO1.map(orderDTO -> orderDTO.getStatus().equals(SupplyOrderStatus.ORDER_RECEIVED)).orElse(false);
    }

    public List<SupplyOrder> getAllByAreaWiseAccumulationRefNo(String areaWiseAccumulationRefNo) {
        return supplyOrderExtendedRepository.getByAreaWiseAccumulationRefNo(areaWiseAccumulationRefNo);
    }

    public List<SupplyOrder> getAllByDateOfOrderBeforeAndDateOfOrderAfter(LocalDate from, LocalDate to) {
        return supplyOrderExtendedRepository.getAllByDateOfOrderIsAfterAndDateOfOrderBefore(from, to);
    }
}
