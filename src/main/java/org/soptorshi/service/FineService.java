package org.soptorshi.service;

import org.soptorshi.domain.Designation;
import org.soptorshi.domain.Employee;
import org.soptorshi.domain.Fine;
import org.soptorshi.domain.Office;
import org.soptorshi.domain.enumeration.EmployeeStatus;
import org.soptorshi.domain.enumeration.PaymentStatus;
import org.soptorshi.repository.FineRepository;
import org.soptorshi.repository.search.FineSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.dto.FineDTO;
import org.soptorshi.service.mapper.FineMapper;
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
 * Service Implementation for managing Fine.
 */
@Service
@Transactional
public class FineService {

    private final Logger log = LoggerFactory.getLogger(FineService.class);

    private final FineRepository fineRepository;

    private final FineMapper fineMapper;

    private final FineSearchRepository fineSearchRepository;

    public FineService(FineRepository fineRepository, FineMapper fineMapper, FineSearchRepository fineSearchRepository) {
        this.fineRepository = fineRepository;
        this.fineMapper = fineMapper;
        this.fineSearchRepository = fineSearchRepository;
    }

    /**
     * Save a fine.
     *
     * @param fineDTO the entity to save
     * @return the persisted entity
     */
    public FineDTO save(FineDTO fineDTO) {
        log.debug("Request to save Fine : {}", fineDTO);
        Fine fine = fineMapper.toEntity(fineDTO);
        fine.setModifiedBy(SecurityUtils.getCurrentUserLogin().toString());
        fine.setModifiedDate(LocalDate.now());
        fine = fineRepository.save(fine);
        FineDTO result = fineMapper.toDto(fine);
        fineSearchRepository.save(fine);
        return result;
    }

    public void saveAll(List<Fine> fines){
        fineRepository.saveAll(fines);
    }

    /**
     * Get all the fines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fines");
        return fineRepository.findAll(pageable)
            .map(fineMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<Fine> get(PaymentStatus paymentStatus, Long officeId, Long designationId, EmployeeStatus employeeStatus){
        return fineRepository.findByPaymentStatusAndEmployee_Office_IdAndEmployee_Designation_IdAndEmployee_EmployeeStatus(paymentStatus, officeId, designationId, employeeStatus);
    }

    @Transactional(readOnly = true)
    public List<Fine> get(Long employeeId, PaymentStatus paymentStatus){
        return fineRepository.findByEmployee_IdAndPaymentStatus(employeeId, paymentStatus);
    }


    /**
     * Get one fine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<FineDTO> findOne(Long id) {
        log.debug("Request to get Fine : {}", id);
        return fineRepository.findById(id)
            .map(fineMapper::toDto);
    }

    /**
     * Delete the fine by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Fine : {}", id);
        fineRepository.deleteById(id);
        fineSearchRepository.deleteById(id);
    }

    /**
     * Search for the fine corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FineDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Fines for query {}", query);
        return fineSearchRepository.search(queryStringQuery(query), pageable)
            .map(fineMapper::toDto);
    }
}
