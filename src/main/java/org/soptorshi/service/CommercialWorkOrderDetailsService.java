package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialWorkOrderDetails;
import org.soptorshi.repository.CommercialWorkOrderDetailsRepository;
import org.soptorshi.repository.search.CommercialWorkOrderDetailsSearchRepository;
import org.soptorshi.service.dto.CommercialWorkOrderDetailsDTO;
import org.soptorshi.service.mapper.CommercialWorkOrderDetailsMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialWorkOrderDetails.
 */
@Service
@Transactional
public class CommercialWorkOrderDetailsService {

    private final Logger log = LoggerFactory.getLogger(CommercialWorkOrderDetailsService.class);

    private final CommercialWorkOrderDetailsRepository commercialWorkOrderDetailsRepository;

    private final CommercialWorkOrderDetailsMapper commercialWorkOrderDetailsMapper;

    private final CommercialWorkOrderDetailsSearchRepository commercialWorkOrderDetailsSearchRepository;

    public CommercialWorkOrderDetailsService(CommercialWorkOrderDetailsRepository commercialWorkOrderDetailsRepository, CommercialWorkOrderDetailsMapper commercialWorkOrderDetailsMapper, CommercialWorkOrderDetailsSearchRepository commercialWorkOrderDetailsSearchRepository) {
        this.commercialWorkOrderDetailsRepository = commercialWorkOrderDetailsRepository;
        this.commercialWorkOrderDetailsMapper = commercialWorkOrderDetailsMapper;
        this.commercialWorkOrderDetailsSearchRepository = commercialWorkOrderDetailsSearchRepository;
    }

    /**
     * Save a commercialWorkOrderDetails.
     *
     * @param commercialWorkOrderDetailsDTO the entity to save
     * @return the persisted entity
     */
    public CommercialWorkOrderDetailsDTO save(CommercialWorkOrderDetailsDTO commercialWorkOrderDetailsDTO) {
        log.debug("Request to save CommercialWorkOrderDetails : {}", commercialWorkOrderDetailsDTO);
        CommercialWorkOrderDetails commercialWorkOrderDetails = commercialWorkOrderDetailsMapper.toEntity(commercialWorkOrderDetailsDTO);
        commercialWorkOrderDetails = commercialWorkOrderDetailsRepository.save(commercialWorkOrderDetails);
        CommercialWorkOrderDetailsDTO result = commercialWorkOrderDetailsMapper.toDto(commercialWorkOrderDetails);
        commercialWorkOrderDetailsSearchRepository.save(commercialWorkOrderDetails);
        return result;
    }

    /**
     * Get all the commercialWorkOrderDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialWorkOrderDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialWorkOrderDetails");
        return commercialWorkOrderDetailsRepository.findAll(pageable)
            .map(commercialWorkOrderDetailsMapper::toDto);
    }


    /**
     * Get one commercialWorkOrderDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialWorkOrderDetailsDTO> findOne(Long id) {
        log.debug("Request to get CommercialWorkOrderDetails : {}", id);
        return commercialWorkOrderDetailsRepository.findById(id)
            .map(commercialWorkOrderDetailsMapper::toDto);
    }

    /**
     * Delete the commercialWorkOrderDetails by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialWorkOrderDetails : {}", id);
        commercialWorkOrderDetailsRepository.deleteById(id);
        commercialWorkOrderDetailsSearchRepository.deleteById(id);
    }

    /**
     * Search for the commercialWorkOrderDetails corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialWorkOrderDetailsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialWorkOrderDetails for query {}", query);
        return commercialWorkOrderDetailsSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialWorkOrderDetailsMapper::toDto);
    }
}
