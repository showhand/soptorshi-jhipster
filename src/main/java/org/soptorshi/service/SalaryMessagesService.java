package org.soptorshi.service;

import org.soptorshi.domain.SalaryMessages;
import org.soptorshi.repository.SalaryMessagesRepository;
import org.soptorshi.repository.search.SalaryMessagesSearchRepository;
import org.soptorshi.service.dto.SalaryMessagesDTO;
import org.soptorshi.service.mapper.SalaryMessagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SalaryMessages.
 */
@Service
@Transactional
public class SalaryMessagesService {

    private final Logger log = LoggerFactory.getLogger(SalaryMessagesService.class);

    private final SalaryMessagesRepository salaryMessagesRepository;

    private final SalaryMessagesMapper salaryMessagesMapper;

    private final SalaryMessagesSearchRepository salaryMessagesSearchRepository;

    public SalaryMessagesService(SalaryMessagesRepository salaryMessagesRepository, SalaryMessagesMapper salaryMessagesMapper, SalaryMessagesSearchRepository salaryMessagesSearchRepository) {
        this.salaryMessagesRepository = salaryMessagesRepository;
        this.salaryMessagesMapper = salaryMessagesMapper;
        this.salaryMessagesSearchRepository = salaryMessagesSearchRepository;
    }

    /**
     * Save a salaryMessages.
     *
     * @param salaryMessagesDTO the entity to save
     * @return the persisted entity
     */
    public SalaryMessagesDTO save(SalaryMessagesDTO salaryMessagesDTO) {
        log.debug("Request to save SalaryMessages : {}", salaryMessagesDTO);
        SalaryMessages salaryMessages = salaryMessagesMapper.toEntity(salaryMessagesDTO);
        salaryMessages = salaryMessagesRepository.save(salaryMessages);
        SalaryMessagesDTO result = salaryMessagesMapper.toDto(salaryMessages);
        salaryMessagesSearchRepository.save(salaryMessages);
        return result;
    }

    /**
     * Get all the salaryMessages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SalaryMessagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SalaryMessages");
        return salaryMessagesRepository.findAll(pageable)
            .map(salaryMessagesMapper::toDto);
    }


    /**
     * Get one salaryMessages by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SalaryMessagesDTO> findOne(Long id) {
        log.debug("Request to get SalaryMessages : {}", id);
        return salaryMessagesRepository.findById(id)
            .map(salaryMessagesMapper::toDto);
    }

    /**
     * Delete the salaryMessages by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SalaryMessages : {}", id);
        salaryMessagesRepository.deleteById(id);
        salaryMessagesSearchRepository.deleteById(id);
    }

    /**
     * Search for the salaryMessages corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SalaryMessagesDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SalaryMessages for query {}", query);
        return salaryMessagesSearchRepository.search(queryStringQuery(query), pageable)
            .map(salaryMessagesMapper::toDto);
    }
}
