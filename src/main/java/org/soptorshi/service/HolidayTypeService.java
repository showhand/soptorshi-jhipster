package org.soptorshi.service;

import org.soptorshi.domain.HolidayType;
import org.soptorshi.repository.HolidayTypeRepository;
import org.soptorshi.repository.search.HolidayTypeSearchRepository;
import org.soptorshi.service.dto.HolidayTypeDTO;
import org.soptorshi.service.mapper.HolidayTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing HolidayType.
 */
@Service
@Transactional
public class HolidayTypeService {

    private final Logger log = LoggerFactory.getLogger(HolidayTypeService.class);

    private final HolidayTypeRepository holidayTypeRepository;

    private final HolidayTypeMapper holidayTypeMapper;

    private final HolidayTypeSearchRepository holidayTypeSearchRepository;

    public HolidayTypeService(HolidayTypeRepository holidayTypeRepository, HolidayTypeMapper holidayTypeMapper, HolidayTypeSearchRepository holidayTypeSearchRepository) {
        this.holidayTypeRepository = holidayTypeRepository;
        this.holidayTypeMapper = holidayTypeMapper;
        this.holidayTypeSearchRepository = holidayTypeSearchRepository;
    }

    /**
     * Save a holidayType.
     *
     * @param holidayTypeDTO the entity to save
     * @return the persisted entity
     */
    public HolidayTypeDTO save(HolidayTypeDTO holidayTypeDTO) {
        log.debug("Request to save HolidayType : {}", holidayTypeDTO);
        HolidayType holidayType = holidayTypeMapper.toEntity(holidayTypeDTO);
        holidayType = holidayTypeRepository.save(holidayType);
        HolidayTypeDTO result = holidayTypeMapper.toDto(holidayType);
        holidayTypeSearchRepository.save(holidayType);
        return result;
    }

    /**
     * Get all the holidayTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<HolidayTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all HolidayTypes");
        return holidayTypeRepository.findAll(pageable)
            .map(holidayTypeMapper::toDto);
    }


    /**
     * Get one holidayType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<HolidayTypeDTO> findOne(Long id) {
        log.debug("Request to get HolidayType : {}", id);
        return holidayTypeRepository.findById(id)
            .map(holidayTypeMapper::toDto);
    }

    /**
     * Delete the holidayType by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete HolidayType : {}", id);
        holidayTypeRepository.deleteById(id);
        holidayTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the holidayType corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<HolidayTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of HolidayTypes for query {}", query);
        return holidayTypeSearchRepository.search(queryStringQuery(query), pageable)
            .map(holidayTypeMapper::toDto);
    }
}
