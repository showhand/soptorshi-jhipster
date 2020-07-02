package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyOrder;
import org.soptorshi.domain.enumeration.SupplyOrderStatus;
import org.soptorshi.repository.extended.SupplyOrderExtendedRepository;
import org.soptorshi.repository.search.SupplyOrderSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyOrderService;
import org.soptorshi.service.dto.SupplyOrderDTO;
import org.soptorshi.service.mapper.SupplyOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public SupplyOrderExtendedService(SupplyOrderExtendedRepository supplyOrderExtendedRepository, SupplyOrderMapper supplyOrderMapper, SupplyOrderSearchRepository supplyOrderSearchRepository) {
        super(supplyOrderExtendedRepository, supplyOrderMapper, supplyOrderSearchRepository);
        this.supplyOrderExtendedRepository = supplyOrderExtendedRepository;
        this.supplyOrderMapper = supplyOrderMapper;
        this.supplyOrderSearchRepository = supplyOrderSearchRepository;
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
            supplyOrderDTO.setStatus(SupplyOrderStatus.ORDER_RECEIVED);
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

    public List<LocalDate> getAllDistinctSupplyOrderDate() {
        log.debug("Request to get all Distinct Supply Order Date");
        List<LocalDate> dates = new ArrayList<>();
        List<SupplyOrder> supplyOrders = supplyOrderExtendedRepository.findAll();
        for (SupplyOrder supplyOrder : supplyOrders) {
            dates.add(supplyOrder.getDateOfOrder());
        }
        return dates.stream().distinct().collect(Collectors.toList());
    }

    public Long updateReferenceNoAfterFilterByDate(String referenceNo, LocalDate fromDate, LocalDate toDate,
                                                   SupplyOrderStatus status) {
        Optional<List<SupplyOrder>> supplyOrders = supplyOrderExtendedRepository.getByDateOfOrderGreaterThanEqualAndDateOfOrderLessThanEqualAndStatus(fromDate, toDate,
            status);

        if (supplyOrders.isPresent()) {
            for (SupplyOrder supplyOrder : supplyOrders.get()) {
                supplyOrder.setAreaWiseAccumulationRefNo(referenceNo);
                supplyOrder.setStatus(SupplyOrderStatus.PROCESSING_ORDER);
                SupplyOrderDTO supplyOrderDTO = supplyOrderMapper.toDto(supplyOrder);
                save(supplyOrderDTO);
            }
            return 1L;
        } else {
            return 0L;
        }
    }
}
