package org.soptorshi.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyZone;
import org.soptorshi.repository.SupplyZoneRepository;
import org.soptorshi.repository.search.SupplyZoneSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SupplyZoneService;
import org.soptorshi.service.dto.SupplyZoneDTO;
import org.soptorshi.service.mapper.SupplyZoneMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing SupplyZone.
 */
@Service
@Transactional
public class SupplyZoneServiceImpl implements SupplyZoneService {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneServiceImpl.class);

    private final SupplyZoneRepository supplyZoneRepository;

    private final SupplyZoneMapper supplyZoneMapper;

    private final SupplyZoneSearchRepository supplyZoneSearchRepository;

    public SupplyZoneServiceImpl(SupplyZoneRepository supplyZoneRepository, SupplyZoneMapper supplyZoneMapper, SupplyZoneSearchRepository supplyZoneSearchRepository) {
        this.supplyZoneRepository = supplyZoneRepository;
        this.supplyZoneMapper = supplyZoneMapper;
        this.supplyZoneSearchRepository = supplyZoneSearchRepository;
    }

    /**
     * Save a supplyZone.
     *
     * @param supplyZoneDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SupplyZoneDTO save(SupplyZoneDTO supplyZoneDTO) {
        log.debug("Request to save SupplyZone : {}", supplyZoneDTO);
        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().toString() : "";
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
        supplyZone = supplyZoneRepository.save(supplyZone);
        SupplyZoneDTO result = supplyZoneMapper.toDto(supplyZone);
        supplyZoneSearchRepository.save(supplyZone);
        return result;
    }

    /**
     * Get all the supplyZones.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SupplyZoneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplyZones");
        return supplyZoneRepository.findAll(pageable)
            .map(supplyZoneMapper::toDto);
    }


    /**
     * Get one supplyZone by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SupplyZoneDTO> findOne(Long id) {
        log.debug("Request to get SupplyZone : {}", id);
        return supplyZoneRepository.findById(id)
            .map(supplyZoneMapper::toDto);
    }

    /**
     * Delete the supplyZone by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SupplyZone : {}", id);
        supplyZoneRepository.deleteById(id);
        supplyZoneSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplyZone corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SupplyZoneDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupplyZones for query {}", query);
        return supplyZoneSearchRepository.search(queryStringQuery(query), pageable)
            .map(supplyZoneMapper::toDto);
    }
}
