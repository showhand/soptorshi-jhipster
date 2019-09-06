package org.soptorshi.service;

import org.soptorshi.domain.MstGroup;
import org.soptorshi.repository.MstGroupRepository;
import org.soptorshi.repository.search.MstGroupSearchRepository;
import org.soptorshi.service.dto.MstGroupDTO;
import org.soptorshi.service.mapper.MstGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing MstGroup.
 */
@Service
@Transactional
public class MstGroupService {

    private final Logger log = LoggerFactory.getLogger(MstGroupService.class);

    private final MstGroupRepository mstGroupRepository;

    private final MstGroupMapper mstGroupMapper;

    private final MstGroupSearchRepository mstGroupSearchRepository;

    public MstGroupService(MstGroupRepository mstGroupRepository, MstGroupMapper mstGroupMapper, MstGroupSearchRepository mstGroupSearchRepository) {
        this.mstGroupRepository = mstGroupRepository;
        this.mstGroupMapper = mstGroupMapper;
        this.mstGroupSearchRepository = mstGroupSearchRepository;
    }

    /**
     * Save a mstGroup.
     *
     * @param mstGroupDTO the entity to save
     * @return the persisted entity
     */
    public MstGroupDTO save(MstGroupDTO mstGroupDTO) {
        log.debug("Request to save MstGroup : {}", mstGroupDTO);
        MstGroup mstGroup = mstGroupMapper.toEntity(mstGroupDTO);
        mstGroup = mstGroupRepository.save(mstGroup);
        MstGroupDTO result = mstGroupMapper.toDto(mstGroup);
        mstGroupSearchRepository.save(mstGroup);
        return result;
    }

    /**
     * Get all the mstGroups.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MstGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MstGroups");
        return mstGroupRepository.findAll(pageable)
            .map(mstGroupMapper::toDto);
    }


    /**
     * Get one mstGroup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MstGroupDTO> findOne(Long id) {
        log.debug("Request to get MstGroup : {}", id);
        return mstGroupRepository.findById(id)
            .map(mstGroupMapper::toDto);
    }

    /**
     * Delete the mstGroup by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MstGroup : {}", id);
        mstGroupRepository.deleteById(id);
        mstGroupSearchRepository.deleteById(id);
    }

    /**
     * Search for the mstGroup corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MstGroupDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MstGroups for query {}", query);
        return mstGroupSearchRepository.search(queryStringQuery(query), pageable)
            .map(mstGroupMapper::toDto);
    }
}
