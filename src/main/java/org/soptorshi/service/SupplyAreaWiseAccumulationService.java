package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyAreaWiseAccumulation;
import org.soptorshi.repository.SupplyAreaWiseAccumulationRepository;
import org.soptorshi.repository.search.SupplyAreaWiseAccumulationSearchRepository;
import org.soptorshi.service.dto.SupplyAreaWiseAccumulationDTO;
import org.soptorshi.service.mapper.SupplyAreaWiseAccumulationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing SupplyAreaWiseAccumulation.
 */
@Service
@Transactional
public class SupplyAreaWiseAccumulationService {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaWiseAccumulationService.class);

    private final SupplyAreaWiseAccumulationRepository supplyAreaWiseAccumulationRepository;

    private final SupplyAreaWiseAccumulationMapper supplyAreaWiseAccumulationMapper;

    private final SupplyAreaWiseAccumulationSearchRepository supplyAreaWiseAccumulationSearchRepository;

    public SupplyAreaWiseAccumulationService(SupplyAreaWiseAccumulationRepository supplyAreaWiseAccumulationRepository, SupplyAreaWiseAccumulationMapper supplyAreaWiseAccumulationMapper, SupplyAreaWiseAccumulationSearchRepository supplyAreaWiseAccumulationSearchRepository) {
        this.supplyAreaWiseAccumulationRepository = supplyAreaWiseAccumulationRepository;
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
        SupplyAreaWiseAccumulation supplyAreaWiseAccumulation = supplyAreaWiseAccumulationMapper.toEntity(supplyAreaWiseAccumulationDTO);
        supplyAreaWiseAccumulation = supplyAreaWiseAccumulationRepository.save(supplyAreaWiseAccumulation);
        SupplyAreaWiseAccumulationDTO result = supplyAreaWiseAccumulationMapper.toDto(supplyAreaWiseAccumulation);
        supplyAreaWiseAccumulationSearchRepository.save(supplyAreaWiseAccumulation);
        return result;
    }

    /**
     * Get all the supplyAreaWiseAccumulations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyAreaWiseAccumulationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplyAreaWiseAccumulations");
        return supplyAreaWiseAccumulationRepository.findAll(pageable)
            .map(supplyAreaWiseAccumulationMapper::toDto);
    }


    /**
     * Get one supplyAreaWiseAccumulation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SupplyAreaWiseAccumulationDTO> findOne(Long id) {
        log.debug("Request to get SupplyAreaWiseAccumulation : {}", id);
        return supplyAreaWiseAccumulationRepository.findById(id)
            .map(supplyAreaWiseAccumulationMapper::toDto);
    }

    /**
     * Delete the supplyAreaWiseAccumulation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SupplyAreaWiseAccumulation : {}", id);
        supplyAreaWiseAccumulationRepository.deleteById(id);
        supplyAreaWiseAccumulationSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplyAreaWiseAccumulation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyAreaWiseAccumulationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupplyAreaWiseAccumulations for query {}", query);
        return supplyAreaWiseAccumulationSearchRepository.search(queryStringQuery(query), pageable)
            .map(supplyAreaWiseAccumulationMapper::toDto);
    }
}
