package org.soptorshi.service;

import org.soptorshi.domain.ChequeRegister;
import org.soptorshi.repository.ChequeRegisterRepository;
import org.soptorshi.repository.search.ChequeRegisterSearchRepository;
import org.soptorshi.service.dto.ChequeRegisterDTO;
import org.soptorshi.service.mapper.ChequeRegisterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ChequeRegister.
 */
@Service
@Transactional
public class ChequeRegisterService {

    private final Logger log = LoggerFactory.getLogger(ChequeRegisterService.class);

    private final ChequeRegisterRepository chequeRegisterRepository;

    private final ChequeRegisterMapper chequeRegisterMapper;

    private final ChequeRegisterSearchRepository chequeRegisterSearchRepository;

    public ChequeRegisterService(ChequeRegisterRepository chequeRegisterRepository, ChequeRegisterMapper chequeRegisterMapper, ChequeRegisterSearchRepository chequeRegisterSearchRepository) {
        this.chequeRegisterRepository = chequeRegisterRepository;
        this.chequeRegisterMapper = chequeRegisterMapper;
        this.chequeRegisterSearchRepository = chequeRegisterSearchRepository;
    }

    /**
     * Save a chequeRegister.
     *
     * @param chequeRegisterDTO the entity to save
     * @return the persisted entity
     */
    public ChequeRegisterDTO save(ChequeRegisterDTO chequeRegisterDTO) {
        log.debug("Request to save ChequeRegister : {}", chequeRegisterDTO);
        ChequeRegister chequeRegister = chequeRegisterMapper.toEntity(chequeRegisterDTO);
        chequeRegister = chequeRegisterRepository.save(chequeRegister);
        ChequeRegisterDTO result = chequeRegisterMapper.toDto(chequeRegister);
        chequeRegisterSearchRepository.save(chequeRegister);
        return result;
    }

    /**
     * Get all the chequeRegisters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ChequeRegisterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChequeRegisters");
        return chequeRegisterRepository.findAll(pageable)
            .map(chequeRegisterMapper::toDto);
    }


    /**
     * Get one chequeRegister by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ChequeRegisterDTO> findOne(Long id) {
        log.debug("Request to get ChequeRegister : {}", id);
        return chequeRegisterRepository.findById(id)
            .map(chequeRegisterMapper::toDto);
    }

    /**
     * Delete the chequeRegister by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChequeRegister : {}", id);
        chequeRegisterRepository.deleteById(id);
        chequeRegisterSearchRepository.deleteById(id);
    }

    /**
     * Search for the chequeRegister corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ChequeRegisterDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ChequeRegisters for query {}", query);
        return chequeRegisterSearchRepository.search(queryStringQuery(query), pageable)
            .map(chequeRegisterMapper::toDto);
    }
}
