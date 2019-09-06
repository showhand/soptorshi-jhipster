package org.soptorshi.service;

import org.soptorshi.domain.DebtorLedger;
import org.soptorshi.repository.DebtorLedgerRepository;
import org.soptorshi.repository.search.DebtorLedgerSearchRepository;
import org.soptorshi.service.dto.DebtorLedgerDTO;
import org.soptorshi.service.mapper.DebtorLedgerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DebtorLedger.
 */
@Service
@Transactional
public class DebtorLedgerService {

    private final Logger log = LoggerFactory.getLogger(DebtorLedgerService.class);

    private final DebtorLedgerRepository debtorLedgerRepository;

    private final DebtorLedgerMapper debtorLedgerMapper;

    private final DebtorLedgerSearchRepository debtorLedgerSearchRepository;

    public DebtorLedgerService(DebtorLedgerRepository debtorLedgerRepository, DebtorLedgerMapper debtorLedgerMapper, DebtorLedgerSearchRepository debtorLedgerSearchRepository) {
        this.debtorLedgerRepository = debtorLedgerRepository;
        this.debtorLedgerMapper = debtorLedgerMapper;
        this.debtorLedgerSearchRepository = debtorLedgerSearchRepository;
    }

    /**
     * Save a debtorLedger.
     *
     * @param debtorLedgerDTO the entity to save
     * @return the persisted entity
     */
    public DebtorLedgerDTO save(DebtorLedgerDTO debtorLedgerDTO) {
        log.debug("Request to save DebtorLedger : {}", debtorLedgerDTO);
        DebtorLedger debtorLedger = debtorLedgerMapper.toEntity(debtorLedgerDTO);
        debtorLedger = debtorLedgerRepository.save(debtorLedger);
        DebtorLedgerDTO result = debtorLedgerMapper.toDto(debtorLedger);
        debtorLedgerSearchRepository.save(debtorLedger);
        return result;
    }

    /**
     * Get all the debtorLedgers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DebtorLedgerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DebtorLedgers");
        return debtorLedgerRepository.findAll(pageable)
            .map(debtorLedgerMapper::toDto);
    }


    /**
     * Get one debtorLedger by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DebtorLedgerDTO> findOne(Long id) {
        log.debug("Request to get DebtorLedger : {}", id);
        return debtorLedgerRepository.findById(id)
            .map(debtorLedgerMapper::toDto);
    }

    /**
     * Delete the debtorLedger by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DebtorLedger : {}", id);
        debtorLedgerRepository.deleteById(id);
        debtorLedgerSearchRepository.deleteById(id);
    }

    /**
     * Search for the debtorLedger corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DebtorLedgerDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DebtorLedgers for query {}", query);
        return debtorLedgerSearchRepository.search(queryStringQuery(query), pageable)
            .map(debtorLedgerMapper::toDto);
    }
}
