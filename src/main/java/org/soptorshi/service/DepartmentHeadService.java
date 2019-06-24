package org.soptorshi.service;

import org.soptorshi.domain.DepartmentHead;
import org.soptorshi.repository.DepartmentHeadRepository;
import org.soptorshi.repository.search.DepartmentHeadSearchRepository;
import org.soptorshi.service.dto.DepartmentHeadDTO;
import org.soptorshi.service.mapper.DepartmentHeadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DepartmentHead.
 */
@Service
@Transactional
public class DepartmentHeadService {

    private final Logger log = LoggerFactory.getLogger(DepartmentHeadService.class);

    private final DepartmentHeadRepository departmentHeadRepository;

    private final DepartmentHeadMapper departmentHeadMapper;

    private final DepartmentHeadSearchRepository departmentHeadSearchRepository;

    public DepartmentHeadService(DepartmentHeadRepository departmentHeadRepository, DepartmentHeadMapper departmentHeadMapper, DepartmentHeadSearchRepository departmentHeadSearchRepository) {
        this.departmentHeadRepository = departmentHeadRepository;
        this.departmentHeadMapper = departmentHeadMapper;
        this.departmentHeadSearchRepository = departmentHeadSearchRepository;
    }

    /**
     * Save a departmentHead.
     *
     * @param departmentHeadDTO the entity to save
     * @return the persisted entity
     */
    public DepartmentHeadDTO save(DepartmentHeadDTO departmentHeadDTO) {
        log.debug("Request to save DepartmentHead : {}", departmentHeadDTO);
        DepartmentHead departmentHead = departmentHeadMapper.toEntity(departmentHeadDTO);
        departmentHead = departmentHeadRepository.save(departmentHead);
        DepartmentHeadDTO result = departmentHeadMapper.toDto(departmentHead);
        departmentHeadSearchRepository.save(departmentHead);
        return result;
    }

    /**
     * Get all the departmentHeads.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DepartmentHeadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DepartmentHeads");
        return departmentHeadRepository.findAll(pageable)
            .map(departmentHeadMapper::toDto);
    }


    /**
     * Get one departmentHead by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DepartmentHeadDTO> findOne(Long id) {
        log.debug("Request to get DepartmentHead : {}", id);
        return departmentHeadRepository.findById(id)
            .map(departmentHeadMapper::toDto);
    }

    /**
     * Delete the departmentHead by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DepartmentHead : {}", id);
        departmentHeadRepository.deleteById(id);
        departmentHeadSearchRepository.deleteById(id);
    }

    /**
     * Search for the departmentHead corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DepartmentHeadDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DepartmentHeads for query {}", query);
        return departmentHeadSearchRepository.search(queryStringQuery(query), pageable)
            .map(departmentHeadMapper::toDto);
    }
}
