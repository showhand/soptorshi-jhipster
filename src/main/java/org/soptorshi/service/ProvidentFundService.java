package org.soptorshi.service;

import org.soptorshi.domain.Employee;
import org.soptorshi.domain.ProvidentFund;
import org.soptorshi.domain.enumeration.ProvidentFundStatus;
import org.soptorshi.repository.ProvidentFundRepository;
import org.soptorshi.repository.search.ProvidentFundSearchRepository;
import org.soptorshi.service.dto.ProvidentFundDTO;
import org.soptorshi.service.mapper.ProvidentFundMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ProvidentFund.
 */
@Service
@Transactional
public class ProvidentFundService {

    private final Logger log = LoggerFactory.getLogger(ProvidentFundService.class);

    private final ProvidentFundRepository providentFundRepository;

    private final ProvidentFundMapper providentFundMapper;

    private final ProvidentFundSearchRepository providentFundSearchRepository;

    public ProvidentFundService(ProvidentFundRepository providentFundRepository, ProvidentFundMapper providentFundMapper, ProvidentFundSearchRepository providentFundSearchRepository) {
        this.providentFundRepository = providentFundRepository;
        this.providentFundMapper = providentFundMapper;
        this.providentFundSearchRepository = providentFundSearchRepository;
    }

    /**
     * Save a providentFund.
     *
     * @param providentFundDTO the entity to save
     * @return the persisted entity
     */
    public ProvidentFundDTO save(ProvidentFundDTO providentFundDTO) {
        log.debug("Request to save ProvidentFund : {}", providentFundDTO);
        ProvidentFund providentFund = providentFundMapper.toEntity(providentFundDTO);
        providentFund = providentFundRepository.save(providentFund);
        ProvidentFundDTO result = providentFundMapper.toDto(providentFund);
        providentFundSearchRepository.save(providentFund);
        return result;
    }

    /**
     * Get all the providentFunds.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProvidentFundDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProvidentFunds");
        return providentFundRepository.findAll(pageable)
            .map(providentFundMapper::toDto);
    }


    /**
     * Get one providentFund by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProvidentFundDTO> findOne(Long id) {
        log.debug("Request to get ProvidentFund : {}", id);
        return providentFundRepository.findById(id)
            .map(providentFundMapper::toDto);
    }

    @Transactional(readOnly = true)
    public ProvidentFund get(Employee employee, ProvidentFundStatus status){
        return providentFundRepository.getByEmployeeAndStatus(employee, status);
    }

    /**
     * Delete the providentFund by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProvidentFund : {}", id);
        providentFundRepository.deleteById(id);
        providentFundSearchRepository.deleteById(id);
    }

    /**
     * Search for the providentFund corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProvidentFundDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProvidentFunds for query {}", query);
        return providentFundSearchRepository.search(queryStringQuery(query), pageable)
            .map(providentFundMapper::toDto);
    }
}
