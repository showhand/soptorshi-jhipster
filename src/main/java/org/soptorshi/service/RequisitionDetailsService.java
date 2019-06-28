package org.soptorshi.service;

import org.soptorshi.domain.RequisitionDetails;
import org.soptorshi.repository.RequisitionDetailsRepository;
import org.soptorshi.repository.search.RequisitionDetailsSearchRepository;
import org.soptorshi.service.dto.RequisitionDetailsDTO;
import org.soptorshi.service.mapper.RequisitionDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RequisitionDetails.
 */
@Service
@Transactional
public class RequisitionDetailsService {

    private final Logger log = LoggerFactory.getLogger(RequisitionDetailsService.class);

    private final RequisitionDetailsRepository requisitionDetailsRepository;

    private final RequisitionDetailsMapper requisitionDetailsMapper;

    private final RequisitionDetailsSearchRepository requisitionDetailsSearchRepository;

    public RequisitionDetailsService(RequisitionDetailsRepository requisitionDetailsRepository, RequisitionDetailsMapper requisitionDetailsMapper, RequisitionDetailsSearchRepository requisitionDetailsSearchRepository) {
        this.requisitionDetailsRepository = requisitionDetailsRepository;
        this.requisitionDetailsMapper = requisitionDetailsMapper;
        this.requisitionDetailsSearchRepository = requisitionDetailsSearchRepository;
    }

    /**
     * Save a requisitionDetails.
     *
     * @param requisitionDetailsDTO the entity to save
     * @return the persisted entity
     */
    public RequisitionDetailsDTO save(RequisitionDetailsDTO requisitionDetailsDTO) {
        log.debug("Request to save RequisitionDetails : {}", requisitionDetailsDTO);
        RequisitionDetails requisitionDetails = requisitionDetailsMapper.toEntity(requisitionDetailsDTO);
        requisitionDetails = requisitionDetailsRepository.save(requisitionDetails);
        RequisitionDetailsDTO result = requisitionDetailsMapper.toDto(requisitionDetails);
        requisitionDetailsSearchRepository.save(requisitionDetails);
        return result;
    }

    /**
     * Get all the requisitionDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RequisitionDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequisitionDetails");
        return requisitionDetailsRepository.findAll(pageable)
            .map(requisitionDetailsMapper::toDto);
    }


    /**
     * Get one requisitionDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RequisitionDetailsDTO> findOne(Long id) {
        log.debug("Request to get RequisitionDetails : {}", id);
        return requisitionDetailsRepository.findById(id)
            .map(requisitionDetailsMapper::toDto);
    }

    /**
     * Delete the requisitionDetails by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RequisitionDetails : {}", id);
        requisitionDetailsRepository.deleteById(id);
        requisitionDetailsSearchRepository.deleteById(id);
    }

    /**
     * Search for the requisitionDetails corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RequisitionDetailsDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RequisitionDetails for query {}", query);
        return requisitionDetailsSearchRepository.search(queryStringQuery(query), pageable)
            .map(requisitionDetailsMapper::toDto);
    }
}
