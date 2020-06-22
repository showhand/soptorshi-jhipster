package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyArea;
import org.soptorshi.repository.extended.SupplyAreaExtendedRepository;
import org.soptorshi.repository.search.SupplyAreaSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyAreaService;
import org.soptorshi.service.dto.SupplyAreaDTO;
import org.soptorshi.service.mapper.SupplyAreaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Service Implementation for managing SupplyArea.
 */
@Service
@Transactional
public class SupplyAreaExtendedService extends SupplyAreaService {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaExtendedService.class);

    private final SupplyAreaExtendedRepository supplyAreaExtendedRepository;

    private final SupplyAreaMapper supplyAreaMapper;

    private final SupplyAreaSearchRepository supplyAreaSearchRepository;

    public SupplyAreaExtendedService(SupplyAreaExtendedRepository supplyAreaExtendedRepository, SupplyAreaMapper supplyAreaMapper, SupplyAreaSearchRepository supplyAreaSearchRepository) {
        super(supplyAreaExtendedRepository, supplyAreaMapper, supplyAreaSearchRepository);
        this.supplyAreaExtendedRepository = supplyAreaExtendedRepository;
        this.supplyAreaMapper = supplyAreaMapper;
        this.supplyAreaSearchRepository = supplyAreaSearchRepository;
    }

    public SupplyAreaDTO save(SupplyAreaDTO supplyAreaDTO) {
        log.debug("Request to save SupplyArea : {}", supplyAreaDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if(supplyAreaDTO.getId() == null) {
            supplyAreaDTO.setCreatedBy(currentUser);
            supplyAreaDTO.setCreatedOn(currentDateTime);
        }
        else {
            supplyAreaDTO.setUpdatedBy(currentUser);
            supplyAreaDTO.setUpdatedOn(currentDateTime);
        }

        SupplyArea supplyArea = supplyAreaMapper.toEntity(supplyAreaDTO);
        supplyArea = supplyAreaExtendedRepository.save(supplyArea);
        SupplyAreaDTO result = supplyAreaMapper.toDto(supplyArea);
        supplyAreaSearchRepository.save(supplyArea);
        return result;
    }
}
