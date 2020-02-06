package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.Weekend;
import org.soptorshi.repository.WeekendRepository;
import org.soptorshi.repository.search.WeekendSearchRepository;
import org.soptorshi.service.dto.WeekendDTO;
import org.soptorshi.service.mapper.WeekendMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Weekend.
 */
@Service
@Transactional
public class WeekendService {

    private final Logger log = LoggerFactory.getLogger(WeekendService.class);

    private final WeekendRepository weekendRepository;

    private final WeekendMapper weekendMapper;

    private final WeekendSearchRepository weekendSearchRepository;

    public WeekendService(WeekendRepository weekendRepository, WeekendMapper weekendMapper, WeekendSearchRepository weekendSearchRepository) {
        this.weekendRepository = weekendRepository;
        this.weekendMapper = weekendMapper;
        this.weekendSearchRepository = weekendSearchRepository;
    }

    /**
     * Save a weekend.
     *
     * @param weekendDTO the entity to save
     * @return the persisted entity
     */
    public WeekendDTO save(WeekendDTO weekendDTO) {
        log.debug("Request to save Weekend : {}", weekendDTO);
        Weekend weekend = weekendMapper.toEntity(weekendDTO);
        weekend = weekendRepository.save(weekend);
        WeekendDTO result = weekendMapper.toDto(weekend);
        weekendSearchRepository.save(weekend);
        return result;
    }

    /**
     * Get all the weekends.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WeekendDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Weekends");
        return weekendRepository.findAll(pageable)
            .map(weekendMapper::toDto);
    }


    /**
     * Get one weekend by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<WeekendDTO> findOne(Long id) {
        log.debug("Request to get Weekend : {}", id);
        return weekendRepository.findById(id)
            .map(weekendMapper::toDto);
    }

    /**
     * Delete the weekend by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Weekend : {}", id);
        weekendRepository.deleteById(id);
        weekendSearchRepository.deleteById(id);
    }

    /**
     * Search for the weekend corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<WeekendDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Weekends for query {}", query);
        return weekendSearchRepository.search(queryStringQuery(query), pageable)
            .map(weekendMapper::toDto);
    }
}
