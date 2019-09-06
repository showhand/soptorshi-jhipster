package org.soptorshi.service;

import org.soptorshi.domain.VoucherNumberControl;
import org.soptorshi.repository.VoucherNumberControlRepository;
import org.soptorshi.repository.search.VoucherNumberControlSearchRepository;
import org.soptorshi.service.dto.VoucherNumberControlDTO;
import org.soptorshi.service.mapper.VoucherNumberControlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing VoucherNumberControl.
 */
@Service
@Transactional
public class VoucherNumberControlService {

    private final Logger log = LoggerFactory.getLogger(VoucherNumberControlService.class);

    private final VoucherNumberControlRepository voucherNumberControlRepository;

    private final VoucherNumberControlMapper voucherNumberControlMapper;

    private final VoucherNumberControlSearchRepository voucherNumberControlSearchRepository;

    public VoucherNumberControlService(VoucherNumberControlRepository voucherNumberControlRepository, VoucherNumberControlMapper voucherNumberControlMapper, VoucherNumberControlSearchRepository voucherNumberControlSearchRepository) {
        this.voucherNumberControlRepository = voucherNumberControlRepository;
        this.voucherNumberControlMapper = voucherNumberControlMapper;
        this.voucherNumberControlSearchRepository = voucherNumberControlSearchRepository;
    }

    /**
     * Save a voucherNumberControl.
     *
     * @param voucherNumberControlDTO the entity to save
     * @return the persisted entity
     */
    public VoucherNumberControlDTO save(VoucherNumberControlDTO voucherNumberControlDTO) {
        log.debug("Request to save VoucherNumberControl : {}", voucherNumberControlDTO);
        VoucherNumberControl voucherNumberControl = voucherNumberControlMapper.toEntity(voucherNumberControlDTO);
        voucherNumberControl = voucherNumberControlRepository.save(voucherNumberControl);
        VoucherNumberControlDTO result = voucherNumberControlMapper.toDto(voucherNumberControl);
        voucherNumberControlSearchRepository.save(voucherNumberControl);
        return result;
    }

    /**
     * Get all the voucherNumberControls.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VoucherNumberControlDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VoucherNumberControls");
        return voucherNumberControlRepository.findAll(pageable)
            .map(voucherNumberControlMapper::toDto);
    }


    /**
     * Get one voucherNumberControl by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<VoucherNumberControlDTO> findOne(Long id) {
        log.debug("Request to get VoucherNumberControl : {}", id);
        return voucherNumberControlRepository.findById(id)
            .map(voucherNumberControlMapper::toDto);
    }

    /**
     * Delete the voucherNumberControl by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete VoucherNumberControl : {}", id);
        voucherNumberControlRepository.deleteById(id);
        voucherNumberControlSearchRepository.deleteById(id);
    }

    /**
     * Search for the voucherNumberControl corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VoucherNumberControlDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of VoucherNumberControls for query {}", query);
        return voucherNumberControlSearchRepository.search(queryStringQuery(query), pageable)
            .map(voucherNumberControlMapper::toDto);
    }
}
