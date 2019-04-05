package org.soptorshi.service;

import org.soptorshi.domain.ReferenceInformation;
import org.soptorshi.repository.ReferenceInformationRepository;
import org.soptorshi.repository.search.ReferenceInformationSearchRepository;
import org.soptorshi.service.dto.ReferenceInformationDTO;
import org.soptorshi.service.mapper.ReferenceInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ReferenceInformation.
 */
@Service
@Transactional
public class ReferenceInformationService {

    private final Logger log = LoggerFactory.getLogger(ReferenceInformationService.class);

    private final ReferenceInformationRepository referenceInformationRepository;

    private final ReferenceInformationMapper referenceInformationMapper;

    private final ReferenceInformationSearchRepository referenceInformationSearchRepository;

    public ReferenceInformationService(ReferenceInformationRepository referenceInformationRepository, ReferenceInformationMapper referenceInformationMapper, ReferenceInformationSearchRepository referenceInformationSearchRepository) {
        this.referenceInformationRepository = referenceInformationRepository;
        this.referenceInformationMapper = referenceInformationMapper;
        this.referenceInformationSearchRepository = referenceInformationSearchRepository;
    }

    /**
     * Save a referenceInformation.
     *
     * @param referenceInformationDTO the entity to save
     * @return the persisted entity
     */
    public ReferenceInformationDTO save(ReferenceInformationDTO referenceInformationDTO) {
        log.debug("Request to save ReferenceInformation : {}", referenceInformationDTO);
        ReferenceInformation referenceInformation = referenceInformationMapper.toEntity(referenceInformationDTO);
        referenceInformation = referenceInformationRepository.save(referenceInformation);
        ReferenceInformationDTO result = referenceInformationMapper.toDto(referenceInformation);
        referenceInformationSearchRepository.save(referenceInformation);
        return result;
    }

    /**
     * Get all the referenceInformations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ReferenceInformationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReferenceInformations");
        return referenceInformationRepository.findAll(pageable)
            .map(referenceInformationMapper::toDto);
    }


    /**
     * Get one referenceInformation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ReferenceInformationDTO> findOne(Long id) {
        log.debug("Request to get ReferenceInformation : {}", id);
        return referenceInformationRepository.findById(id)
            .map(referenceInformationMapper::toDto);
    }

    /**
     * Delete the referenceInformation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ReferenceInformation : {}", id);
        referenceInformationRepository.deleteById(id);
        referenceInformationSearchRepository.deleteById(id);
    }

    /**
     * Search for the referenceInformation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ReferenceInformationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ReferenceInformations for query {}", query);
        return referenceInformationSearchRepository.search(queryStringQuery(query), pageable)
            .map(referenceInformationMapper::toDto);
    }
}
