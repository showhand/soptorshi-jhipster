package org.soptorshi.service;

import org.soptorshi.domain.DtTransaction;
import org.soptorshi.repository.DtTransactionRepository;
import org.soptorshi.repository.search.DtTransactionSearchRepository;
import org.soptorshi.service.dto.DtTransactionDTO;
import org.soptorshi.service.mapper.DtTransactionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DtTransaction.
 */
@Service
@Transactional
public class DtTransactionService {

    private final Logger log = LoggerFactory.getLogger(DtTransactionService.class);

    private final DtTransactionRepository dtTransactionRepository;

    private final DtTransactionMapper dtTransactionMapper;

    private final DtTransactionSearchRepository dtTransactionSearchRepository;

    public DtTransactionService(DtTransactionRepository dtTransactionRepository, DtTransactionMapper dtTransactionMapper, DtTransactionSearchRepository dtTransactionSearchRepository) {
        this.dtTransactionRepository = dtTransactionRepository;
        this.dtTransactionMapper = dtTransactionMapper;
        this.dtTransactionSearchRepository = dtTransactionSearchRepository;
    }

    /**
     * Save a dtTransaction.
     *
     * @param dtTransactionDTO the entity to save
     * @return the persisted entity
     */
    public DtTransactionDTO save(DtTransactionDTO dtTransactionDTO) {
        log.debug("Request to save DtTransaction : {}", dtTransactionDTO);
        DtTransaction dtTransaction = dtTransactionMapper.toEntity(dtTransactionDTO);
        dtTransaction = dtTransactionRepository.save(dtTransaction);
        dtTransactionRepository.flush();
        DtTransactionDTO result = dtTransactionMapper.toDto(dtTransaction);
        dtTransactionSearchRepository.save(dtTransaction);
        return result;
    }

    /**
     * Get all the dtTransactions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DtTransactionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DtTransactions");
        return dtTransactionRepository.findAll(pageable)
            .map(dtTransactionMapper::toDto);
    }


    /**
     * Get one dtTransaction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DtTransactionDTO> findOne(Long id) {
        log.debug("Request to get DtTransaction : {}", id);
        return dtTransactionRepository.findById(id)
            .map(dtTransactionMapper::toDto);
    }

    /**
     * Delete the dtTransaction by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DtTransaction : {}", id);
        dtTransactionRepository.deleteById(id);
        dtTransactionSearchRepository.deleteById(id);
    }

    /**
     * Search for the dtTransaction corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DtTransactionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DtTransactions for query {}", query);
        return dtTransactionSearchRepository.search(queryStringQuery(query), pageable)
            .map(dtTransactionMapper::toDto);
    }
}
