package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyOrder;
import org.soptorshi.domain.SupplyOrderDetails;
import org.soptorshi.domain.enumeration.SupplyOrderStatus;
import org.soptorshi.repository.extended.SupplyOrderDetailsExtendedRepository;
import org.soptorshi.repository.search.SupplyOrderDetailsSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyOrderDetailsService;
import org.soptorshi.service.dto.SupplyOrderDTO;
import org.soptorshi.service.dto.SupplyOrderDetailsDTO;
import org.soptorshi.service.mapper.SupplyOrderDetailsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SupplyOrderDetails.
 */
@Service
@Transactional
public class SupplyOrderDetailsExtendedService extends SupplyOrderDetailsService {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderDetailsExtendedService.class);

    private final SupplyOrderDetailsExtendedRepository supplyOrderDetailsExtendedRepository;

    private final SupplyOrderDetailsMapper supplyOrderDetailsMapper;

    private final SupplyOrderDetailsSearchRepository supplyOrderDetailsSearchRepository;

    private final SupplyOrderExtendedService supplyOrderExtendedService;

    public SupplyOrderDetailsExtendedService(SupplyOrderDetailsExtendedRepository supplyOrderDetailsExtendedRepository, SupplyOrderDetailsMapper supplyOrderDetailsMapper, SupplyOrderDetailsSearchRepository supplyOrderDetailsSearchRepository, SupplyOrderExtendedService supplyOrderExtendedService) {
       super(supplyOrderDetailsExtendedRepository, supplyOrderDetailsMapper, supplyOrderDetailsSearchRepository);
       this.supplyOrderDetailsExtendedRepository = supplyOrderDetailsExtendedRepository;
        this.supplyOrderDetailsMapper = supplyOrderDetailsMapper;
        this.supplyOrderDetailsSearchRepository = supplyOrderDetailsSearchRepository;
        this.supplyOrderExtendedService = supplyOrderExtendedService;
    }

    @Transactional
    public SupplyOrderDetailsDTO save(SupplyOrderDetailsDTO supplyOrderDetailsDTO) {
        log.debug("Request to save SupplyOrderDetails : {}", supplyOrderDetailsDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if(supplyOrderDetailsDTO.getId() == null) {
            supplyOrderDetailsDTO.setCreatedBy(currentUser);
            supplyOrderDetailsDTO.setCreatedOn(currentDateTime);
        }
        else {
            supplyOrderDetailsDTO.setUpdatedBy(currentUser);
            supplyOrderDetailsDTO.setUpdatedOn(currentDateTime);
        }

        SupplyOrderDetails supplyOrderDetails = supplyOrderDetailsMapper.toEntity(supplyOrderDetailsDTO);
        supplyOrderDetails = supplyOrderDetailsExtendedRepository.save(supplyOrderDetails);
        SupplyOrderDetailsDTO result = supplyOrderDetailsMapper.toDto(supplyOrderDetails);
        //supplyOrderDetailsSearchRepository.save(supplyOrderDetails);
        return result;
    }

    public List<SupplyOrderDetails> getAllBySupplyOrder(SupplyOrder supplyOrder) {
        return supplyOrderDetailsExtendedRepository.getAllBySupplyOrder(supplyOrder);
    }

    public boolean isValidSupplyOrderStatus(SupplyOrderDetailsDTO supplyOrderDetailsDTO) {
        Optional<SupplyOrderDTO> supplyOrderDTO = supplyOrderExtendedService.findOne(supplyOrderDetailsDTO.getSupplyOrderId());
        return supplyOrderDTO.map(orderDTO -> orderDTO.getStatus().equals(SupplyOrderStatus.ORDER_RECEIVED)).orElse(false);
    }
}
