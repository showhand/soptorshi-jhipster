package org.soptorshi.service;

import org.soptorshi.domain.Bill;
import org.soptorshi.repository.BillRepository;
import org.soptorshi.repository.search.BillSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.dto.BillDTO;
import org.soptorshi.service.mapper.BillMapper;
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
 * Service Implementation for managing Bill.
 */
@Service
@Transactional
public class BillService {

    private final Logger log = LoggerFactory.getLogger(BillService.class);

    private final BillRepository billRepository;

    private final BillMapper billMapper;

    private final BillSearchRepository billSearchRepository;

    public BillService(BillRepository billRepository, BillMapper billMapper, BillSearchRepository billSearchRepository) {
        this.billRepository = billRepository;
        this.billMapper = billMapper;
        this.billSearchRepository = billSearchRepository;
    }

    /**
     * Save a bill.
     *
     * @param billDTO the entity to save
     * @return the persisted entity
     */
    public BillDTO save(BillDTO billDTO) {
        log.debug("Request to save Bill : {}", billDTO);
        Bill bill = billMapper.toEntity(billDTO);
        bill.setModifiedBy(SecurityUtils.getCurrentUserLogin().toString());
        bill.setModifiedDate(LocalDate.now());
        bill = billRepository.save(bill);
        BillDTO result = billMapper.toDto(bill);
        billSearchRepository.save(bill);
        return result;
    }

    /**
     * Get all the bills.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BillDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bills");
        return billRepository.findAll(pageable)
            .map(billMapper::toDto);
    }


    /**
     * Get one bill by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BillDTO> findOne(Long id) {
        log.debug("Request to get Bill : {}", id);
        return billRepository.findById(id)
            .map(billMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<Bill> get(Long employeeId){
        return billRepository.getByEmployee_Id(employeeId);
    }

    /**
     * Delete the bill by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Bill : {}", id);
        billRepository.deleteById(id);
        billSearchRepository.deleteById(id);
    }

    /**
     * Search for the bill corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BillDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Bills for query {}", query);
        return billSearchRepository.search(queryStringQuery(query), pageable)
            .map(billMapper::toDto);
    }
}
