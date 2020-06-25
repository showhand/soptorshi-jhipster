package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyArea;
import org.soptorshi.repository.SupplyAreaRepository;
import org.soptorshi.repository.search.SupplyAreaSearchRepository;
import org.soptorshi.service.dto.SupplyAreaDTO;
import org.soptorshi.service.mapper.SupplyAreaMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing SupplyArea.
 */
@Service
@Transactional
public class SupplyAreaService {

    private final Logger log = LoggerFactory.getLogger(SupplyAreaService.class);

    private final SupplyAreaRepository supplyAreaRepository;

    private final SupplyAreaMapper supplyAreaMapper;

    private final SupplyAreaSearchRepository supplyAreaSearchRepository;

    public SupplyAreaService(SupplyAreaRepository supplyAreaRepository, SupplyAreaMapper supplyAreaMapper, SupplyAreaSearchRepository supplyAreaSearchRepository) {
        this.supplyAreaRepository = supplyAreaRepository;
        this.supplyAreaMapper = supplyAreaMapper;
        this.supplyAreaSearchRepository = supplyAreaSearchRepository;
    }

    /**
     * Save a supplyArea.
     *
     * @param supplyAreaDTO the entity to save
     * @return the persisted entity
     */
    public SupplyAreaDTO save(SupplyAreaDTO supplyAreaDTO) {
        log.debug("Request to save SupplyArea : {}", supplyAreaDTO);
        SupplyArea supplyArea = supplyAreaMapper.toEntity(supplyAreaDTO);
        supplyArea = supplyAreaRepository.save(supplyArea);
        SupplyAreaDTO result = supplyAreaMapper.toDto(supplyArea);
        supplyAreaSearchRepository.save(supplyArea);
        return result;
    }

    /**
     * Get all the supplyAreas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyAreaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplyAreas");
        return supplyAreaRepository.findAll(pageable)
            .map(supplyAreaMapper::toDto);
    }


    /**
     * Get one supplyArea by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SupplyAreaDTO> findOne(Long id) {
        log.debug("Request to get SupplyArea : {}", id);
        return supplyAreaRepository.findById(id)
            .map(supplyAreaMapper::toDto);
    }

    /**
     * Delete the supplyArea by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SupplyArea : {}", id);
        supplyAreaRepository.deleteById(id);
        supplyAreaSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplyArea corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyAreaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupplyAreas for query {}", query);
        return supplyAreaSearchRepository.search(queryStringQuery(query), pageable)
            .map(supplyAreaMapper::toDto);
    }
}
