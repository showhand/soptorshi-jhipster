package org.soptorshi.service;

import org.soptorshi.domain.Designation;
import org.soptorshi.repository.DesignationRepository;
import org.soptorshi.repository.search.DesignationSearchRepository;
import org.soptorshi.service.dto.DesignationDTO;
import org.soptorshi.service.mapper.DesignationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Designation.
 */
@Service
@Transactional
public class DesignationService {

    private final Logger log = LoggerFactory.getLogger(DesignationService.class);

    private final DesignationRepository designationRepository;

    private final DesignationMapper designationMapper;

    private final DesignationSearchRepository designationSearchRepository;

    public DesignationService(DesignationRepository designationRepository, DesignationMapper designationMapper, DesignationSearchRepository designationSearchRepository) {
        this.designationRepository = designationRepository;
        this.designationMapper = designationMapper;
        this.designationSearchRepository = designationSearchRepository;
    }

    /**
     * Save a designation.
     *
     * @param designationDTO the entity to save
     * @return the persisted entity
     */
    public DesignationDTO save(DesignationDTO designationDTO) {
        log.debug("Request to save Designation : {}", designationDTO);
        Designation designation = designationMapper.toEntity(designationDTO);
        designation = designationRepository.save(designation);
        DesignationDTO result = designationMapper.toDto(designation);
        designationSearchRepository.save(designation);
        return result;
    }

    /**
     * Get all the designations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Designations");
        return designationRepository.findAll(pageable)
            .map(designationMapper::toDto);
    }


    /**
     * Get one designation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DesignationDTO> findOne(Long id) {
        log.debug("Request to get Designation : {}", id);
        return designationRepository.findById(id)
            .map(designationMapper::toDto);
    }

    /**
     * Delete the designation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Designation : {}", id);
        designationRepository.deleteById(id);
        designationSearchRepository.deleteById(id);
    }

    /**
     * Search for the designation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Designations for query {}", query);
        return designationSearchRepository.search(queryStringQuery(query), pageable)
            .map(designationMapper::toDto);
    }
}
