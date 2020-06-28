package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyZoneWiseAccumulation;
import org.soptorshi.repository.extended.SupplyZoneWiseAccumulationExtendedRepository;
import org.soptorshi.repository.search.SupplyZoneWiseAccumulationSearchRepository;
import org.soptorshi.service.SupplyZoneWiseAccumulationService;
import org.soptorshi.service.dto.SupplyZoneWiseAccumulationDTO;
import org.soptorshi.service.mapper.SupplyZoneWiseAccumulationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public SupplyZoneWiseAccumulationExtendedService(SupplyZoneWiseAccumulationExtendedRepository supplyZoneWiseAccumulationExtendedRepository, SupplyZoneWiseAccumulationMapper supplyZoneWiseAccumulationMapper, SupplyZoneWiseAccumulationSearchRepository supplyZoneWiseAccumulationSearchRepository) {
        super(supplyZoneWiseAccumulationExtendedRepository, supplyZoneWiseAccumulationMapper, supplyZoneWiseAccumulationSearchRepository);
        this.supplyZoneWiseAccumulationExtendedRepository = supplyZoneWiseAccumulationExtendedRepository;
        this.supplyZoneWiseAccumulationMapper = supplyZoneWiseAccumulationMapper;
        this.supplyZoneWiseAccumulationSearchRepository = supplyZoneWiseAccumulationSearchRepository;
    }

    /**
     * Save a supplyZoneWiseAccumulation.
     *
     * @param supplyZoneWiseAccumulationDTO the entity to save
     * @return the persisted entity
     */
    public SupplyZoneWiseAccumulationDTO save(SupplyZoneWiseAccumulationDTO supplyZoneWiseAccumulationDTO) {
        log.debug("Request to save SupplyZoneWiseAccumulation : {}", supplyZoneWiseAccumulationDTO);
        SupplyZoneWiseAccumulation supplyZoneWiseAccumulation = supplyZoneWiseAccumulationMapper.toEntity(supplyZoneWiseAccumulationDTO);
        supplyZoneWiseAccumulation = supplyZoneWiseAccumulationExtendedRepository.save(supplyZoneWiseAccumulation);
        SupplyZoneWiseAccumulationDTO result = supplyZoneWiseAccumulationMapper.toDto(supplyZoneWiseAccumulation);
        supplyZoneWiseAccumulationSearchRepository.save(supplyZoneWiseAccumulation);
        return result;
    }
}
