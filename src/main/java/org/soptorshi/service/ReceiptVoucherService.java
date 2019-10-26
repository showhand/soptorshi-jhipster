package org.soptorshi.service;

import org.soptorshi.domain.ReceiptVoucher;
import org.soptorshi.repository.ReceiptVoucherRepository;
import org.soptorshi.repository.search.ReceiptVoucherSearchRepository;
import org.soptorshi.service.dto.ReceiptVoucherDTO;
import org.soptorshi.service.mapper.ReceiptVoucherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ReceiptVoucher.
 */
@Service
@Transactional
public class ReceiptVoucherService {

    private final Logger log = LoggerFactory.getLogger(ReceiptVoucherService.class);

    private final ReceiptVoucherRepository receiptVoucherRepository;

    private final ReceiptVoucherMapper receiptVoucherMapper;

    private final ReceiptVoucherSearchRepository receiptVoucherSearchRepository;

    public ReceiptVoucherService(ReceiptVoucherRepository receiptVoucherRepository, ReceiptVoucherMapper receiptVoucherMapper, ReceiptVoucherSearchRepository receiptVoucherSearchRepository) {
        this.receiptVoucherRepository = receiptVoucherRepository;
        this.receiptVoucherMapper = receiptVoucherMapper;
        this.receiptVoucherSearchRepository = receiptVoucherSearchRepository;
    }

    /**
     * Save a receiptVoucher.
     *
     * @param receiptVoucherDTO the entity to save
     * @return the persisted entity
     */
    public ReceiptVoucherDTO save(ReceiptVoucherDTO receiptVoucherDTO) {
        log.debug("Request to save ReceiptVoucher : {}", receiptVoucherDTO);
        ReceiptVoucher receiptVoucher = receiptVoucherMapper.toEntity(receiptVoucherDTO);
        receiptVoucher = receiptVoucherRepository.save(receiptVoucher);
        ReceiptVoucherDTO result = receiptVoucherMapper.toDto(receiptVoucher);
        receiptVoucherSearchRepository.save(receiptVoucher);
        return result;
    }

    /**
     * Get all the receiptVouchers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ReceiptVoucherDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ReceiptVouchers");
        return receiptVoucherRepository.findAll(pageable)
            .map(receiptVoucherMapper::toDto);
    }


    /**
     * Get one receiptVoucher by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ReceiptVoucherDTO> findOne(Long id) {
        log.debug("Request to get ReceiptVoucher : {}", id);
        return receiptVoucherRepository.findById(id)
            .map(receiptVoucherMapper::toDto);
    }

    /**
     * Delete the receiptVoucher by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ReceiptVoucher : {}", id);
        receiptVoucherRepository.deleteById(id);
        receiptVoucherSearchRepository.deleteById(id);
    }

    /**
     * Search for the receiptVoucher corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ReceiptVoucherDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ReceiptVouchers for query {}", query);
        return receiptVoucherSearchRepository.search(queryStringQuery(query), pageable)
            .map(receiptVoucherMapper::toDto);
    }
}
