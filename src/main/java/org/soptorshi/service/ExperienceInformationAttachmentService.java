package org.soptorshi.service;

import org.soptorshi.domain.ExperienceInformationAttachment;
import org.soptorshi.repository.ExperienceInformationAttachmentRepository;
import org.soptorshi.repository.search.ExperienceInformationAttachmentSearchRepository;
import org.soptorshi.service.dto.ExperienceInformationAttachmentDTO;
import org.soptorshi.service.mapper.ExperienceInformationAttachmentMapper;
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
 * Service Implementation for managing ExperienceInformationAttachment.
 */
@Service
@Transactional
public class ExperienceInformationAttachmentService {

    private final Logger log = LoggerFactory.getLogger(ExperienceInformationAttachmentService.class);

    private final ExperienceInformationAttachmentRepository experienceInformationAttachmentRepository;

    private final ExperienceInformationAttachmentMapper experienceInformationAttachmentMapper;

    private final ExperienceInformationAttachmentSearchRepository experienceInformationAttachmentSearchRepository;

    public ExperienceInformationAttachmentService(ExperienceInformationAttachmentRepository experienceInformationAttachmentRepository, ExperienceInformationAttachmentMapper experienceInformationAttachmentMapper, ExperienceInformationAttachmentSearchRepository experienceInformationAttachmentSearchRepository) {
        this.experienceInformationAttachmentRepository = experienceInformationAttachmentRepository;
        this.experienceInformationAttachmentMapper = experienceInformationAttachmentMapper;
        this.experienceInformationAttachmentSearchRepository = experienceInformationAttachmentSearchRepository;
    }

    /**
     * Save a experienceInformationAttachment.
     *
     * @param experienceInformationAttachmentDTO the entity to save
     * @return the persisted entity
     */
    public ExperienceInformationAttachmentDTO save(ExperienceInformationAttachmentDTO experienceInformationAttachmentDTO) {
        log.debug("Request to save ExperienceInformationAttachment : {}", experienceInformationAttachmentDTO);
        ExperienceInformationAttachment experienceInformationAttachment = experienceInformationAttachmentMapper.toEntity(experienceInformationAttachmentDTO);
        experienceInformationAttachment = experienceInformationAttachmentRepository.save(experienceInformationAttachment);
        ExperienceInformationAttachmentDTO result = experienceInformationAttachmentMapper.toDto(experienceInformationAttachment);
        experienceInformationAttachmentSearchRepository.save(experienceInformationAttachment);
        return result;
    }

    /**
     * Get all the experienceInformationAttachments.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ExperienceInformationAttachmentDTO> findAll() {
        log.debug("Request to get all ExperienceInformationAttachments");
        return experienceInformationAttachmentRepository.findAll().stream()
            .map(experienceInformationAttachmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one experienceInformationAttachment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ExperienceInformationAttachmentDTO> findOne(Long id) {
        log.debug("Request to get ExperienceInformationAttachment : {}", id);
        return experienceInformationAttachmentRepository.findById(id)
            .map(experienceInformationAttachmentMapper::toDto);
    }

    /**
     * Delete the experienceInformationAttachment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ExperienceInformationAttachment : {}", id);
        experienceInformationAttachmentRepository.deleteById(id);
        experienceInformationAttachmentSearchRepository.deleteById(id);
    }

    /**
     * Search for the experienceInformationAttachment corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ExperienceInformationAttachmentDTO> search(String query) {
        log.debug("Request to search ExperienceInformationAttachments for query {}", query);
        return StreamSupport
            .stream(experienceInformationAttachmentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(experienceInformationAttachmentMapper::toDto)
            .collect(Collectors.toList());
    }
}
