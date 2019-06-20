package org.soptorshi.service;

import org.soptorshi.domain.Designation;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.Loan;
import org.soptorshi.domain.Office;
import org.soptorshi.domain.enumeration.EmployeeStatus;
import org.soptorshi.domain.enumeration.PaymentStatus;
import org.soptorshi.repository.LoanRepository;
import org.soptorshi.repository.search.LoanSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.dto.LoanDTO;
import org.soptorshi.service.mapper.LoanMapper;
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
 * Service Implementation for managing Loan.
 */
@Service
@Transactional
public class LoanService {

    private final Logger log = LoggerFactory.getLogger(LoanService.class);

    private final LoanRepository loanRepository;

    private final LoanMapper loanMapper;

    private final LoanSearchRepository loanSearchRepository;

    public LoanService(LoanRepository loanRepository, LoanMapper loanMapper, LoanSearchRepository loanSearchRepository) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.loanSearchRepository = loanSearchRepository;
    }

    /**
     * Save a loan.
     *
     * @param loanDTO the entity to save
     * @return the persisted entity
     */
    public LoanDTO save(LoanDTO loanDTO) {
        log.debug("Request to save Loan : {}", loanDTO);
        Loan loan = loanMapper.toEntity(loanDTO);
        loan.setModifiedBy(SecurityUtils.getCurrentUserLogin().toString());
        loan.setModifiedDate(LocalDate.now());
        loan = loanRepository.save(loan);
        LoanDTO result = loanMapper.toDto(loan);
        loanSearchRepository.save(loan);
        return result;
    }

    public void saveAll(List<Loan> loans){
        loanRepository.saveAll(loans);
    }

    /**
     * Get all the loans.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LoanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Loans");
        return loanRepository.findAll(pageable)
            .map(loanMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<Loan> get(Employee employee, PaymentStatus paymentStatus){
        return loanRepository.getByEmployeeAndPaymentStatus(employee, paymentStatus);
    }


    /**
     * Get one loan by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<LoanDTO> findOne(Long id) {
        log.debug("Request to get Loan : {}", id);
        return loanRepository.findById(id)
            .map(loanMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<Loan> get(PaymentStatus paymentStatus, Office office, Designation designation, EmployeeStatus employeeStatus){
        return loanRepository.getByPaymentStatusAndEmployee_OfficeAndEmployee_DesignationAndEmployee_EmployeeStatus(paymentStatus, office, designation, employeeStatus);
    }

    /**
     * Delete the loan by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Loan : {}", id);
        loanRepository.deleteById(id);
        loanSearchRepository.deleteById(id);
    }

    /**
     * Search for the loan corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<LoanDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Loans for query {}", query);
        return loanSearchRepository.search(queryStringQuery(query), pageable)
            .map(loanMapper::toDto);
    }
}
