package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPaymentInfo;
import org.soptorshi.repository.CommercialPaymentInfoRepository;
import org.soptorshi.repository.search.CommercialPaymentInfoSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialPaymentInfoService;
import org.soptorshi.service.CommercialPoStatusService;
import org.soptorshi.service.dto.CommercialPaymentInfoDTO;
import org.soptorshi.service.mapper.CommercialPaymentInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialPaymentInfo.
 */
@Service
@Transactional
public class CommercialPaymentInfoExtendedService extends CommercialPaymentInfoService {

    private final Logger log = LoggerFactory.getLogger(CommercialPaymentInfoExtendedService.class);

    private final CommercialPaymentInfoRepository commercialPaymentInfoRepository;

    private final CommercialPaymentInfoMapper commercialPaymentInfoMapper;

    private final CommercialPaymentInfoSearchRepository commercialPaymentInfoSearchRepository;

    private final CommercialPoStatusService commercialPoStatusService;

    public CommercialPaymentInfoExtendedService(CommercialPaymentInfoRepository commercialPaymentInfoRepository, CommercialPaymentInfoMapper commercialPaymentInfoMapper, CommercialPaymentInfoSearchRepository commercialPaymentInfoSearchRepository, CommercialPoStatusService commercialPoStatusService) {
        super(commercialPaymentInfoRepository, commercialPaymentInfoMapper, commercialPaymentInfoSearchRepository, commercialPoStatusService);
        this.commercialPaymentInfoRepository = commercialPaymentInfoRepository;
        this.commercialPaymentInfoMapper = commercialPaymentInfoMapper;
        this.commercialPaymentInfoSearchRepository = commercialPaymentInfoSearchRepository;
        this.commercialPoStatusService = commercialPoStatusService;
    }

    /**
     * Save a commercialPaymentInfo.
     *
     * @param commercialPaymentInfoDTO the entity to save
     * @return the persisted entity
     */
    public CommercialPaymentInfoDTO save(CommercialPaymentInfoDTO commercialPaymentInfoDTO) {
        log.debug("Request to save CommercialPaymentInfo : {}", commercialPaymentInfoDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().toString() : "";
        LocalDate currentDate = LocalDate.now();
        if (commercialPaymentInfoDTO.getId() == null) {
            commercialPaymentInfoDTO.setCreatedBy(currentUser);
            commercialPaymentInfoDTO.setCreateOn(currentDate);
        } else {
            commercialPaymentInfoDTO.setUpdatedBy(currentUser);
            commercialPaymentInfoDTO.setUpdatedOn(currentDate);
        }
        CommercialPaymentInfo commercialPaymentInfo = commercialPaymentInfoMapper.toEntity(commercialPaymentInfoDTO);
        commercialPaymentInfo = commercialPaymentInfoRepository.save(commercialPaymentInfo);
        CommercialPaymentInfoDTO result = commercialPaymentInfoMapper.toDto(commercialPaymentInfo);
        commercialPaymentInfoSearchRepository.save(commercialPaymentInfo);
        return result;
    }


    /**
     * Get all the commercialPaymentInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPaymentInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialPaymentInfos");
        return commercialPaymentInfoRepository.findAll(pageable)
            .map(commercialPaymentInfoMapper::toDto);
    }


    /**
     * Get one commercialPaymentInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialPaymentInfoDTO> findOne(Long id) {
        log.debug("Request to get CommercialPaymentInfo : {}", id);
        return commercialPaymentInfoRepository.findById(id)
            .map(commercialPaymentInfoMapper::toDto);
    }

    /**
     * Delete the commercialPaymentInfo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialPaymentInfo : {}", id);
        /*commercialPaymentInfoRepository.deleteById(id);
        commercialPaymentInfoSearchRepository.deleteById(id);*/
    }

    /**
     * Search for the commercialPaymentInfo corresponding to the query.
     *
     * @param query    the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialPaymentInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialPaymentInfos for query {}", query);
        return commercialPaymentInfoSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialPaymentInfoMapper::toDto);
    }
}
