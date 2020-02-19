package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.LeaveType;
import org.soptorshi.repository.LeaveTypeRepository;
import org.soptorshi.repository.search.LeaveTypeSearchRepository;
import org.soptorshi.service.dto.LeaveTypeDTO;
import org.soptorshi.service.mapper.LeaveTypeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing LeaveType.
 */
@Service
@Transactional
public class LeaveTypeService {

    private final Logger log = LoggerFactory.getLogger(LeaveTypeService.class);

    private final LeaveTypeRepository leaveTypeRepository;

    private final LeaveTypeMapper leaveTypeMapper;

    private final LeaveTypeSearchRepository leaveTypeSearchRepository;

    public LeaveTypeService(LeaveTypeRepository leaveTypeRepository, LeaveTypeMapper leaveTypeMapper, LeaveTypeSearchRepository leaveTypeSearchRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
        this.leaveTypeMapper = leaveTypeMapper;
        this.leaveTypeSearchRepository = leaveTypeSearchRepository;
    }

    /**
     * Save a leaveType.
     *
     * @param leaveTypeDTO the entity to save
     * @return the persisted entity
     */
    public LeaveTypeDTO save(LeaveTypeDTO leaveTypeDTO) {
        log.debug("Request to save LeaveType : {}", leaveTypeDTO);
        LeaveType leaveType = leaveTypeMapper.toEntity(leaveTypeDTO);
        leaveType = leaveTypeRepository.save(leaveType);
        LeaveTypeDTO result = leaveTypeMapper.toDto(leaveType);
        leaveTypeSearchRepository.save(leaveType);
        return result;
    }

    /**
     * Get all the leaveTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LeaveTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveTypes");
        return leaveTypeRepository.findAll(pageable)
            .map(leaveTypeMapper::toDto);
    }


    /**
     * Get one leaveType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<LeaveTypeDTO> findOne(Long id) {
        log.debug("Request to get LeaveType : {}", id);
        return leaveTypeRepository.findById(id)
            .map(leaveTypeMapper::toDto);
    }

    /**
     * Delete the leaveType by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveType : {}", id);
        leaveTypeRepository.deleteById(id);
        leaveTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the leaveType corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LeaveTypeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LeaveTypes for query {}", query);
        return leaveTypeSearchRepository.search(queryStringQuery(query), pageable)
            .map(leaveTypeMapper::toDto);
    }
}
