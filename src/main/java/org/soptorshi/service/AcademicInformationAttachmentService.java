package org.soptorshi.service;

import org.soptorshi.domain.AcademicInformationAttachment;
import org.soptorshi.repository.AcademicInformationAttachmentRepository;
import org.soptorshi.repository.search.AcademicInformationAttachmentSearchRepository;
import org.soptorshi.service.dto.AcademicInformationAttachmentDTO;
import org.soptorshi.service.mapper.AcademicInformationAttachmentMapper;
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
 * Service Implementation for managing AcademicInformationAttachment.
 */
@Service
@Transactional
public class AcademicInformationAttachmentService {

    private final Logger log = LoggerFactory.getLogger(AcademicInformationAttachmentService.class);

    private final AcademicInformationAttachmentRepository academicInformationAttachmentRepository;

    private final AcademicInformationAttachmentMapper academicInformationAttachmentMapper;

    private final AcademicInformationAttachmentSearchRepository academicInformationAttachmentSearchRepository;

    public AcademicInformationAttachmentService(AcademicInformationAttachmentRepository academicInformationAttachmentRepository, AcademicInformationAttachmentMapper academicInformationAttachmentMapper, AcademicInformationAttachmentSearchRepository academicInformationAttachmentSearchRepository) {
        this.academicInformationAttachmentRepository = academicInformationAttachmentRepository;
        this.academicInformationAttachmentMapper = academicInformationAttachmentMapper;
        this.academicInformationAttachmentSearchRepository = academicInformationAttachmentSearchRepository;
    }

    /**
     * Save a academicInformationAttachment.
     *
     * @param academicInformationAttachmentDTO the entity to save
     * @return the persisted entity
     */
    public AcademicInformationAttachmentDTO save(AcademicInformationAttachmentDTO academicInformationAttachmentDTO) {
        log.debug("Request to save AcademicInformationAttachment : {}", academicInformationAttachmentDTO);
        AcademicInformationAttachment academicInformationAttachment = academicInformationAttachmentMapper.toEntity(academicInformationAttachmentDTO);
        academicInformationAttachment = academicInformationAttachmentRepository.save(academicInformationAttachment);
        AcademicInformationAttachmentDTO result = academicInformationAttachmentMapper.toDto(academicInformationAttachment);
        academicInformationAttachmentSearchRepository.save(academicInformationAttachment);
        return result;
    }

    /**
     * Get all the academicInformationAttachments.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AcademicInformationAttachmentDTO> findAll() {
        log.debug("Request to get all AcademicInformationAttachments");
        return academicInformationAttachmentRepository.findAll().stream()
            .map(academicInformationAttachmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<AcademicInformationAttachmentDTO> findAllByEmployeeId(Long employeeId){
        log.debug("Requested to get all attachment for employee id-->"+employeeId);
        return academicInformationAttachmentRepository.findByEmployeeId(employeeId)
            .stream()
            .map(academicInformationAttachmentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one academicInformationAttachment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AcademicInformationAttachmentDTO> findOne(Long id) {
        log.debug("Request to get AcademicInformationAttachment : {}", id);
        return academicInformationAttachmentRepository.findById(id)
            .map(academicInformationAttachmentMapper::toDto);
    }

    /**
     * Delete the academicInformationAttachment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AcademicInformationAttachment : {}", id);
        academicInformationAttachmentRepository.deleteById(id);
        academicInformationAttachmentSearchRepository.deleteById(id);
    }

    /**
     * Search for the academicInformationAttachment corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AcademicInformationAttachmentDTO> search(String query) {
        log.debug("Request to search AcademicInformationAttachments for query {}", query);
        return StreamSupport
            .stream(academicInformationAttachmentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(academicInformationAttachmentMapper::toDto)
            .collect(Collectors.toList());
    }
}
