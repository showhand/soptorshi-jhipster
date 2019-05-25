package org.soptorshi.service.impl;

import org.soptorshi.service.HolidayService;
import org.soptorshi.domain.Holiday;
import org.soptorshi.repository.HolidayRepository;
import org.soptorshi.repository.search.HolidaySearchRepository;
import org.soptorshi.service.dto.HolidayDTO;
import org.soptorshi.service.mapper.HolidayMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Holiday.
 */
@Service
@Transactional
public class HolidayServiceImpl implements HolidayService {

    private final Logger log = LoggerFactory.getLogger(HolidayServiceImpl.class);

    private final HolidayRepository holidayRepository;

    private final HolidayMapper holidayMapper;

    private final HolidaySearchRepository holidaySearchRepository;

    public HolidayServiceImpl(HolidayRepository holidayRepository, HolidayMapper holidayMapper, HolidaySearchRepository holidaySearchRepository) {
        this.holidayRepository = holidayRepository;
        this.holidayMapper = holidayMapper;
        this.holidaySearchRepository = holidaySearchRepository;
    }

    /**
     * Save a holiday.
     *
     * @param holidayDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HolidayDTO save(HolidayDTO holidayDTO) {
        log.debug("Request to save Holiday : {}", holidayDTO);
        Holiday holiday = holidayMapper.toEntity(holidayDTO);
        holiday = holidayRepository.save(holiday);
        HolidayDTO result = holidayMapper.toDto(holiday);
        holidaySearchRepository.save(holiday);
        return result;
    }

    /**
     * Get all the holidays.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HolidayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Holidays");
        return holidayRepository.findAll(pageable)
            .map(holidayMapper::toDto);
    }


    /**
     * Get one holiday by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<HolidayDTO> findOne(Long id) {
        log.debug("Request to get Holiday : {}", id);
        return holidayRepository.findById(id)
            .map(holidayMapper::toDto);
    }

    /**
     * Delete the holiday by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Holiday : {}", id);
        holidayRepository.deleteById(id);
        holidaySearchRepository.deleteById(id);
    }

    /**
     * Search for the holiday corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<HolidayDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Holidays for query {}", query);
        return holidaySearchRepository.search(queryStringQuery(query), pageable)
            .map(holidayMapper::toDto);
    }
}
