package org.soptorshi.service;

import org.soptorshi.domain.DesignationWiseAllowance;
import org.soptorshi.repository.DesignationWiseAllowanceRepository;
import org.soptorshi.repository.search.DesignationWiseAllowanceSearchRepository;
import org.soptorshi.service.dto.DesignationWiseAllowanceDTO;
import org.soptorshi.service.mapper.DesignationWiseAllowanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DesignationWiseAllowance.
 */
@Service
@Transactional
public class DesignationWiseAllowanceService {

    private final Logger log = LoggerFactory.getLogger(DesignationWiseAllowanceService.class);

    private final DesignationWiseAllowanceRepository designationWiseAllowanceRepository;

    private final DesignationWiseAllowanceMapper designationWiseAllowanceMapper;

    private final DesignationWiseAllowanceSearchRepository designationWiseAllowanceSearchRepository;

    public DesignationWiseAllowanceService(DesignationWiseAllowanceRepository designationWiseAllowanceRepository, DesignationWiseAllowanceMapper designationWiseAllowanceMapper, DesignationWiseAllowanceSearchRepository designationWiseAllowanceSearchRepository) {
        this.designationWiseAllowanceRepository = designationWiseAllowanceRepository;
        this.designationWiseAllowanceMapper = designationWiseAllowanceMapper;
        this.designationWiseAllowanceSearchRepository = designationWiseAllowanceSearchRepository;
    }

    /**
     * Save a designationWiseAllowance.
     *
     * @param designationWiseAllowanceDTO the entity to save
     * @return the persisted entity
     */
    public DesignationWiseAllowanceDTO save(DesignationWiseAllowanceDTO designationWiseAllowanceDTO) {
        log.debug("Request to save DesignationWiseAllowance : {}", designationWiseAllowanceDTO);
        DesignationWiseAllowance designationWiseAllowance = designationWiseAllowanceMapper.toEntity(designationWiseAllowanceDTO);
        designationWiseAllowance = designationWiseAllowanceRepository.save(designationWiseAllowance);
        DesignationWiseAllowanceDTO result = designationWiseAllowanceMapper.toDto(designationWiseAllowance);
        designationWiseAllowance.setModifiedOn(LocalDate.now());
        designationWiseAllowanceSearchRepository.save(designationWiseAllowance);
        return result;
    }

    /**
     * Get all the designationWiseAllowances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignationWiseAllowanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DesignationWiseAllowances");
        return designationWiseAllowanceRepository.findAll(pageable)
            .map(designationWiseAllowanceMapper::toDto);
    }


    /**
     * Get one designationWiseAllowance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DesignationWiseAllowanceDTO> findOne(Long id) {
        log.debug("Request to get DesignationWiseAllowance : {}", id);
        return designationWiseAllowanceRepository.findById(id)
            .map(designationWiseAllowanceMapper::toDto);
    }

    public List<DesignationWiseAllowance> get(Long designationId){
        return designationWiseAllowanceRepository.getByDesignation_Id(designationId);
    }

    /**
     * Delete the designationWiseAllowance by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DesignationWiseAllowance : {}", id);
        designationWiseAllowanceRepository.deleteById(id);
        designationWiseAllowanceSearchRepository.deleteById(id);
    }

    /**
     * Search for the designationWiseAllowance corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DesignationWiseAllowanceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DesignationWiseAllowances for query {}", query);
        return designationWiseAllowanceSearchRepository.search(queryStringQuery(query), pageable)
            .map(designationWiseAllowanceMapper::toDto);
    }
}
