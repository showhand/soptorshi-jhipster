package org.soptorshi.service;

import org.soptorshi.domain.AcademicInformation;
import org.soptorshi.repository.AcademicInformationRepository;
import org.soptorshi.repository.search.AcademicInformationSearchRepository;
import org.soptorshi.service.dto.AcademicInformationDTO;
import org.soptorshi.service.mapper.AcademicInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AcademicInformation.
 */
@Service
@Transactional
public class AcademicInformationService {

    private final Logger log = LoggerFactory.getLogger(AcademicInformationService.class);

    private final AcademicInformationRepository academicInformationRepository;

    private final AcademicInformationMapper academicInformationMapper;

    private final AcademicInformationSearchRepository academicInformationSearchRepository;

    public AcademicInformationService(AcademicInformationRepository academicInformationRepository, AcademicInformationMapper academicInformationMapper, AcademicInformationSearchRepository academicInformationSearchRepository) {
        this.academicInformationRepository = academicInformationRepository;
        this.academicInformationMapper = academicInformationMapper;
        this.academicInformationSearchRepository = academicInformationSearchRepository;
    }

    /**
     * Save a academicInformation.
     *
     * @param academicInformationDTO the entity to save
     * @return the persisted entity
     */
    public AcademicInformationDTO save(AcademicInformationDTO academicInformationDTO) {
        log.debug("Request to save AcademicInformation : {}", academicInformationDTO);
        AcademicInformation academicInformation = academicInformationMapper.toEntity(academicInformationDTO);
        academicInformation = academicInformationRepository.save(academicInformation);
        AcademicInformationDTO result = academicInformationMapper.toDto(academicInformation);
        academicInformationSearchRepository.save(academicInformation);
        return result;
    }

    /**
     * Get all the academicInformations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AcademicInformationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AcademicInformations");
        return academicInformationRepository.findAll(pageable)
            .map(academicInformationMapper::toDto);
    }


    /**
     * Get one academicInformation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AcademicInformationDTO> findOne(Long id) {
        log.debug("Request to get AcademicInformation : {}", id);
        return academicInformationRepository.findById(id)
            .map(academicInformationMapper::toDto);
    }

    /**
     * Delete the academicInformation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AcademicInformation : {}", id);
        academicInformationRepository.deleteById(id);
        academicInformationSearchRepository.deleteById(id);
    }

    /**
     * Search for the academicInformation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AcademicInformationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AcademicInformations for query {}", query);
        return academicInformationSearchRepository.search(queryStringQuery(query), pageable)
            .map(academicInformationMapper::toDto);
    }
}
