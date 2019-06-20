package org.soptorshi.service;

import org.soptorshi.domain.Advance;
import org.soptorshi.domain.Designation;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.Office;
import org.soptorshi.domain.enumeration.EmployeeStatus;
import org.soptorshi.domain.enumeration.PaymentStatus;
import org.soptorshi.repository.AdvanceRepository;
import org.soptorshi.repository.search.AdvanceSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.dto.AdvanceDTO;
import org.soptorshi.service.mapper.AdvanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Advance.
 */
@Service
@Transactional
public class AdvanceService {

    private final Logger log = LoggerFactory.getLogger(AdvanceService.class);

    private final AdvanceRepository advanceRepository;

    private final AdvanceMapper advanceMapper;

    private final AdvanceSearchRepository advanceSearchRepository;

    public AdvanceService(AdvanceRepository advanceRepository, AdvanceMapper advanceMapper, AdvanceSearchRepository advanceSearchRepository) {
        this.advanceRepository = advanceRepository;
        this.advanceMapper = advanceMapper;
        this.advanceSearchRepository = advanceSearchRepository;
    }

    /**
     * Save a advance.
     *
     * @param advanceDTO the entity to save
     * @return the persisted entity
     */
    public AdvanceDTO save(AdvanceDTO advanceDTO) {
        log.debug("Request to save Advance : {}", advanceDTO);
        Advance advance = advanceMapper.toEntity(advanceDTO);
        advance.setModifiedOn(LocalDate.now());
        advance.setModifiedBy(SecurityUtils.getCurrentUserLogin().toString());
        advance = advanceRepository.save(advance);
        AdvanceDTO result = advanceMapper.toDto(advance);
        advanceSearchRepository.save(advance);
        return result;
    }

    public void saveAll(List<Advance> advances){
        advanceRepository.saveAll(advances);
    }

    /**
     * Get all the advances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AdvanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Advances");
        return advanceRepository.findAll(pageable)
            .map(advanceMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<Advance> get(PaymentStatus paymentStatus, Office office, Designation designation, EmployeeStatus employeeStatus){
        return advanceRepository.findByPaymentStatusAndEmployee_OfficeAndEmployee_DesignationAndEmployee_EmployeeStatus(paymentStatus, office, designation, employeeStatus);
    }

    @Transactional(readOnly = true)
    public List<Advance> get(Employee employee, PaymentStatus paymentStatus){
        return advanceRepository.findByEmployeeAndPaymentStatus(employee, paymentStatus);
    }


    /**
     * Get one advance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<AdvanceDTO> findOne(Long id) {
        log.debug("Request to get Advance : {}", id);
        return advanceRepository.findById(id)
            .map(advanceMapper::toDto);
    }

    /**
     * Delete the advance by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Advance : {}", id);
        advanceRepository.deleteById(id);
        advanceSearchRepository.deleteById(id);
    }

    /**
     * Search for the advance corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AdvanceDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Advances for query {}", query);
        return advanceSearchRepository.search(queryStringQuery(query), pageable)
            .map(advanceMapper::toDto);
    }
}
