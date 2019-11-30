package org.soptorshi.service;

import org.soptorshi.domain.SalaryVoucherRelation;
import org.soptorshi.repository.SalaryVoucherRelationRepository;
import org.soptorshi.repository.search.SalaryVoucherRelationSearchRepository;
import org.soptorshi.service.dto.SalaryVoucherRelationDTO;
import org.soptorshi.service.mapper.SalaryVoucherRelationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SalaryVoucherRelation.
 */
@Service
@Transactional
public class SalaryVoucherRelationService {

    private final Logger log = LoggerFactory.getLogger(SalaryVoucherRelationService.class);

    private final SalaryVoucherRelationRepository salaryVoucherRelationRepository;

    private final SalaryVoucherRelationMapper salaryVoucherRelationMapper;

    private final SalaryVoucherRelationSearchRepository salaryVoucherRelationSearchRepository;

    public SalaryVoucherRelationService(SalaryVoucherRelationRepository salaryVoucherRelationRepository, SalaryVoucherRelationMapper salaryVoucherRelationMapper, SalaryVoucherRelationSearchRepository salaryVoucherRelationSearchRepository) {
        this.salaryVoucherRelationRepository = salaryVoucherRelationRepository;
        this.salaryVoucherRelationMapper = salaryVoucherRelationMapper;
        this.salaryVoucherRelationSearchRepository = salaryVoucherRelationSearchRepository;
    }

    /**
     * Save a salaryVoucherRelation.
     *
     * @param salaryVoucherRelationDTO the entity to save
     * @return the persisted entity
     */
    public SalaryVoucherRelationDTO save(SalaryVoucherRelationDTO salaryVoucherRelationDTO) {
        log.debug("Request to save SalaryVoucherRelation : {}", salaryVoucherRelationDTO);
        SalaryVoucherRelation salaryVoucherRelation = salaryVoucherRelationMapper.toEntity(salaryVoucherRelationDTO);
        salaryVoucherRelation = salaryVoucherRelationRepository.save(salaryVoucherRelation);
        SalaryVoucherRelationDTO result = salaryVoucherRelationMapper.toDto(salaryVoucherRelation);
        salaryVoucherRelationSearchRepository.save(salaryVoucherRelation);
        return result;
    }

    /**
     * Get all the salaryVoucherRelations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SalaryVoucherRelationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SalaryVoucherRelations");
        return salaryVoucherRelationRepository.findAll(pageable)
            .map(salaryVoucherRelationMapper::toDto);
    }


    /**
     * Get one salaryVoucherRelation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SalaryVoucherRelationDTO> findOne(Long id) {
        log.debug("Request to get SalaryVoucherRelation : {}", id);
        return salaryVoucherRelationRepository.findById(id)
            .map(salaryVoucherRelationMapper::toDto);
    }

    /**
     * Delete the salaryVoucherRelation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SalaryVoucherRelation : {}", id);
        salaryVoucherRelationRepository.deleteById(id);
        salaryVoucherRelationSearchRepository.deleteById(id);
    }

    /**
     * Search for the salaryVoucherRelation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SalaryVoucherRelationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SalaryVoucherRelations for query {}", query);
        return salaryVoucherRelationSearchRepository.search(queryStringQuery(query), pageable)
            .map(salaryVoucherRelationMapper::toDto);
    }
}
