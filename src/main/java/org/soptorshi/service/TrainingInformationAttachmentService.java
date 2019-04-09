package org.soptorshi.service;

import org.soptorshi.domain.TrainingInformationAttachment;
import org.soptorshi.repository.TrainingInformationAttachmentRepository;
import org.soptorshi.repository.search.TrainingInformationAttachmentSearchRepository;
import org.soptorshi.service.dto.TrainingInformationAttachmentDTO;
import org.soptorshi.service.mapper.TrainingInformationAttachmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TrainingInformationAttachment.
 */
@Service
@Transactional
public class TrainingInformationAttachmentService {

    private final Logger log = LoggerFactory.getLogger(TrainingInformationAttachmentService.class);

    private final TrainingInformationAttachmentRepository trainingInformationAttachmentRepository;

    private final TrainingInformationAttachmentMapper trainingInformationAttachmentMapper;

    private final TrainingInformationAttachmentSearchRepository trainingInformationAttachmentSearchRepository;

    public TrainingInformationAttachmentService(TrainingInformationAttachmentRepository trainingInformationAttachmentRepository, TrainingInformationAttachmentMapper trainingInformationAttachmentMapper, TrainingInformationAttachmentSearchRepository trainingInformationAttachmentSearchRepository) {
        this.trainingInformationAttachmentRepository = trainingInformationAttachmentRepository;
        this.trainingInformationAttachmentMapper = trainingInformationAttachmentMapper;
        this.trainingInformationAttachmentSearchRepository = trainingInformationAttachmentSearchRepository;
    }

    /**
     * Save a trainingInformationAttachment.
     *
     * @param trainingInformationAttachmentDTO the entity to save
     * @return the persisted entity
     */
    public TrainingInformationAttachmentDTO save(TrainingInformationAttachmentDTO trainingInformationAttachmentDTO) {
        log.debug("Request to save TrainingInformationAttachment : {}", trainingInformationAttachmentDTO);
        TrainingInformationAttachment trainingInformationAttachment = trainingInformationAttachmentMapper.toEntity(trainingInformationAttachmentDTO);
        trainingInformationAttachment = trainingInformationAttachmentRepository.save(trainingInformationAttachment);
        TrainingInformationAttachmentDTO result = trainingInformationAttachmentMapper.toDto(trainingInformationAttachment);
        trainingInformationAttachmentSearchRepository.save(trainingInformationAttachment);
        return result;
    }

    /**
     * Get all the trainingInformationAttachments.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TrainingInformationAttachmentDTO> findAll() {
        log.debug("Request to get all TrainingInformationAttachments");
        return trainingInformationAttachmentRepository.findAll().stream()
            .map(trainingInformationAttachmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one trainingInformationAttachment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TrainingInformationAttachmentDTO> findOne(Long id) {
        log.debug("Request to get TrainingInformationAttachment : {}", id);
        return trainingInformationAttachmentRepository.findById(id)
            .map(trainingInformationAttachmentMapper::toDto);
    }

    /**
     * Delete the trainingInformationAttachment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TrainingInformationAttachment : {}", id);
        trainingInformationAttachmentRepository.deleteById(id);
        trainingInformationAttachmentSearchRepository.deleteById(id);
    }

    /**
     * Search for the trainingInformationAttachment corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TrainingInformationAttachmentDTO> search(String query) {
        log.debug("Request to search TrainingInformationAttachments for query {}", query);
        return StreamSupport
            .stream(trainingInformationAttachmentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(trainingInformationAttachmentMapper::toDto)
            .collect(Collectors.toList());
    }
}
