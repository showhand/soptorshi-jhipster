package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPoStatus;
import org.soptorshi.repository.CommercialPoStatusRepository;
import org.soptorshi.repository.search.CommercialPoStatusSearchRepository;
import org.soptorshi.service.dto.CommercialPoStatusDTO;
import org.soptorshi.service.mapper.CommercialPoStatusMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialPoStatus.
 */
@Service
@Transactional
public class CommercialPoStatusService {

    private final Logger log = LoggerFactory.getLogger(CommercialPoStatusService.class);

    private final CommercialPoStatusRepository commercialPoStatusRepository;

    private final CommercialPoStatusMapper commercialPoStatusMapper;

    private final CommercialPoStatusSearchRepository commercialPoStatusSearchRepository;

    public CommercialPoStatusService(CommercialPoStatusRepository commercialPoStatusRepository, CommercialPoStatusMapper commercialPoStatusMapper, CommercialPoStatusSearchRepository commercialPoStatusSearchRepository) {
        this.commercialPoStatusRepository = commercialPoStatusRepository;
        this.commercialPoStatusMapper = commercialPoStatusMapper;
        this.commercialPoStatusSearchRepository = commercialPoStatusSearchRepository;
    }

    /**
     * Save a commercialPoStatus.
     *
     * @param commercialPoStatusDTO the entity to save
     * @return the persisted entity
     */
    public CommercialPoStatusDTO save(CommercialPoStatusDTO commercialPoStatusDTO) {
        log.debug("Request to save CommercialPoStatus : {}", commercialPoStatusDTO);
        CommercialPoStatus commercialPoStatus = commercialPoStatusMapper.toEntity(commercialPoStatusDTO);
        commercialPoStatus = commercialPoStatusRepository.save(commercialPoStatus);
        CommercialPoStatusDTO result = commercialPoStatusMapper.toDto(commercialPoStatus);
        commercialPoStatusSearchRepository.save(commercialPoStatus);
        return result;
    }

    /**
     * Get all the commercialPoStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPoStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialPoStatuses");
        return commercialPoStatusRepository.findAll(pageable)
            .map(commercialPoStatusMapper::toDto);
    }


    /**
     * Get one commercialPoStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialPoStatusDTO> findOne(Long id) {
        log.debug("Request to get CommercialPoStatus : {}", id);
        return commercialPoStatusRepository.findById(id)
            .map(commercialPoStatusMapper::toDto);
    }

    /**
     * Delete the commercialPoStatus by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialPoStatus : {}", id);
        commercialPoStatusRepository.deleteById(id);
        commercialPoStatusSearchRepository.deleteById(id);
    }

    /**
     * Search for the commercialPoStatus corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPoStatusDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialPoStatuses for query {}", query);
        return commercialPoStatusSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialPoStatusMapper::toDto);
    }
}
