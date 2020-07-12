package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.*;
import org.soptorshi.domain.enumeration.SupplyAreaManagerStatus;
import org.soptorshi.domain.enumeration.SupplyOrderStatus;
import org.soptorshi.domain.enumeration.SupplyZoneManagerStatus;
import org.soptorshi.repository.SupplyMoneyCollectionRepository;
import org.soptorshi.repository.extended.EmployeeExtendedRepository;
import org.soptorshi.repository.search.SupplyMoneyCollectionSearchRepository;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.dto.*;
import org.soptorshi.service.extended.*;
import org.soptorshi.service.mapper.SupplyMoneyCollectionMapper;
import org.soptorshi.service.mapper.SupplyOrderMapper;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing SupplyMoneyCollection.
 */
@Service
@Transactional
public class SupplyMoneyCollectionService {

    private final Logger log = LoggerFactory.getLogger(SupplyMoneyCollectionService.class);

    private final SupplyMoneyCollectionRepository supplyMoneyCollectionRepository;

    private final SupplyMoneyCollectionMapper supplyMoneyCollectionMapper;

    private final SupplyMoneyCollectionSearchRepository supplyMoneyCollectionSearchRepository;

    private final SupplyZoneManagerExtendedService supplyZoneManagerExtendedService;

    private final SupplyAreaManagerExtendedService supplyAreaManagerExtendedService;

    private final SupplySalesRepresentativeExtendedService supplySalesRepresentativeExtendedService;

    private final SupplyShopExtendedService supplyShopExtendedService;

    private final SupplyAreaExtendedService supplyAreaExtendedService;

    private final EmployeeExtendedRepository employeeExtendedRepository;

    private final SupplyOrderExtendedService supplyOrderExtendedService;

    private final SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService;

    private final SupplyOrderMapper supplyOrderMapper;

    public SupplyMoneyCollectionService(SupplyMoneyCollectionRepository supplyMoneyCollectionRepository, SupplyMoneyCollectionMapper supplyMoneyCollectionMapper, SupplyMoneyCollectionSearchRepository supplyMoneyCollectionSearchRepository,
                                        SupplyZoneManagerExtendedService supplyZoneManagerExtendedService, SupplyAreaManagerExtendedService supplyAreaManagerExtendedService, SupplySalesRepresentativeExtendedService supplySalesRepresentativeExtendedService, SupplyShopExtendedService supplyShopExtendedService, EmployeeExtendedRepository employeeExtendedRepository, SupplyAreaExtendedService supplyAreaExtendedService, SupplyOrderExtendedService supplyOrderExtendedService, SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService,
                                        SupplyOrderMapper supplyOrderMapper) {
        this.supplyMoneyCollectionRepository = supplyMoneyCollectionRepository;
        this.supplyMoneyCollectionMapper = supplyMoneyCollectionMapper;
        this.supplyMoneyCollectionSearchRepository = supplyMoneyCollectionSearchRepository;
        this.supplyZoneManagerExtendedService = supplyZoneManagerExtendedService;
        this.supplyAreaManagerExtendedService = supplyAreaManagerExtendedService;
        this.supplySalesRepresentativeExtendedService = supplySalesRepresentativeExtendedService;
        this.supplyShopExtendedService = supplyShopExtendedService;
        this.supplyAreaExtendedService = supplyAreaExtendedService;
        this.employeeExtendedRepository = employeeExtendedRepository;
        this.supplyOrderExtendedService = supplyOrderExtendedService;
        this.supplyOrderDetailsExtendedService = supplyOrderDetailsExtendedService;
        this.supplyOrderMapper = supplyOrderMapper;
    }

