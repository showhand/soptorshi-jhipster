package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.LeaveApplication;
import org.soptorshi.repository.LeaveApplicationRepository;
import org.soptorshi.repository.search.LeaveApplicationSearchRepository;
import org.soptorshi.service.dto.LeaveApplicationDTO;
import org.soptorshi.service.mapper.LeaveApplicationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing LeaveApplication.
 */
@Service
@Transactional
public class LeaveApplicationService {

    private final Logger log = LoggerFactory.getLogger(LeaveApplicationService.class);

    private final LeaveApplicationRepository leaveApplicationRepository;

    private final LeaveApplicationMapper leaveApplicationMapper;

    private final LeaveApplicationSearchRepository leaveApplicationSearchRepository;

    public LeaveApplicationService(LeaveApplicationRepository leaveApplicationRepository, LeaveApplicationMapper leaveApplicationMapper, LeaveApplicationSearchRepository leaveApplicationSearchRepository) {
        this.leaveApplicationRepository = leaveApplicationRepository;
        this.leaveApplicationMapper = leaveApplicationMapper;
        this.leaveApplicationSearchRepository = leaveApplicationSearchRepository;
    }

    /**
     * Save a leaveApplication.
     *
     * @param leaveApplicationDTO the entity to save
     * @return the persisted entity
     */
    public LeaveApplicationDTO save(LeaveApplicationDTO leaveApplicationDTO) {
        log.debug("Request to save LeaveApplication : {}", leaveApplicationDTO);
        LeaveApplication leaveApplication = leaveApplicationMapper.toEntity(leaveApplicationDTO);
        leaveApplication = leaveApplicationRepository.save(leaveApplication);
        LeaveApplicationDTO result = leaveApplicationMapper.toDto(leaveApplication);
        leaveApplicationSearchRepository.save(leaveApplication);
        return result;
    }

    /**
     * Get all the leaveApplications.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LeaveApplicationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LeaveApplications");
        return leaveApplicationRepository.findAll(pageable)
            .map(leaveApplicationMapper::toDto);
    }


    /**
     * Get one leaveApplication by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<LeaveApplicationDTO> findOne(Long id) {
        log.debug("Request to get LeaveApplication : {}", id);
        return leaveApplicationRepository.findById(id)
            .map(leaveApplicationMapper::toDto);
    }

    /**
     * Delete the leaveApplication by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete LeaveApplication : {}", id);
        leaveApplicationRepository.deleteById(id);
        leaveApplicationSearchRepository.deleteById(id);
    }

    /**
     * Search for the leaveApplication corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LeaveApplicationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LeaveApplications for query {}", query);
        return leaveApplicationSearchRepository.search(queryStringQuery(query), pageable)
            .map(leaveApplicationMapper::toDto);
    }
}
