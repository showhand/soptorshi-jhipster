package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPo;
import org.soptorshi.repository.CommercialPoRepository;
import org.soptorshi.repository.search.CommercialPoSearchRepository;
import org.soptorshi.service.dto.CommercialPoDTO;
import org.soptorshi.service.mapper.CommercialPoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialPo.
 */
@Service
@Transactional
public class CommercialPoService {

    private final Logger log = LoggerFactory.getLogger(CommercialPoService.class);

    private final CommercialPoRepository commercialPoRepository;

    private final CommercialPoMapper commercialPoMapper;

    private final CommercialPoSearchRepository commercialPoSearchRepository;

    public CommercialPoService(CommercialPoRepository commercialPoRepository, CommercialPoMapper commercialPoMapper, CommercialPoSearchRepository commercialPoSearchRepository) {
        this.commercialPoRepository = commercialPoRepository;
        this.commercialPoMapper = commercialPoMapper;
        this.commercialPoSearchRepository = commercialPoSearchRepository;
    }

    /**
     * Save a commercialPo.
     *
     * @param commercialPoDTO the entity to save
     * @return the persisted entity
     */
    public CommercialPoDTO save(CommercialPoDTO commercialPoDTO) {
        log.debug("Request to save CommercialPo : {}", commercialPoDTO);
        CommercialPo commercialPo = commercialPoMapper.toEntity(commercialPoDTO);
        commercialPo = commercialPoRepository.save(commercialPo);
        CommercialPoDTO result = commercialPoMapper.toDto(commercialPo);
        commercialPoSearchRepository.save(commercialPo);
        return result;
    }

    /**
     * Get all the commercialPos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialPos");
        return commercialPoRepository.findAll(pageable)
            .map(commercialPoMapper::toDto);
    }


    /**
     * Get one commercialPo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialPoDTO> findOne(Long id) {
        log.debug("Request to get CommercialPo : {}", id);
        return commercialPoRepository.findById(id)
            .map(commercialPoMapper::toDto);
    }

    /**
     * Delete the commercialPo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialPo : {}", id);
        commercialPoRepository.deleteById(id);
        commercialPoSearchRepository.deleteById(id);
    }

    /**
     * Search for the commercialPo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialPos for query {}", query);
        return commercialPoSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialPoMapper::toDto);
    }
}
