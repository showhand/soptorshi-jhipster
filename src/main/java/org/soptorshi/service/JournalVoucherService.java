package org.soptorshi.service;

import org.soptorshi.domain.JournalVoucher;
import org.soptorshi.repository.JournalVoucherRepository;
import org.soptorshi.repository.search.JournalVoucherSearchRepository;
import org.soptorshi.service.dto.JournalVoucherDTO;
import org.soptorshi.service.mapper.JournalVoucherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing JournalVoucher.
 */
@Service
@Transactional
public class JournalVoucherService {

    private final Logger log = LoggerFactory.getLogger(JournalVoucherService.class);

    private final JournalVoucherRepository journalVoucherRepository;

    private final JournalVoucherMapper journalVoucherMapper;

    private final JournalVoucherSearchRepository journalVoucherSearchRepository;

    public JournalVoucherService(JournalVoucherRepository journalVoucherRepository, JournalVoucherMapper journalVoucherMapper, JournalVoucherSearchRepository journalVoucherSearchRepository) {
        this.journalVoucherRepository = journalVoucherRepository;
        this.journalVoucherMapper = journalVoucherMapper;
        this.journalVoucherSearchRepository = journalVoucherSearchRepository;
    }

    /**
     * Save a journalVoucher.
     *
     * @param journalVoucherDTO the entity to save
     * @return the persisted entity
     */
    public JournalVoucherDTO save(JournalVoucherDTO journalVoucherDTO) {
        log.debug("Request to save JournalVoucher : {}", journalVoucherDTO);
        JournalVoucher journalVoucher = journalVoucherMapper.toEntity(journalVoucherDTO);
        journalVoucher = journalVoucherRepository.saveAndFlush(journalVoucher);
        JournalVoucherDTO result = journalVoucherMapper.toDto(journalVoucher);
        journalVoucherSearchRepository.save(journalVoucher);
        return result;
    }

    /**
     * Get all the journalVouchers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<JournalVoucherDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JournalVouchers");
        return journalVoucherRepository.findAll(pageable)
            .map(journalVoucherMapper::toDto);
    }


    /**
     * Get one journalVoucher by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<JournalVoucherDTO> findOne(Long id) {
        log.debug("Request to get JournalVoucher : {}", id);
        return journalVoucherRepository.findById(id)
            .map(journalVoucherMapper::toDto);
    }

    /**
     * Delete the journalVoucher by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete JournalVoucher : {}", id);
        journalVoucherRepository.deleteById(id);
        journalVoucherSearchRepository.deleteById(id);
    }

    /**
     * Search for the journalVoucher corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<JournalVoucherDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of JournalVouchers for query {}", query);
        return journalVoucherSearchRepository.search(queryStringQuery(query), pageable)
            .map(journalVoucherMapper::toDto);
    }
}
