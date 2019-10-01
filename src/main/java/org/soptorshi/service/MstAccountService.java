package org.soptorshi.service;

import org.soptorshi.domain.MstAccount;
import org.soptorshi.repository.MstAccountRepository;
import org.soptorshi.repository.search.MstAccountSearchRepository;
import org.soptorshi.service.dto.MstAccountDTO;
import org.soptorshi.service.mapper.MstAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing MstAccount.
 */
@Service
@Transactional
public class MstAccountService {

    private final Logger log = LoggerFactory.getLogger(MstAccountService.class);

    private final MstAccountRepository mstAccountRepository;

    private final MstAccountMapper mstAccountMapper;

    private final MstAccountSearchRepository mstAccountSearchRepository;

    public MstAccountService(MstAccountRepository mstAccountRepository, MstAccountMapper mstAccountMapper, MstAccountSearchRepository mstAccountSearchRepository) {
        this.mstAccountRepository = mstAccountRepository;
        this.mstAccountMapper = mstAccountMapper;
        this.mstAccountSearchRepository = mstAccountSearchRepository;
    }

    /**
     * Save a mstAccount.
     *
     * @param mstAccountDTO the entity to save
     * @return the persisted entity
     */
    public MstAccountDTO save(MstAccountDTO mstAccountDTO) {
        log.debug("Request to save MstAccount : {}", mstAccountDTO);
        MstAccount mstAccount = mstAccountMapper.toEntity(mstAccountDTO);
        mstAccount = mstAccountRepository.save(mstAccount);
        MstAccountDTO result = mstAccountMapper.toDto(mstAccount);
        mstAccountSearchRepository.save(mstAccount);
        return result;
    }

    /**
     * Get all the mstAccounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MstAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MstAccounts");
        return mstAccountRepository.findAll(pageable)
            .map(mstAccountMapper::toDto);
    }


    /**
     * Get one mstAccount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MstAccountDTO> findOne(Long id) {
        log.debug("Request to get MstAccount : {}", id);
        return mstAccountRepository.findById(id)
            .map(mstAccountMapper::toDto);
    }

    /**
     * Delete the mstAccount by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MstAccount : {}", id);
        mstAccountRepository.deleteById(id);
        mstAccountSearchRepository.deleteById(id);
    }

    /**
     * Search for the mstAccount corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MstAccountDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MstAccounts for query {}", query);
        return mstAccountSearchRepository.search(queryStringQuery(query), pageable)
            .map(mstAccountMapper::toDto);
    }
}
