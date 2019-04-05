package org.soptorshi.service;

import org.soptorshi.domain.FamilyInformation;
import org.soptorshi.repository.FamilyInformationRepository;
import org.soptorshi.repository.search.FamilyInformationSearchRepository;
import org.soptorshi.service.dto.FamilyInformationDTO;
import org.soptorshi.service.mapper.FamilyInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing FamilyInformation.
 */
@Service
@Transactional
public class FamilyInformationService {

    private final Logger log = LoggerFactory.getLogger(FamilyInformationService.class);

    private final FamilyInformationRepository familyInformationRepository;

    private final FamilyInformationMapper familyInformationMapper;

    private final FamilyInformationSearchRepository familyInformationSearchRepository;

    public FamilyInformationService(FamilyInformationRepository familyInformationRepository, FamilyInformationMapper familyInformationMapper, FamilyInformationSearchRepository familyInformationSearchRepository) {
        this.familyInformationRepository = familyInformationRepository;
        this.familyInformationMapper = familyInformationMapper;
        this.familyInformationSearchRepository = familyInformationSearchRepository;
    }

    /**
     * Save a familyInformation.
     *
     * @param familyInformationDTO the entity to save
     * @return the persisted entity
     */
    public FamilyInformationDTO save(FamilyInformationDTO familyInformationDTO) {
        log.debug("Request to save FamilyInformation : {}", familyInformationDTO);
        FamilyInformation familyInformation = familyInformationMapper.toEntity(familyInformationDTO);
        familyInformation = familyInformationRepository.save(familyInformation);
        FamilyInformationDTO result = familyInformationMapper.toDto(familyInformation);
        familyInformationSearchRepository.save(familyInformation);
        return result;
    }

    /**
     * Get all the familyInformations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FamilyInformationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FamilyInformations");
        return familyInformationRepository.findAll(pageable)
            .map(familyInformationMapper::toDto);
    }


    /**
     * Get one familyInformation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<FamilyInformationDTO> findOne(Long id) {
        log.debug("Request to get FamilyInformation : {}", id);
        return familyInformationRepository.findById(id)
            .map(familyInformationMapper::toDto);
    }

    /**
     * Delete the familyInformation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FamilyInformation : {}", id);
        familyInformationRepository.deleteById(id);
        familyInformationSearchRepository.deleteById(id);
    }

    /**
     * Search for the familyInformation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FamilyInformationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FamilyInformations for query {}", query);
        return familyInformationSearchRepository.search(queryStringQuery(query), pageable)
            .map(familyInformationMapper::toDto);
    }
}
