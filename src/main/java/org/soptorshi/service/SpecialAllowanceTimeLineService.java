package org.soptorshi.service;

import org.soptorshi.domain.SpecialAllowanceTimeLine;
import org.soptorshi.repository.SpecialAllowanceTimeLineRepository;
import org.soptorshi.repository.search.SpecialAllowanceTimeLineSearchRepository;
import org.soptorshi.service.dto.SpecialAllowanceTimeLineDTO;
import org.soptorshi.service.mapper.SpecialAllowanceTimeLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SpecialAllowanceTimeLine.
 */
@Service
@Transactional
public class SpecialAllowanceTimeLineService {

    private final Logger log = LoggerFactory.getLogger(SpecialAllowanceTimeLineService.class);

    private final SpecialAllowanceTimeLineRepository specialAllowanceTimeLineRepository;

    private final SpecialAllowanceTimeLineMapper specialAllowanceTimeLineMapper;

    private final SpecialAllowanceTimeLineSearchRepository specialAllowanceTimeLineSearchRepository;

    public SpecialAllowanceTimeLineService(SpecialAllowanceTimeLineRepository specialAllowanceTimeLineRepository, SpecialAllowanceTimeLineMapper specialAllowanceTimeLineMapper, SpecialAllowanceTimeLineSearchRepository specialAllowanceTimeLineSearchRepository) {
        this.specialAllowanceTimeLineRepository = specialAllowanceTimeLineRepository;
        this.specialAllowanceTimeLineMapper = specialAllowanceTimeLineMapper;
        this.specialAllowanceTimeLineSearchRepository = specialAllowanceTimeLineSearchRepository;
    }

    /**
     * Save a specialAllowanceTimeLine.
     *
     * @param specialAllowanceTimeLineDTO the entity to save
     * @return the persisted entity
     */
    public SpecialAllowanceTimeLineDTO save(SpecialAllowanceTimeLineDTO specialAllowanceTimeLineDTO) {
        log.debug("Request to save SpecialAllowanceTimeLine : {}", specialAllowanceTimeLineDTO);
        SpecialAllowanceTimeLine specialAllowanceTimeLine = specialAllowanceTimeLineMapper.toEntity(specialAllowanceTimeLineDTO);
        specialAllowanceTimeLine = specialAllowanceTimeLineRepository.save(specialAllowanceTimeLine);
        SpecialAllowanceTimeLineDTO result = specialAllowanceTimeLineMapper.toDto(specialAllowanceTimeLine);
        specialAllowanceTimeLineSearchRepository.save(specialAllowanceTimeLine);
        return result;
    }

    /**
     * Get all the specialAllowanceTimeLines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SpecialAllowanceTimeLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SpecialAllowanceTimeLines");
        return specialAllowanceTimeLineRepository.findAll(pageable)
            .map(specialAllowanceTimeLineMapper::toDto);
    }


    /**
     * Get one specialAllowanceTimeLine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SpecialAllowanceTimeLineDTO> findOne(Long id) {
        log.debug("Request to get SpecialAllowanceTimeLine : {}", id);
        return specialAllowanceTimeLineRepository.findById(id)
            .map(specialAllowanceTimeLineMapper::toDto);
    }

    /**
     * Delete the specialAllowanceTimeLine by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SpecialAllowanceTimeLine : {}", id);
        specialAllowanceTimeLineRepository.deleteById(id);
        specialAllowanceTimeLineSearchRepository.deleteById(id);
    }

    /**
     * Search for the specialAllowanceTimeLine corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SpecialAllowanceTimeLineDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SpecialAllowanceTimeLines for query {}", query);
        return specialAllowanceTimeLineSearchRepository.search(queryStringQuery(query), pageable)
            .map(specialAllowanceTimeLineMapper::toDto);
    }
}
