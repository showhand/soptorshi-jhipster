package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyOrderDetails;
import org.soptorshi.repository.SupplyOrderDetailsRepository;
import org.soptorshi.repository.search.SupplyOrderDetailsSearchRepository;
import org.soptorshi.service.dto.SupplyOrderDetailsDTO;
import org.soptorshi.service.mapper.SupplyOrderDetailsMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing SupplyOrderDetails.
 */
@Service
@Transactional
public class SupplyOrderDetailsService {

    private final Logger log = LoggerFactory.getLogger(SupplyOrderDetailsService.class);

    private final SupplyOrderDetailsRepository supplyOrderDetailsRepository;

    private final SupplyOrderDetailsMapper supplyOrderDetailsMapper;

    private final SupplyOrderDetailsSearchRepository supplyOrderDetailsSearchRepository;

    public SupplyOrderDetailsService(SupplyOrderDetailsRepository supplyOrderDetailsRepository, SupplyOrderDetailsMapper supplyOrderDetailsMapper, SupplyOrderDetailsSearchRepository supplyOrderDetailsSearchRepository) {
        this.supplyOrderDetailsRepository = supplyOrderDetailsRepository;
        this.supplyOrderDetailsMapper = supplyOrderDetailsMapper;
        this.supplyOrderDetailsSearchRepository = supplyOrderDetailsSearchRepository;
    }

    /**
     * Save a supplyOrderDetails.
     *
     * @param supplyOrderDetailsDTO the entity to save
     * @return the persisted entity
     */
    public SupplyOrderDetailsDTO save(SupplyOrderDetailsDTO supplyOrderDetailsDTO) {
        log.debug("Request to save SupplyOrderDetails : {}", supplyOrderDetailsDTO);
        SupplyOrderDetails supplyOrderDetails = supplyOrderDetailsMapper.toEntity(supplyOrderDetailsDTO);
        supplyOrderDetails = supplyOrderDetailsRepository.save(supplyOrderDetails);
        SupplyOrderDetailsDTO result = supplyOrderDetailsMapper.toDto(supplyOrderDetails);
        supplyOrderDetailsSearchRepository.save(supplyOrderDetails);
        return result;
    }

    /**
     * Get all the supplyOrderDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyOrderDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplyOrderDetails");
        return supplyOrderDetailsRepository.findAll(pageable)
            .map(supplyOrderDetailsMapper::toDto);
    }


    /**
     * Get one supplyOrderDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SupplyOrderDetailsDTO> findOne(Long id) {
        log.debug("Request to get SupplyOrderDetails : {}", id);
        return supplyOrderDetailsRepository.findById(id)
            .map(supplyOrderDetailsMapper::toDto);
    }

    /**
     * Delete the supplyOrderDetails by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SupplyOrderDetails : {}", id);
        supplyOrderDetailsRepository.deleteById(id);
        supplyOrderDetailsSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplyOrderDetails corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyOrderDetailsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupplyOrderDetails for query {}", query);
        return supplyOrderDetailsSearchRepository.search(queryStringQuery(query), pageable)
            .map(supplyOrderDetailsMapper::toDto);
    }
}
