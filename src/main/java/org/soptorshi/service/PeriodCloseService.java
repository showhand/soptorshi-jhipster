package org.soptorshi.service;

import org.soptorshi.domain.PeriodClose;
import org.soptorshi.repository.PeriodCloseRepository;
import org.soptorshi.repository.search.PeriodCloseSearchRepository;
import org.soptorshi.service.dto.PeriodCloseDTO;
import org.soptorshi.service.mapper.PeriodCloseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PeriodClose.
 */
@Service
@Transactional
public class PeriodCloseService {

    private final Logger log = LoggerFactory.getLogger(PeriodCloseService.class);

    private final PeriodCloseRepository periodCloseRepository;

    private final PeriodCloseMapper periodCloseMapper;

    private final PeriodCloseSearchRepository periodCloseSearchRepository;

    public PeriodCloseService(PeriodCloseRepository periodCloseRepository, PeriodCloseMapper periodCloseMapper, PeriodCloseSearchRepository periodCloseSearchRepository) {
        this.periodCloseRepository = periodCloseRepository;
        this.periodCloseMapper = periodCloseMapper;
        this.periodCloseSearchRepository = periodCloseSearchRepository;
    }

    /**
     * Save a periodClose.
     *
     * @param periodCloseDTO the entity to save
     * @return the persisted entity
     */
    public PeriodCloseDTO save(PeriodCloseDTO periodCloseDTO) {
        log.debug("Request to save PeriodClose : {}", periodCloseDTO);
        PeriodClose periodClose = periodCloseMapper.toEntity(periodCloseDTO);
        periodClose = periodCloseRepository.save(periodClose);
        PeriodCloseDTO result = periodCloseMapper.toDto(periodClose);
        periodCloseSearchRepository.save(periodClose);
        return result;
    }

    /**
     * Get all the periodCloses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PeriodCloseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PeriodCloses");
        return periodCloseRepository.findAll(pageable)
            .map(periodCloseMapper::toDto);
    }


    /**
     * Get one periodClose by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PeriodCloseDTO> findOne(Long id) {
        log.debug("Request to get PeriodClose : {}", id);
        return periodCloseRepository.findById(id)
            .map(periodCloseMapper::toDto);
    }

    /**
     * Delete the periodClose by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PeriodClose : {}", id);
        periodCloseRepository.deleteById(id);
        periodCloseSearchRepository.deleteById(id);
    }

    /**
     * Search for the periodClose corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PeriodCloseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PeriodCloses for query {}", query);
        return periodCloseSearchRepository.search(queryStringQuery(query), pageable)
            .map(periodCloseMapper::toDto);
    }
}
