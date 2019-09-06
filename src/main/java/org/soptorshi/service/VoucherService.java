package org.soptorshi.service;

import org.soptorshi.domain.Voucher;
import org.soptorshi.repository.VoucherRepository;
import org.soptorshi.repository.search.VoucherSearchRepository;
import org.soptorshi.service.dto.VoucherDTO;
import org.soptorshi.service.mapper.VoucherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Voucher.
 */
@Service
@Transactional
public class VoucherService {

    private final Logger log = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    private final VoucherMapper voucherMapper;

    private final VoucherSearchRepository voucherSearchRepository;

    public VoucherService(VoucherRepository voucherRepository, VoucherMapper voucherMapper, VoucherSearchRepository voucherSearchRepository) {
        this.voucherRepository = voucherRepository;
        this.voucherMapper = voucherMapper;
        this.voucherSearchRepository = voucherSearchRepository;
    }

    /**
     * Save a voucher.
     *
     * @param voucherDTO the entity to save
     * @return the persisted entity
     */
    public VoucherDTO save(VoucherDTO voucherDTO) {
        log.debug("Request to save Voucher : {}", voucherDTO);
        Voucher voucher = voucherMapper.toEntity(voucherDTO);
        voucher = voucherRepository.save(voucher);
        VoucherDTO result = voucherMapper.toDto(voucher);
        voucherSearchRepository.save(voucher);
        return result;
    }

    /**
     * Get all the vouchers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VoucherDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vouchers");
        return voucherRepository.findAll(pageable)
            .map(voucherMapper::toDto);
    }


    /**
     * Get one voucher by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<VoucherDTO> findOne(Long id) {
        log.debug("Request to get Voucher : {}", id);
        return voucherRepository.findById(id)
            .map(voucherMapper::toDto);
    }

    /**
     * Delete the voucher by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Voucher : {}", id);
        voucherRepository.deleteById(id);
        voucherSearchRepository.deleteById(id);
    }

    /**
     * Search for the voucher corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VoucherDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Vouchers for query {}", query);
        return voucherSearchRepository.search(queryStringQuery(query), pageable)
            .map(voucherMapper::toDto);
    }
}
