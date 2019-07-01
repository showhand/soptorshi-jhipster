package org.soptorshi.service;

import org.soptorshi.domain.Authority;
import org.soptorshi.domain.PurchaseCommittee;
import org.soptorshi.domain.User;
import org.soptorshi.repository.AuthorityRepository;
import org.soptorshi.repository.PurchaseCommitteeRepository;
import org.soptorshi.repository.UserRepository;
import org.soptorshi.repository.search.PurchaseCommitteeSearchRepository;
import org.soptorshi.security.AuthoritiesConstants;
import org.soptorshi.service.dto.EmployeeDTO;
import org.soptorshi.service.dto.PurchaseCommitteeDTO;
import org.soptorshi.service.mapper.PurchaseCommitteeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PurchaseCommittee.
 */
@Service
@Transactional
public class PurchaseCommitteeService {

    private final Logger log = LoggerFactory.getLogger(PurchaseCommitteeService.class);

    private final PurchaseCommitteeRepository purchaseCommitteeRepository;

    private final PurchaseCommitteeMapper purchaseCommitteeMapper;

    private final PurchaseCommitteeSearchRepository purchaseCommitteeSearchRepository;

    private final AuthorityRepository authorityRepository;

    private final UserRepository userRepository;

    private final EmployeeService employeeService;

    public PurchaseCommitteeService(PurchaseCommitteeRepository purchaseCommitteeRepository, PurchaseCommitteeMapper purchaseCommitteeMapper, PurchaseCommitteeSearchRepository purchaseCommitteeSearchRepository, AuthorityRepository authorityRepository, UserRepository userRepository, EmployeeService employeeService) {
        this.purchaseCommitteeRepository = purchaseCommitteeRepository;
        this.purchaseCommitteeMapper = purchaseCommitteeMapper;
        this.purchaseCommitteeSearchRepository = purchaseCommitteeSearchRepository;
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
        this.employeeService = employeeService;
    }

    /**
     * Save a purchaseCommittee.
     *
     * @param purchaseCommitteeDTO the entity to save
     * @return the persisted entity
     */
    public PurchaseCommitteeDTO save(PurchaseCommitteeDTO purchaseCommitteeDTO) {
        log.debug("Request to save PurchaseCommittee : {}", purchaseCommitteeDTO);
        PurchaseCommittee purchaseCommittee = purchaseCommitteeMapper.toEntity(purchaseCommitteeDTO);
        purchaseCommittee = purchaseCommitteeRepository.save(purchaseCommittee);
        EmployeeDTO employeeDTO = employeeService.findOne(purchaseCommittee.getEmployee().getId()).get();
        User user = userRepository.findOneByLogin(employeeDTO.getEmployeeId()).get();
        Set<Authority> authorities = user.getAuthorities();
        authorityRepository.findById(AuthoritiesConstants.PURCHASE_COMMITTEE).ifPresent(authorities::add);
        user.setAuthorities(authorities);
        userRepository.save(user);
        PurchaseCommitteeDTO result = purchaseCommitteeMapper.toDto(purchaseCommittee);
        purchaseCommitteeSearchRepository.save(purchaseCommittee);
        return result;
    }

    /**
     * Get all the purchaseCommittees.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PurchaseCommitteeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseCommittees");
        return purchaseCommitteeRepository.findAll(pageable)
            .map(purchaseCommitteeMapper::toDto);
    }


    /**
     * Get one purchaseCommittee by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PurchaseCommitteeDTO> findOne(Long id) {
        log.debug("Request to get PurchaseCommittee : {}", id);
        return purchaseCommitteeRepository.findById(id)
            .map(purchaseCommitteeMapper::toDto);
    }

    /**
     * Delete the purchaseCommittee by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PurchaseCommittee : {}", id);
        purchaseCommitteeRepository.deleteById(id);
        purchaseCommitteeSearchRepository.deleteById(id);
    }

    /**
     * Search for the purchaseCommittee corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PurchaseCommitteeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PurchaseCommittees for query {}", query);
        return purchaseCommitteeSearchRepository.search(queryStringQuery(query), pageable)
            .map(purchaseCommitteeMapper::toDto);
    }
}
