package org.soptorshi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialProductInfo;
import org.soptorshi.repository.CommercialProductInfoRepository;
import org.soptorshi.repository.search.CommercialProductInfoSearchRepository;
import org.soptorshi.service.dto.CommercialBudgetDTO;
import org.soptorshi.service.dto.CommercialProductInfoDTO;
import org.soptorshi.service.mapper.CommercialProductInfoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing CommercialProductInfo.
 */
@Service
@Transactional
public class CommercialProductInfoService {

    private final Logger log = LoggerFactory.getLogger(CommercialProductInfoService.class);

    private final CommercialProductInfoRepository commercialProductInfoRepository;

    private final CommercialProductInfoMapper commercialProductInfoMapper;

    private final CommercialProductInfoSearchRepository commercialProductInfoSearchRepository;

    private final CommercialBudgetService commercialBudgetService;

    public CommercialProductInfoService(CommercialProductInfoRepository commercialProductInfoRepository, CommercialProductInfoMapper commercialProductInfoMapper, CommercialProductInfoSearchRepository commercialProductInfoSearchRepository, CommercialBudgetService commercialBudgetService) {
        this.commercialProductInfoRepository = commercialProductInfoRepository;
        this.commercialProductInfoMapper = commercialProductInfoMapper;
        this.commercialProductInfoSearchRepository = commercialProductInfoSearchRepository;
        this.commercialBudgetService = commercialBudgetService;
    }

    /**
     * Save a commercialProductInfo.
     *
     * @param commercialProductInfoDTO the entity to save
     * @return the persisted entity
     */

    @Transactional
    public CommercialProductInfoDTO save(CommercialProductInfoDTO commercialProductInfoDTO) {
        log.debug("Request to save CommercialProductInfo : {}", commercialProductInfoDTO);
        CommercialProductInfo commercialProductInfo = commercialProductInfoMapper.toEntity(commercialProductInfoDTO);
        commercialProductInfo = commercialProductInfoRepository.save(commercialProductInfo);
        CommercialProductInfoDTO result = commercialProductInfoMapper.toDto(commercialProductInfo);
        commercialProductInfoSearchRepository.save(commercialProductInfo);
        Optional<CommercialBudgetDTO> commercialBudgetDTO = commercialBudgetService.findOne(commercialProductInfo.getCommercialBudget().getId());
        if(commercialBudgetDTO.isPresent()) {
            commercialBudgetDTO.get().setOfferedPrice(commercialBudgetDTO.get().getOfferedPrice() == null ? commercialProductInfo.getOfferedTotalPrice() :
                commercialBudgetDTO.get().getOfferedPrice().add(commercialProductInfo.getOfferedTotalPrice()));
            commercialBudgetDTO.get().setBuyingPrice(commercialBudgetDTO.get().getBuyingPrice() == null ? commercialProductInfo.getBuyingTotalPrice() : commercialBudgetDTO.get().getBuyingPrice().add(commercialProductInfo.getBuyingTotalPrice()));
            commercialBudgetDTO.get().setProfitAmount(commercialBudgetDTO.get().getOfferedPrice().subtract(commercialBudgetDTO.get().getBuyingPrice()));
            commercialBudgetDTO.get().setProfitPercentage(commercialBudgetDTO.get().getBuyingPrice().divide(commercialBudgetDTO.get().getOfferedPrice().subtract(commercialBudgetDTO.get().getBuyingPrice()).multiply(BigDecimal.valueOf(100)), 4, RoundingMode.HALF_UP));
            commercialBudgetService.save(commercialBudgetDTO.get());
        }
        return result;
    }

    /**
     * Get all the commercialProductInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialProductInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommercialProductInfos");
        return commercialProductInfoRepository.findAll(pageable)
            .map(commercialProductInfoMapper::toDto);
    }


    /**
     * Get one commercialProductInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommercialProductInfoDTO> findOne(Long id) {
        log.debug("Request to get CommercialProductInfo : {}", id);
        return commercialProductInfoRepository.findById(id)
            .map(commercialProductInfoMapper::toDto);
    }

    /**
     * Delete the commercialProductInfo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommercialProductInfo : {}", id);
        commercialProductInfoRepository.deleteById(id);
        commercialProductInfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the commercialProductInfo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommercialProductInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommercialProductInfos for query {}", query);
        return commercialProductInfoSearchRepository.search(queryStringQuery(query), pageable)
            .map(commercialProductInfoMapper::toDto);
    }
}
