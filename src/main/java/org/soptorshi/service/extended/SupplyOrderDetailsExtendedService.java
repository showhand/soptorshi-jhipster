package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyOrder;
import org.soptorshi.domain.SupplyOrderDetails;
import org.soptorshi.repository.extended.SupplyOrderDetailsExtendedRepository;
import org.soptorshi.repository.search.SupplyOrderDetailsSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyOrderDetailsService;
import org.soptorshi.service.dto.SupplyOrderDTO;
import org.soptorshi.service.dto.SupplyOrderDetailsDTO;
import org.soptorshi.service.mapper.SupplyOrderDetailsMapper;
import org.soptorshi.service.mapper.SupplyOrderMapper;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    private SupplyOrderDetailsExtendedRepository supplyOrderDetailsExtendedRepository;

    private final SupplyOrderDetailsMapper supplyOrderDetailsMapper;

    private final SupplyOrderDetailsSearchRepository supplyOrderDetailsSearchRepository;

    private final SupplyOrderExtendedService supplyOrderExtendedService;

    private final SupplyOrderMapper supplyOrderMapper;

    public SupplyOrderDetailsExtendedService(SupplyOrderDetailsExtendedRepository supplyOrderDetailsExtendedRepository, SupplyOrderDetailsMapper supplyOrderDetailsMapper, SupplyOrderDetailsSearchRepository supplyOrderDetailsSearchRepository, SupplyOrderExtendedService supplyOrderExtendedService,
                                             SupplyOrderMapper supplyOrderMapper) {
       super(supplyOrderDetailsExtendedRepository, supplyOrderDetailsMapper, supplyOrderDetailsSearchRepository);
       this.supplyOrderDetailsExtendedRepository = supplyOrderDetailsExtendedRepository;
        this.supplyOrderDetailsMapper = supplyOrderDetailsMapper;
        this.supplyOrderDetailsSearchRepository = supplyOrderDetailsSearchRepository;
        this.supplyOrderExtendedService = supplyOrderExtendedService;
        this.supplyOrderMapper = supplyOrderMapper;
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
        supplyOrderDetailsSearchRepository.save(supplyOrderDetails);
        updateSupplyOrderOfferedPrice(supplyOrderDetailsDTO);
        return result;
    }

    private void updateSupplyOrderOfferedPrice(SupplyOrderDetailsDTO supplyOrderDetailsDTO) {
        Optional<SupplyOrderDTO> supplyOrderDTO = supplyOrderExtendedService.findOne(supplyOrderDetailsDTO.getSupplyOrderId());
        if(supplyOrderDTO.isPresent()) {
            SupplyOrder supplyOrder = supplyOrderMapper.toEntity(supplyOrderDTO.get());
            Optional<List<SupplyOrderDetails>> supplyOrderDetails = getAllBySupplyOrder(supplyOrder);
            BigDecimal offeredPrice = BigDecimal.ZERO;
            if(supplyOrderDetails.isPresent()) {
                for(SupplyOrderDetails supplyOrderDetails1: supplyOrderDetails.get()) {
                    if(supplyOrderDetails1.getOfferedPrice() != null) {
                        offeredPrice = offeredPrice.add(supplyOrderDetails1.getOfferedPrice());
                    }
                }

                supplyOrder.setOfferAmount(offeredPrice);
                supplyOrderExtendedService.save(supplyOrderMapper.toDto(supplyOrder));
            }
        }
        else {
            throw new BadRequestAlertException("Permission Denied", "supplyOrderDetails", "invalidaccess");
        }
    }

    Optional<List<SupplyOrderDetails>> getAllBySupplyOrder(SupplyOrder supplyOrder) {
        return supplyOrderDetailsExtendedRepository.getAllBySupplyOrder(supplyOrder);
    }
}
