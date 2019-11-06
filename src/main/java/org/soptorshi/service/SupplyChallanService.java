package org.soptorshi.service;

import org.soptorshi.service.dto.SupplyChallanDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SupplyChallan.
 */
public interface SupplyChallanService {

    /**
     * Save a supplyChallan.
     *
     * @param supplyChallanDTO the entity to save
     * @return the persisted entity
     */
    SupplyChallanDTO save(SupplyChallanDTO supplyChallanDTO);

    /**
     * Get all the supplyChallans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyChallanDTO> findAll(Pageable pageable);


    /**
     * Get the "id" supplyChallan.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SupplyChallanDTO> findOne(Long id);

    /**
     * Delete the "id" supplyChallan.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the supplyChallan corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplyChallanDTO> search(String query, Pageable pageable);
}