    /**
     * Save a supplyMoneyCollection.
     *
     * @param supplyMoneyCollectionDTO the entity to save
     * @return the persisted entity
     */
    public SupplyMoneyCollectionDTO save(SupplyMoneyCollectionDTO supplyMoneyCollectionDTO) {
        log.debug("Request to save SupplyMoneyCollection : {}", supplyMoneyCollectionDTO);
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if(supplyMoneyCollectionDTO.getId() == null) {
            supplyMoneyCollectionDTO.setCreatedBy(currentUser);
            supplyMoneyCollectionDTO.setCreatedOn(currentDateTime);
        }
        else {
            supplyMoneyCollectionDTO.setUpdatedBy(currentUser);
            supplyMoneyCollectionDTO.setUpdatedOn(currentDateTime);
        }
        double amount = 0;
        Optional<SupplyOrderDTO> supplyOrder = supplyOrderExtendedService.findOne(supplyMoneyCollectionDTO.getSupplyOrderId());
        if(supplyOrder.isPresent()) {
            List<SupplyOrderDetails> supplyOrderDetails = supplyOrderDetailsExtendedService.getAllBySupplyOrder(supplyOrderMapper.toEntity(supplyOrder.get()));
            for(SupplyOrderDetails supplyOrderDetails1: supplyOrderDetails) {
                amount += supplyOrderDetails1.getPrice().doubleValue();
            }
        }


        if(supplyMoneyCollectionDTO.getReceivedAmount().equals(amount)) {
            supplyOrder.get().setStatus(SupplyOrderStatus.ORDER_CLOSE);
            supplyOrderExtendedService.save(supplyOrder.get());
            SupplyMoneyCollection supplyMoneyCollection = supplyMoneyCollectionMapper.toEntity(supplyMoneyCollectionDTO);
            supplyMoneyCollection = supplyMoneyCollectionRepository.save(supplyMoneyCollection);
            SupplyMoneyCollectionDTO result = supplyMoneyCollectionMapper.toDto(supplyMoneyCollection);
            //supplyMoneyCollectionSearchRepository.save(supplyMoneyCollection);
            return result;
        }
        else {
            throw new BadRequestAlertException("Amount Mismatch!!", "SupplyMoneyCollection", "idnull");
        }

    }

