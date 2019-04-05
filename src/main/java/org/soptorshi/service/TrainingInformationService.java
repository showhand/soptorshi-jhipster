package org.soptorshi.service;

import org.soptorshi.domain.TrainingInformation;
import org.soptorshi.repository.TrainingInformationRepository;
import org.soptorshi.repository.search.TrainingInformationSearchRepository;
import org.soptorshi.service.dto.TrainingInformationDTO;
import org.soptorshi.service.mapper.TrainingInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TrainingInformation.
 */
@Service
@Transactional
public class TrainingInformationService {

    private final Logger log = LoggerFactory.getLogger(TrainingInformationService.class);

    private final TrainingInformationRepository trainingInformationRepository;

    private final TrainingInformationMapper trainingInformationMapper;

    private final TrainingInformationSearchRepository trainingInformationSearchRepository;

    public TrainingInformationService(TrainingInformationRepository trainingInformationRepository, TrainingInformationMapper trainingInformationMapper, TrainingInformationSearchRepository trainingInformationSearchRepository) {
        this.trainingInformationRepository = trainingInformationRepository;
        this.trainingInformationMapper = trainingInformationMapper;
        this.trainingInformationSearchRepository = trainingInformationSearchRepository;
    }

    /**
     * Save a trainingInformation.
     *
     * @param trainingInformationDTO the entity to save
     * @return the persisted entity
     */
    public TrainingInformationDTO save(TrainingInformationDTO trainingInformationDTO) {
        log.debug("Request to save TrainingInformation : {}", trainingInformationDTO);
        TrainingInformation trainingInformation = trainingInformationMapper.toEntity(trainingInformationDTO);
        trainingInformation = trainingInformationRepository.save(trainingInformation);
        TrainingInformationDTO result = trainingInformationMapper.toDto(trainingInformation);
        trainingInformationSearchRepository.save(trainingInformation);
        return result;
    }

    /**
     * Get all the trainingInformations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TrainingInformationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TrainingInformations");
        return trainingInformationRepository.findAll(pageable)
            .map(trainingInformationMapper::toDto);
    }


    /**
     * Get one trainingInformation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TrainingInformationDTO> findOne(Long id) {
        log.debug("Request to get TrainingInformation : {}", id);
        return trainingInformationRepository.findById(id)
            .map(trainingInformationMapper::toDto);
    }

    /**
     * Delete the trainingInformation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TrainingInformation : {}", id);
        trainingInformationRepository.deleteById(id);
        trainingInformationSearchRepository.deleteById(id);
    }

    /**
     * Search for the trainingInformation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TrainingInformationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TrainingInformations for query {}", query);
        return trainingInformationSearchRepository.search(queryStringQuery(query), pageable)
            .map(trainingInformationMapper::toDto);
    }
}
