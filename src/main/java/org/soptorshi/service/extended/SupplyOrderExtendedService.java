package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyOrder;
import org.soptorshi.domain.SupplyOrderDetails;
import org.soptorshi.repository.extended.SupplyOrderExtendedRepository;
import org.soptorshi.repository.search.SupplyOrderSearchRepository;
import org.soptorshi.service.SupplyOrderService;
import org.soptorshi.service.dto.SupplyOrderDTO;
import org.soptorshi.service.mapper.SupplyOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    private final SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService;

    public SupplyOrderExtendedService(SupplyOrderExtendedRepository supplyOrderExtendedRepository, SupplyOrderMapper supplyOrderMapper, SupplyOrderSearchRepository supplyOrderSearchRepository, SupplyOrderDetailsExtendedService supplyOrderDetailsExtendedService) {
        super(supplyOrderExtendedRepository, supplyOrderMapper, supplyOrderSearchRepository);
        this.supplyOrderExtendedRepository = supplyOrderExtendedRepository;
        this.supplyOrderMapper = supplyOrderMapper;
        this.supplyOrderSearchRepository = supplyOrderSearchRepository;
        this.supplyOrderDetailsExtendedService = supplyOrderDetailsExtendedService;
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

        SupplyOrder supplyOrder = supplyOrderMapper.toEntity(supplyOrderDTO);


        if(supplyOrderDTO.getId() != null) {
            Optional<List<SupplyOrderDetails>> supplyOrderDetails = supplyOrderDetailsExtendedService.getAllBySupplyOrder(supplyOrder);

            if(supplyOrderDetails.isPresent()) {
                BigDecimal total = BigDecimal.ZERO;

                for(SupplyOrderDetails supplyOrderDetail: supplyOrderDetails.get()) {
                       total = total.add(supplyOrderDetail.getOfferedPrice());
                }

                supplyOrder.setOfferAmount(total);
            }
        }


        supplyOrder = supplyOrderExtendedRepository.save(supplyOrder);
        SupplyOrderDTO result = supplyOrderMapper.toDto(supplyOrder);
        supplyOrderSearchRepository.save(supplyOrder);

        return result;
    }
}
