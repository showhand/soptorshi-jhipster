package org.soptorshi.service;

import org.soptorshi.service.dto.SupplySalesRepresentativeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SupplySalesRepresentative.
 */
public interface SupplySalesRepresentativeService {

    /**
     * Save a supplySalesRepresentative.
     *
     * @param supplySalesRepresentativeDTO the entity to save
     * @return the persisted entity
     */
    SupplySalesRepresentativeDTO save(SupplySalesRepresentativeDTO supplySalesRepresentativeDTO);

    /**
     * Get all the supplySalesRepresentatives.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplySalesRepresentativeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" supplySalesRepresentative.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SupplySalesRepresentativeDTO> findOne(Long id);

    /**
     * Delete the "id" supplySalesRepresentative.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the supplySalesRepresentative corresponding to the query.
     *
     * @param query the query of the search
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SupplySalesRepresentativeDTO> search(String query, Pageable pageable);
}
