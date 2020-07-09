package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.SupplyAreaWiseAccumulationStatus;
import org.soptorshi.domain.enumeration.SupplyOrderStatus;
import org.soptorshi.domain.enumeration.SupplyZoneManagerStatus;
import org.soptorshi.domain.enumeration.SupplyZoneWiseAccumulationStatus;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.extended.SupplyZoneWiseAccumulationExtendedRepository;
import org.soptorshi.repository.search.SupplyZoneWiseAccumulationSearchRepository;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyZoneWiseAccumulationService;
import org.soptorshi.service.dto.SupplyZoneManagerDTO;
import org.soptorshi.service.dto.SupplyZoneWiseAccumulationDTO;
import org.soptorshi.service.mapper.SupplyAreaWiseAccumulationMapper;
import org.soptorshi.service.mapper.SupplyOrderMapper;
import org.soptorshi.service.mapper.SupplyZoneWiseAccumulationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SupplyZoneWiseAccumulation.
 */
@Service
@Transactional
public class SupplyZoneWiseAccumulationExtendedService extends SupplyZoneWiseAccumulationService {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneWiseAccumulationExtendedService.class);

    private final SupplyZoneWiseAccumulationExtendedRepository supplyZoneWiseAccumulationExtendedRepository;

    private final SupplyZoneWiseAccumulationMapper supplyZoneWiseAccumulationMapper;

    private final SupplyZoneWiseAccumulationSearchRepository supplyZoneWiseAccumulationSearchRepository;

    private final SupplyZoneManagerExtendedService supplyZoneManagerExtendedService;

    private final EmployeeExtendedRepository employeeExtendedRepository;

    private final SupplyAreaWiseAccumulationExtendedService supplyAreaWiseAccumulationExtendedService;

    private final SupplyOrderExtendedService supplyOrderExtendedService;

    private final SupplyOrderMapper supplyOrderMapper;

    private final SupplyAreaWiseAccumulationMapper supplyAreaWiseAccumulationMapper;

    public SupplyZoneWiseAccumulationExtendedService(SupplyZoneWiseAccumulationExtendedRepository supplyZoneWiseAccumulationExtendedRepository, SupplyZoneWiseAccumulationMapper supplyZoneWiseAccumulationMapper, SupplyZoneWiseAccumulationSearchRepository supplyZoneWiseAccumulationSearchRepository,
                                                     SupplyZoneManagerExtendedService supplyZoneManagerExtendedService, EmployeeExtendedRepository employeeExtendedRepository, SupplyAreaWiseAccumulationExtendedService supplyAreaWiseAccumulationExtendedService, SupplyOrderExtendedService supplyOrderExtendedService, SupplyOrderMapper supplyOrderMapper, SupplyAreaWiseAccumulationMapper supplyAreaWiseAccumulationMapper) {
        super(supplyZoneWiseAccumulationExtendedRepository, supplyZoneWiseAccumulationMapper, supplyZoneWiseAccumulationSearchRepository);
        this.supplyZoneWiseAccumulationExtendedRepository = supplyZoneWiseAccumulationExtendedRepository;
        this.supplyZoneWiseAccumulationMapper = supplyZoneWiseAccumulationMapper;
        this.supplyZoneWiseAccumulationSearchRepository = supplyZoneWiseAccumulationSearchRepository;
        this.supplyZoneManagerExtendedService = supplyZoneManagerExtendedService;
        this.employeeExtendedRepository = employeeExtendedRepository;
        this.supplyAreaWiseAccumulationExtendedService = supplyAreaWiseAccumulationExtendedService;
        this.supplyOrderExtendedService = supplyOrderExtendedService;
        this.supplyOrderMapper = supplyOrderMapper;
        this.supplyAreaWiseAccumulationMapper = supplyAreaWiseAccumulationMapper;
    }

    public SupplyZoneWiseAccumulationDTO save(SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO) {
        log.debug("Request to save SupplyZoneWiseAccumulation : {}", supplyZoneWiseAccumulationDTO);
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if(supplyZoneWiseAccumulationDTO.getId() == null) {
            supplyZoneWiseAccumulationDTO.setCreatedBy(currentUser);
            supplyZoneWiseAccumulationDTO.setCreatedOn(currentDateTime);
        }
        else {
            supplyZoneWiseAccumulationDTO.setUpdatedBy(currentUser);
            supplyZoneWiseAccumulationDTO.setUpdatedOn(currentDateTime);
        }
        SupplyZoneWiseAccumulation supplyZoneWiseAccumulation = supplyZoneWiseAccumulationMapper.toEntity(supplyZoneWiseAccumulationDTO);

        if(supplyZoneWiseAccumulation.getStatus().equals(SupplyZoneWiseAccumulationStatus.APPROVED)) {
            List<SupplyAreaWiseAccumulation> supplyAreaWiseAccumulations = supplyAreaWiseAccumulationExtendedService.getAllByZoneWiseAccumulationRefNo(supplyZoneWiseAccumulation.getZoneWiseAccumulationRefNo());

            for(SupplyAreaWiseAccumulation supplyAreaWiseAccumulation: supplyAreaWiseAccumulations) {
                List<SupplyOrder> supplyOrders = supplyOrderExtendedService.getAllByAreaWiseAccumulationRefNo(supplyAreaWiseAccumulation.getAreaWiseAccumulationRefNo());
                for(SupplyOrder supplyOrder: supplyOrders) {
                    supplyOrder.setStatus(SupplyOrderStatus.ORDER_DELIVERED_AND_WAITING_FOR_MONEY_COLLECTION);
                    supplyOrderExtendedService.save(supplyOrderMapper.toDto(supplyOrder));
                }

                supplyAreaWiseAccumulation.setStatus(SupplyAreaWiseAccumulationStatus.APPROVED);
                supplyAreaWiseAccumulationExtendedService.save(supplyAreaWiseAccumulationMapper.toDto(supplyAreaWiseAccumulation));
            }
        }

        supplyZoneWiseAccumulation = supplyZoneWiseAccumulationExtendedRepository.save(supplyZoneWiseAccumulation);
        SupplyZoneWiseAccumulationDTO result = supplyZoneWiseAccumulationMapper.toDto(supplyZoneWiseAccumulation);
        supplyZoneWiseAccumulationSearchRepository.save(supplyZoneWiseAccumulation);
        return result;
    }

    public boolean isValidInput(SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO) {
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER)) {
            return isValidZoneAsPerZoneManagerRole(supplyZoneWiseAccumulationDTO, currentUser) && isValidZoneManager(supplyZoneWiseAccumulationDTO);
        }
        return isValidZoneManager(supplyZoneWiseAccumulationDTO);
    }

    private boolean isValidZoneAsPerZoneManagerRole(SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO, String currentUser) {
        Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
        if (currentEmployee.isPresent()) {
            List<SupplyZoneManager> supplyZoneManagers = supplyZoneManagerExtendedService.getZoneManagers(currentEmployee.get(), SupplyZoneManagerStatus.ACTIVE);
            for (SupplyZoneManager supplyZoneManager : supplyZoneManagers) {
                if (supplyZoneManager.getSupplyZone().getId().equals(supplyZoneWiseAccumulationDTO.getSupplyZoneId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidZoneManager(SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO) {
        Optional<SupplyZoneManagerDTO> selectedZoneManager = supplyZoneManagerExtendedService.findOne(supplyZoneWiseAccumulationDTO.getSupplyZoneManagerId());
        return selectedZoneManager.map(supplyZoneManagerDTO -> supplyZoneManagerDTO.getSupplyZoneId().equals(supplyZoneWiseAccumulationDTO.getSupplyZoneId()) && supplyZoneManagerDTO.getStatus().equals(SupplyZoneManagerStatus.ACTIVE)).orElse(false);
    }
}
