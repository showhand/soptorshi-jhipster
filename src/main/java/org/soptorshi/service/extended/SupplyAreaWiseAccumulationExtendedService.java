package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyAreaWiseAccumulation;
import org.soptorshi.repository.extended.SupplyAreaWiseAccumulationExtendedRepository;
import org.soptorshi.repository.search.SupplyAreaWiseAccumulationSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyAreaWiseAccumulationService;
import org.soptorshi.service.dto.SupplyAreaWiseAccumulationDTO;
import org.soptorshi.service.mapper.SupplyAreaWiseAccumulationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

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

    public SupplyAreaWiseAccumulationExtendedService(SupplyAreaWiseAccumulationExtendedRepository supplyAreaWiseAccumulationExtendedRepository, SupplyAreaWiseAccumulationMapper supplyAreaWiseAccumulationMapper, SupplyAreaWiseAccumulationSearchRepository supplyAreaWiseAccumulationSearchRepository) {
        super(supplyAreaWiseAccumulationExtendedRepository, supplyAreaWiseAccumulationMapper, supplyAreaWiseAccumulationSearchRepository);
        this.supplyAreaWiseAccumulationExtendedRepository = supplyAreaWiseAccumulationExtendedRepository;
        this.supplyAreaWiseAccumulationMapper = supplyAreaWiseAccumulationMapper;
        this.supplyAreaWiseAccumulationSearchRepository = supplyAreaWiseAccumulationSearchRepository;
    }

    /**
     * Save a supplyAreaWiseAccumulation.
     *
     * @param supplyAreaWiseAccumulationDTO the entity to save
     * @return the persisted entity
     */
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
}
