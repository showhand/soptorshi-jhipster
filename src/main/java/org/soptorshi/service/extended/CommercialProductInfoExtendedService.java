package org.soptorshi.service.extended;

import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialProductInfo;
import org.soptorshi.domain.enumeration.CommercialBudgetStatus;
import org.soptorshi.repository.CommercialProductInfoRepository;
import org.soptorshi.repository.search.CommercialProductInfoSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialProductInfoQueryService;
import org.soptorshi.service.CommercialProductInfoService;
import org.soptorshi.service.dto.CommercialBudgetDTO;
import org.soptorshi.service.dto.CommercialProductInfoCriteria;
import org.soptorshi.service.dto.CommercialProductInfoDTO;
import org.soptorshi.service.mapper.CommercialProductInfoMapper;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing CommercialProductInfo.
 */
@Service
@Transactional
public class CommercialProductInfoExtendedService extends CommercialProductInfoService {

    private final Logger log = LoggerFactory.getLogger(CommercialProductInfoExtendedService.class);

    private final CommercialProductInfoRepository commercialProductInfoRepository;

    private final CommercialProductInfoMapper commercialProductInfoMapper;

    private final CommercialProductInfoSearchRepository commercialProductInfoSearchRepository;

    private final CommercialProductInfoQueryService commercialProductInfoQueryService;

    private final CommercialBudgetExtendedService commercialBudgetExtendedService;

    public CommercialProductInfoExtendedService(CommercialProductInfoRepository commercialProductInfoRepository, CommercialProductInfoMapper commercialProductInfoMapper, CommercialProductInfoSearchRepository commercialProductInfoSearchRepository, CommercialProductInfoQueryService commercialProductInfoQueryService, CommercialBudgetExtendedService commercialBudgetExtendedService) {
        super(commercialProductInfoRepository, commercialProductInfoMapper, commercialProductInfoSearchRepository);
        this.commercialProductInfoRepository = commercialProductInfoRepository;
        this.commercialProductInfoMapper = commercialProductInfoMapper;
        this.commercialProductInfoSearchRepository = commercialProductInfoSearchRepository;
        this.commercialProductInfoQueryService = commercialProductInfoQueryService;
        this.commercialBudgetExtendedService = commercialBudgetExtendedService;
    }

    @Transactional
    public CommercialProductInfoDTO save(CommercialProductInfoDTO commercialProductInfoDTO) {
        log.debug("Request to save CommercialProductInfo : {}", commercialProductInfoDTO);
        Optional<CommercialBudgetDTO> commercialBudgetDTO = commercialBudgetExtendedService.findOne(commercialProductInfoDTO.getCommercialBudgetId());
        if (commercialBudgetDTO.isPresent()) {
            if (!commercialBudgetDTO.get().getBudgetStatus().equals(CommercialBudgetStatus.WAITING_FOR_APPROVAL) && !commercialBudgetDTO.get().getBudgetStatus().equals(CommercialBudgetStatus.APPROVED) && !commercialBudgetDTO.get().getBudgetStatus().equals(CommercialBudgetStatus.REJECTED)) {
                String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
                Instant currentDateTime = Instant.now();

                if (commercialProductInfoDTO.getId() == null) {
                    commercialProductInfoDTO.setCreatedBy(currentUser);
                    commercialProductInfoDTO.setCreatedOn(currentDateTime);
                } else {
                    commercialProductInfoDTO.setUpdatedBy(currentUser);
                    commercialProductInfoDTO.setUpdatedOn(currentDateTime);
                }
                CommercialProductInfo commercialProductInfo = commercialProductInfoMapper.toEntity(commercialProductInfoDTO);
                commercialProductInfo = commercialProductInfoRepository.save(commercialProductInfo);
                CommercialProductInfoDTO result = commercialProductInfoMapper.toDto(commercialProductInfo);

                commercialProductInfoSearchRepository.save(commercialProductInfo);

                List<CommercialProductInfoDTO> commercialProductInfoDTOS = getAllProductInfoByBudgetId(commercialBudgetDTO.get().getId());

                BigDecimal totalQuantity = BigDecimal.ZERO;
                BigDecimal totalOfferedPrice = BigDecimal.ZERO;
                BigDecimal totalBuyingPrice = BigDecimal.ZERO.add(commercialBudgetDTO.get().getTotalTransportationCost());
                BigDecimal profitAmount = BigDecimal.ZERO.subtract(commercialBudgetDTO.get().getTotalTransportationCost());
                BigDecimal profitPercentage = BigDecimal.ZERO;

                for (CommercialProductInfoDTO commercialProductInfoDTO1 : commercialProductInfoDTOS) {
                    totalQuantity = commercialProductInfoDTO1.getOfferedQuantity().add(totalQuantity);
                    totalOfferedPrice = commercialProductInfoDTO1.getOfferedTotalPrice().add(totalOfferedPrice);
                    totalBuyingPrice = commercialProductInfoDTO1.getBuyingTotalPrice().add(totalBuyingPrice);
                    profitAmount = profitAmount.add(commercialProductInfoDTO1.getOfferedTotalPrice().subtract(commercialProductInfoDTO1.getBuyingTotalPrice()));
                    profitPercentage = ((totalOfferedPrice.subtract(totalBuyingPrice)).divide(totalBuyingPrice, 7, RoundingMode.CEILING)).multiply(BigDecimal.valueOf(100));
                }

                commercialBudgetDTO.get().setTotalQuantity(totalQuantity);
                commercialBudgetDTO.get().setTotalOfferedPrice(totalOfferedPrice);
                commercialBudgetDTO.get().setTotalBuyingPrice(totalBuyingPrice);
                commercialBudgetDTO.get().setProfitAmount(profitAmount);
                commercialBudgetDTO.get().setProfitPercentage(profitPercentage);
                commercialBudgetExtendedService.save(commercialBudgetDTO.get());

                return result;
            }
            else {
                throw new BadRequestAlertException("Budget is Waiting for approval or in Accepted/Rejected State", "commercialProductInfo", "invalidaccess");
            }
        }
        return null;
    }

    public List<CommercialProductInfoDTO> getAllProductInfoByBudgetId(Long commercialBudgetId) {
        CommercialProductInfoCriteria criteria = new CommercialProductInfoCriteria();
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(commercialBudgetId);
        criteria.setCommercialBudgetId(longFilter);
        return commercialProductInfoQueryService.findByCriteria(criteria);
    }
}
