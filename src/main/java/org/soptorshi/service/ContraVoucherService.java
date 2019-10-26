package org.soptorshi.service;

import org.soptorshi.domain.ContraVoucher;
import org.soptorshi.repository.ContraVoucherRepository;
import org.soptorshi.repository.search.ContraVoucherSearchRepository;
import org.soptorshi.service.dto.ContraVoucherDTO;
import org.soptorshi.service.mapper.ContraVoucherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ContraVoucher.
 */
@Service
@Transactional
public class ContraVoucherService {

    private final Logger log = LoggerFactory.getLogger(ContraVoucherService.class);

    private final ContraVoucherRepository contraVoucherRepository;

    private final ContraVoucherMapper contraVoucherMapper;

    private final ContraVoucherSearchRepository contraVoucherSearchRepository;

    public ContraVoucherService(ContraVoucherRepository contraVoucherRepository, ContraVoucherMapper contraVoucherMapper, ContraVoucherSearchRepository contraVoucherSearchRepository) {
        this.contraVoucherRepository = contraVoucherRepository;
        this.contraVoucherMapper = contraVoucherMapper;
        this.contraVoucherSearchRepository = contraVoucherSearchRepository;
    }

    /**
     * Save a contraVoucher.
     *
     * @param contraVoucherDTO the entity to save
     * @return the persisted entity
     */
    public ContraVoucherDTO save(ContraVoucherDTO contraVoucherDTO) {
        log.debug("Request to save ContraVoucher : {}", contraVoucherDTO);
        ContraVoucher contraVoucher = contraVoucherMapper.toEntity(contraVoucherDTO);
        contraVoucher = contraVoucherRepository.save(contraVoucher);
        ContraVoucherDTO result = contraVoucherMapper.toDto(contraVoucher);
        contraVoucherSearchRepository.save(contraVoucher);
        return result;
    }

    /**
     * Get all the contraVouchers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ContraVoucherDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContraVouchers");
        return contraVoucherRepository.findAll(pageable)
            .map(contraVoucherMapper::toDto);
    }


    /**
     * Get one contraVoucher by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ContraVoucherDTO> findOne(Long id) {
        log.debug("Request to get ContraVoucher : {}", id);
        return contraVoucherRepository.findById(id)
            .map(contraVoucherMapper::toDto);
    }

    /**
     * Delete the contraVoucher by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ContraVoucher : {}", id);
        contraVoucherRepository.deleteById(id);
        contraVoucherSearchRepository.deleteById(id);
    }

    /**
     * Search for the contraVoucher corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ContraVoucherDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ContraVouchers for query {}", query);
        return contraVoucherSearchRepository.search(queryStringQuery(query), pageable)
            .map(contraVoucherMapper::toDto);
    }
}
