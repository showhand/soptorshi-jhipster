package org.soptorshi.service;

import org.soptorshi.domain.ExperienceInformation;
import org.soptorshi.repository.ExperienceInformationRepository;
import org.soptorshi.repository.search.ExperienceInformationSearchRepository;
import org.soptorshi.service.dto.ExperienceInformationDTO;
import org.soptorshi.service.mapper.ExperienceInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ExperienceInformation.
 */
@Service
@Transactional
public class ExperienceInformationService {

    private final Logger log = LoggerFactory.getLogger(ExperienceInformationService.class);

    private final ExperienceInformationRepository experienceInformationRepository;

    private final ExperienceInformationMapper experienceInformationMapper;

    private final ExperienceInformationSearchRepository experienceInformationSearchRepository;

    public ExperienceInformationService(ExperienceInformationRepository experienceInformationRepository, ExperienceInformationMapper experienceInformationMapper, ExperienceInformationSearchRepository experienceInformationSearchRepository) {
        this.experienceInformationRepository = experienceInformationRepository;
        this.experienceInformationMapper = experienceInformationMapper;
        this.experienceInformationSearchRepository = experienceInformationSearchRepository;
    }

    /**
     * Save a experienceInformation.
     *
     * @param experienceInformationDTO the entity to save
     * @return the persisted entity
     */
    public ExperienceInformationDTO save(ExperienceInformationDTO experienceInformationDTO) {
        log.debug("Request to save ExperienceInformation : {}", experienceInformationDTO);
        ExperienceInformation experienceInformation = experienceInformationMapper.toEntity(experienceInformationDTO);
        experienceInformation = experienceInformationRepository.save(experienceInformation);
        ExperienceInformationDTO result = experienceInformationMapper.toDto(experienceInformation);
        experienceInformationSearchRepository.save(experienceInformation);
        return result;
    }

    /**
     * Get all the experienceInformations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExperienceInformationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExperienceInformations");
        return experienceInformationRepository.findAll(pageable)
            .map(experienceInformationMapper::toDto);
    }


    /**
     * Get one experienceInformation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ExperienceInformationDTO> findOne(Long id) {
        log.debug("Request to get ExperienceInformation : {}", id);
        return experienceInformationRepository.findById(id)
            .map(experienceInformationMapper::toDto);
    }

    /**
     * Delete the experienceInformation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExperienceInformation : {}", id);
        experienceInformationRepository.deleteById(id);
        experienceInformationSearchRepository.deleteById(id);
    }

    /**
     * Search for the experienceInformation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ExperienceInformationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ExperienceInformations for query {}", query);
        return experienceInformationSearchRepository.search(queryStringQuery(query), pageable)
            .map(experienceInformationMapper::toDto);
    }
}