    /**
     * Get all the supplyMoneyCollections.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyMoneyCollectionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplyMoneyCollections");
        return supplyMoneyCollectionRepository.findAll(pageable)
            .map(supplyMoneyCollectionMapper::toDto);
    }


    /**
     * Get one supplyMoneyCollection by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SupplyMoneyCollectionDTO> findOne(Long id) {
        log.debug("Request to get SupplyMoneyCollection : {}", id);
        return supplyMoneyCollectionRepository.findById(id)
            .map(supplyMoneyCollectionMapper::toDto);
    }

    /**
     * Delete the supplyMoneyCollection by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SupplyMoneyCollection : {}", id);
        supplyMoneyCollectionRepository.deleteById(id);
        supplyMoneyCollectionSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplyMoneyCollection corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyMoneyCollectionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupplyMoneyCollections for query {}", query);
        return supplyMoneyCollectionSearchRepository.search(queryStringQuery(query), pageable)
            .map(supplyMoneyCollectionMapper::toDto);
    }

    public boolean isValidInput(SupplyChallanDTO supplyChallanDTO) {
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_ZONE_MANAGER)) {
            return isValidZoneAsPerZoneManagerRole(supplyChallanDTO, currentUser) && isValidZoneManager(supplyChallanDTO) && isValidArea(supplyChallanDTO) && isValidAreaManager(supplyChallanDTO) && isValidSalesRepresentative(supplyChallanDTO) && isValidShop(supplyChallanDTO) && isValidStatus(supplyChallanDTO);
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.SCM_AREA_MANAGER)) {
            return isValidZoneAsPerAreaManagerRole(supplyChallanDTO, currentUser) && isValidZoneManager(supplyChallanDTO) && isValidArea(supplyChallanDTO) && isValidAreaManager(supplyChallanDTO) && isValidSalesRepresentative(supplyChallanDTO) && isValidShop(supplyChallanDTO) && isValidStatus(supplyChallanDTO);
        }
        return isValidZoneManager(supplyChallanDTO) && isValidArea(supplyChallanDTO) && isValidAreaManager(supplyChallanDTO) && isValidSalesRepresentative(supplyChallanDTO) && isValidShop(supplyChallanDTO) && isValidStatus(supplyChallanDTO);
    }

    private boolean isValidZoneAsPerZoneManagerRole(SupplyChallanDTO supplyChallanDTO, String currentUser) {
        Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
        if (currentEmployee.isPresent()) {
            List<SupplyZoneManager> supplyZoneManagers = supplyZoneManagerExtendedService.getZoneManagers(currentEmployee.get(), SupplyZoneManagerStatus.ACTIVE);
            for (SupplyZoneManager supplyZoneManager : supplyZoneManagers) {
                if (supplyZoneManager.getSupplyZone().getId().equals(supplyChallanDTO.getSupplyZoneId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidZoneAsPerAreaManagerRole(SupplyChallanDTO supplyChallanDTO, String currentUser) {
        Optional<Employee> currentEmployee = employeeExtendedRepository.findByEmployeeId(currentUser);
        if (currentEmployee.isPresent()) {
            List<SupplyAreaManager> supplyAreaManagers = supplyAreaManagerExtendedService.getAreaManagers(currentEmployee.get(), SupplyAreaManagerStatus.ACTIVE);
            for (SupplyAreaManager supplyAreaManager : supplyAreaManagers) {
                if (supplyAreaManager.getSupplyZone().getId().equals(supplyChallanDTO.getSupplyZoneId())
                    && supplyAreaManager.getSupplyZoneManager().getId().equals(supplyChallanDTO.getSupplyZoneManagerId())
                    && supplyAreaManager.getSupplyArea().getId().equals(supplyChallanDTO.getSupplyAreaId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidZoneManager(SupplyChallanDTO supplyChallanDTO) {
        Optional<SupplyZoneManagerDTO> selectedZoneManager = supplyZoneManagerExtendedService.findOne(supplyChallanDTO.getSupplyZoneManagerId());
        return selectedZoneManager.map(supplyZoneManagerDTO -> supplyZoneManagerDTO.getSupplyZoneId().equals(supplyChallanDTO.getSupplyZoneId()) && supplyZoneManagerDTO.getStatus().equals(SupplyZoneManagerStatus.ACTIVE)).orElse(false);
    }

    private boolean isValidArea(SupplyChallanDTO supplyChallanDTO) {
        Optional<SupplyAreaDTO> selectedArea = supplyAreaExtendedService.findOne(supplyChallanDTO.getSupplyAreaId());
        return selectedArea.map(supplyAreaDTO -> supplyAreaDTO.getSupplyZoneId().equals(supplyChallanDTO.getSupplyZoneId()) &&
            supplyAreaDTO.getSupplyZoneManagerId().equals(supplyChallanDTO.getSupplyZoneManagerId())).orElse(false);
    }

    private boolean isValidAreaManager(SupplyChallanDTO supplyChallanDTO) {
        Optional<SupplyAreaManagerDTO> selectedAreaManager = supplyAreaManagerExtendedService.findOne(supplyChallanDTO.getSupplyAreaManagerId());
        return selectedAreaManager.filter(supplyAreaManagerDTO -> supplyAreaManagerDTO.getSupplyZoneId().equals(supplyChallanDTO.getSupplyZoneId()) &&
            supplyAreaManagerDTO.getSupplyZoneManagerId().equals(supplyChallanDTO.getSupplyZoneManagerId()) &&
            supplyAreaManagerDTO.getSupplyAreaId().equals(supplyChallanDTO.getSupplyAreaId())).isPresent();
    }

    private boolean isValidSalesRepresentative(SupplyChallanDTO supplyChallanDTO) {
        Optional<SupplySalesRepresentativeDTO> selectedSalesRepresentative = supplySalesRepresentativeExtendedService.findOne(supplyChallanDTO.getSupplySalesRepresentativeId());
        return selectedSalesRepresentative.filter(supplyAreaManagerDTO -> supplyAreaManagerDTO.getSupplyZoneId().equals(supplyChallanDTO.getSupplyZoneId()) &&
            supplyAreaManagerDTO.getSupplyZoneManagerId().equals(supplyChallanDTO.getSupplyZoneManagerId()) &&
            supplyAreaManagerDTO.getSupplyAreaId().equals(supplyChallanDTO.getSupplyAreaId()) &&
            supplyAreaManagerDTO.getSupplyAreaManagerId().equals(supplyChallanDTO.getSupplyAreaManagerId())).isPresent();
    }

    private boolean isValidShop(SupplyChallanDTO supplyChallanDTO) {
        Optional<SupplyShopDTO> selectedSupplyShop = supplyShopExtendedService.findOne(supplyChallanDTO.getSupplyShopId());
        return selectedSupplyShop.filter(supplyAreaManagerDTO -> supplyAreaManagerDTO.getSupplyZoneId().equals(supplyChallanDTO.getSupplyZoneId()) &&
            supplyAreaManagerDTO.getSupplyZoneManagerId().equals(supplyChallanDTO.getSupplyZoneManagerId()) &&
            supplyAreaManagerDTO.getSupplyAreaId().equals(supplyChallanDTO.getSupplyAreaId()) &&
            supplyAreaManagerDTO.getSupplyAreaManagerId().equals(supplyChallanDTO.getSupplyAreaManagerId()) &&
            supplyAreaManagerDTO.getSupplySalesRepresentativeId().equals(supplyChallanDTO.getSupplySalesRepresentativeId())).isPresent();
    }

    public boolean isValidStatus(SupplyChallanDTO supplyChallanDTO) {
        Optional<SupplyOrderDTO> supplyOrderDTO1 = supplyOrderExtendedService.findOne(supplyChallanDTO.getSupplyOrderId());
        return supplyOrderDTO1.map(orderDTO -> orderDTO.getStatus().equals(SupplyOrderStatus.ORDER_DELIVERED_AND_WAITING_FOR_MONEY_COLLECTION)).orElse(false);
    }
}
