package org.soptorshi.service;

import org.soptorshi.domain.CreditorLedger;
import org.soptorshi.repository.CreditorLedgerRepository;
import org.soptorshi.repository.search.CreditorLedgerSearchRepository;
import org.soptorshi.service.dto.CreditorLedgerDTO;
import org.soptorshi.service.mapper.CreditorLedgerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CreditorLedger.
 */
@Service
@Transactional
public class CreditorLedgerService {

    private final Logger log = LoggerFactory.getLogger(CreditorLedgerService.class);

    private final CreditorLedgerRepository creditorLedgerRepository;

    private final CreditorLedgerMapper creditorLedgerMapper;

    private final CreditorLedgerSearchRepository creditorLedgerSearchRepository;

    public CreditorLedgerService(CreditorLedgerRepository creditorLedgerRepository, CreditorLedgerMapper creditorLedgerMapper, CreditorLedgerSearchRepository creditorLedgerSearchRepository) {
        this.creditorLedgerRepository = creditorLedgerRepository;
        this.creditorLedgerMapper = creditorLedgerMapper;
        this.creditorLedgerSearchRepository = creditorLedgerSearchRepository;
    }

    /**
     * Save a creditorLedger.
     *
     * @param creditorLedgerDTO the entity to save
     * @return the persisted entity
     */
    public CreditorLedgerDTO save(CreditorLedgerDTO creditorLedgerDTO) {
        log.debug("Request to save CreditorLedger : {}", creditorLedgerDTO);
        CreditorLedger creditorLedger = creditorLedgerMapper.toEntity(creditorLedgerDTO);
        creditorLedger = creditorLedgerRepository.save(creditorLedger);
        CreditorLedgerDTO result = creditorLedgerMapper.toDto(creditorLedger);
        creditorLedgerSearchRepository.save(creditorLedger);
        return result;
    }

    /**
     * Get all the creditorLedgers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CreditorLedgerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CreditorLedgers");
        return creditorLedgerRepository.findAll(pageable)
            .map(creditorLedgerMapper::toDto);
    }


    /**
     * Get one creditorLedger by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CreditorLedgerDTO> findOne(Long id) {
        log.debug("Request to get CreditorLedger : {}", id);
        return creditorLedgerRepository.findById(id)
            .map(creditorLedgerMapper::toDto);
    }

    /**
     * Delete the creditorLedger by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CreditorLedger : {}", id);
        creditorLedgerRepository.deleteById(id);
        creditorLedgerSearchRepository.deleteById(id);
    }

    /**
     * Search for the creditorLedger corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CreditorLedgerDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CreditorLedgers for query {}", query);
        return creditorLedgerSearchRepository.search(queryStringQuery(query), pageable)
            .map(creditorLedgerMapper::toDto);
    }
}
