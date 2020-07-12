package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyZone;
import org.soptorshi.repository.extended.SupplyZoneExtendedRepository;
import org.soptorshi.repository.search.SupplyZoneSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyZoneService;
import org.soptorshi.service.dto.SupplyZoneDTO;
import org.soptorshi.service.mapper.SupplyZoneMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * Service Implementation for managing SupplyZone.
 */
@Service
@Transactional
public class SupplyZoneExtendedService extends SupplyZoneService {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneExtendedService.class);

    private final SupplyZoneExtendedRepository supplyZoneExtendedRepository;

    private final SupplyZoneMapper supplyZoneMapper;

    private final SupplyZoneSearchRepository supplyZoneSearchRepository;

    public SupplyZoneExtendedService(SupplyZoneExtendedRepository supplyZoneExtendedRepository, SupplyZoneMapper supplyZoneMapper, SupplyZoneSearchRepository supplyZoneSearchRepository) {
        super(supplyZoneExtendedRepository, supplyZoneMapper, supplyZoneSearchRepository);

        this.supplyZoneExtendedRepository = supplyZoneExtendedRepository;
        this.supplyZoneMapper = supplyZoneMapper;
        this.supplyZoneSearchRepository = supplyZoneSearchRepository;
    }

    public SupplyZoneDTO save(SupplyZoneDTO supplyZoneDTO) {
        log.debug("Request to save SupplyZone : {}", supplyZoneDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if(supplyZoneDTO.getId() == null) {
            supplyZoneDTO.setCreatedBy(currentUser);
            supplyZoneDTO.setCreatedOn(currentDateTime);
        }
        else {
            supplyZoneDTO.setUpdatedBy(currentUser);
            supplyZoneDTO.setUpdatedOn(currentDateTime);
        }

        SupplyZone supplyZone = supplyZoneMapper.toEntity(supplyZoneDTO);
        supplyZone = supplyZoneExtendedRepository.save(supplyZone);
        SupplyZoneDTO result = supplyZoneMapper.toDto(supplyZone);
        supplyZoneSearchRepository.save(supplyZone);
        return result;
    }
}
