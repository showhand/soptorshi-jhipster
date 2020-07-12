package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyZoneWiseAccumulation;
import org.soptorshi.repository.SupplyZoneWiseAccumulationRepository;
import org.soptorshi.repository.search.SupplyZoneWiseAccumulationSearchRepository;
import org.soptorshi.service.dto.SupplyZoneWiseAccumulationDTO;
import org.soptorshi.service.mapper.SupplyZoneWiseAccumulationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing SupplyZoneWiseAccumulation.
 */
@Service
@Transactional
public class SupplyZoneWiseAccumulationService {

    private final Logger log = LoggerFactory.getLogger(SupplyZoneWiseAccumulationService.class);

    private final SupplyZoneWiseAccumulationRepository supplyZoneWiseAccumulationRepository;

    private final SupplyZoneWiseAccumulationMapper supplyZoneWiseAccumulationMapper;

    private final SupplyZoneWiseAccumulationSearchRepository supplyZoneWiseAccumulationSearchRepository;

    public SupplyZoneWiseAccumulationService(SupplyZoneWiseAccumulationRepository supplyZoneWiseAccumulationRepository, SupplyZoneWiseAccumulationMapper supplyZoneWiseAccumulationMapper, SupplyZoneWiseAccumulationSearchRepository supplyZoneWiseAccumulationSearchRepository) {
        this.supplyZoneWiseAccumulationRepository = supplyZoneWiseAccumulationRepository;
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
        supplyZoneWiseAccumulation = supplyZoneWiseAccumulationRepository.save(supplyZoneWiseAccumulation);
        SupplyZoneWiseAccumulationDTO result = supplyZoneWiseAccumulationMapper.toDto(supplyZoneWiseAccumulation);
        supplyZoneWiseAccumulationSearchRepository.save(supplyZoneWiseAccumulation);
        return result;
    }

    /**
     * Get all the supplyZoneWiseAccumulations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyZoneWiseAccumulationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplyZoneWiseAccumulations");
        return supplyZoneWiseAccumulationRepository.findAll(pageable)
            .map(supplyZoneWiseAccumulationMapper::toDto);
    }


    /**
     * Get one supplyZoneWiseAccumulation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SupplyZoneWiseAccumulationDTO> findOne(Long id) {
        log.debug("Request to get SupplyZoneWiseAccumulation : {}", id);
        return supplyZoneWiseAccumulationRepository.findById(id)
            .map(supplyZoneWiseAccumulationMapper::toDto);
    }

    /**
     * Delete the supplyZoneWiseAccumulation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SupplyZoneWiseAccumulation : {}", id);
        supplyZoneWiseAccumulationRepository.deleteById(id);
        supplyZoneWiseAccumulationSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplyZoneWiseAccumulation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyZoneWiseAccumulationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupplyZoneWiseAccumulations for query {}", query);
        return supplyZoneWiseAccumulationSearchRepository.search(queryStringQuery(query), pageable)
            .map(supplyZoneWiseAccumulationMapper::toDto);
    }
}
