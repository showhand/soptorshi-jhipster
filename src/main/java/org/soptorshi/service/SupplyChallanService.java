package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.SupplyChallan;
import org.soptorshi.repository.SupplyChallanRepository;
import org.soptorshi.repository.search.SupplyChallanSearchRepository;
import org.soptorshi.service.dto.SupplyChallanDTO;
import org.soptorshi.service.mapper.SupplyChallanMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing SupplyChallan.
 */
@Service
@Transactional
public class SupplyChallanService {

    private final Logger log = LoggerFactory.getLogger(SupplyChallanService.class);

    private final SupplyChallanRepository supplyChallanRepository;

    private final SupplyChallanMapper supplyChallanMapper;

    private final SupplyChallanSearchRepository supplyChallanSearchRepository;

    public SupplyChallanService(SupplyChallanRepository supplyChallanRepository, SupplyChallanMapper supplyChallanMapper, SupplyChallanSearchRepository supplyChallanSearchRepository) {
        this.supplyChallanRepository = supplyChallanRepository;
        this.supplyChallanMapper = supplyChallanMapper;
        this.supplyChallanSearchRepository = supplyChallanSearchRepository;
    }

    /**
     * Save a supplyChallan.
     *
     * @param supplyChallanDTO the entity to save
     * @return the persisted entity
     */
    public SupplyChallanDTO save(SupplyChallanDTO supplyChallanDTO) {
        log.debug("Request to save SupplyChallan : {}", supplyChallanDTO);
        SupplyChallan supplyChallan = supplyChallanMapper.toEntity(supplyChallanDTO);
        supplyChallan = supplyChallanRepository.save(supplyChallan);
        SupplyChallanDTO result = supplyChallanMapper.toDto(supplyChallan);
        supplyChallanSearchRepository.save(supplyChallan);
        return result;
    }

    /**
     * Get all the supplyChallans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyChallanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplyChallans");
        return supplyChallanRepository.findAll(pageable)
            .map(supplyChallanMapper::toDto);
    }


    /**
     * Get one supplyChallan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SupplyChallanDTO> findOne(Long id) {
        log.debug("Request to get SupplyChallan : {}", id);
        return supplyChallanRepository.findById(id)
            .map(supplyChallanMapper::toDto);
    }

    /**
     * Delete the supplyChallan by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SupplyChallan : {}", id);
        supplyChallanRepository.deleteById(id);
        supplyChallanSearchRepository.deleteById(id);
    }

    /**
     * Search for the supplyChallan corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SupplyChallanDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SupplyChallans for query {}", query);
        return supplyChallanSearchRepository.search(queryStringQuery(query), pageable)
            .map(supplyChallanMapper::toDto);
    }
}
