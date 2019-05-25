package org.soptorshi.service;

import org.soptorshi.service.dto.HolidayTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing HolidayType.
 */
public interface HolidayTypeService {

    /**
     * Save a holidayType.
     *
     * @param holidayTypeDTO the entity to save
     * @return the persisted entity
     */
    HolidayTypeDTO save(HolidayTypeDTO holidayTypeDTO);

    /**
     * Get all the holidayTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HolidayTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" holidayType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<HolidayTypeDTO> findOne(Long id);

    /**
     * Delete the "id" holidayType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the holidayType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HolidayTypeDTO> search(String query, Pageable pageable);
}
