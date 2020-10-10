package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPaymentInfo;
import org.soptorshi.domain.enumeration.CommercialPaymentStatus;
import org.soptorshi.domain.enumeration.CommercialPoStatus;
import org.soptorshi.repository.extended.CommercialPaymentInfoExtendedRepository;
import org.soptorshi.repository.search.CommercialPaymentInfoSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialPaymentInfoService;
import org.soptorshi.service.dto.CommercialPaymentInfoDTO;
import org.soptorshi.service.dto.CommercialPoDTO;
import org.soptorshi.service.mapper.CommercialPaymentInfoMapper;
import org.soptorshi.web.rest.errors.BadRequestAlertException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing CommercialPaymentInfo.
 */
@Service
@Transactional
public class CommercialPaymentInfoExtendedService extends CommercialPaymentInfoService {

    private final Logger log = LoggerFactory.getLogger(CommercialPaymentInfoExtendedService.class);

    private final CommercialPaymentInfoExtendedRepository commercialPaymentInfoExtendedRepository;

    private final CommercialPaymentInfoMapper commercialPaymentInfoMapper;

    private final CommercialPaymentInfoSearchRepository commercialPaymentInfoSearchRepository;

    private final CommercialPoExtendedService commercialPoExtendedService;

    public CommercialPaymentInfoExtendedService(CommercialPaymentInfoExtendedRepository commercialPaymentInfoExtendedRepository, CommercialPaymentInfoMapper commercialPaymentInfoMapper, CommercialPaymentInfoSearchRepository commercialPaymentInfoSearchRepository, CommercialPoExtendedService commercialPoExtendedService) {
        super(commercialPaymentInfoExtendedRepository, commercialPaymentInfoMapper, commercialPaymentInfoSearchRepository);
        this.commercialPaymentInfoExtendedRepository = commercialPaymentInfoExtendedRepository;
        this.commercialPaymentInfoMapper = commercialPaymentInfoMapper;
        this.commercialPaymentInfoSearchRepository = commercialPaymentInfoSearchRepository;
        this.commercialPoExtendedService = commercialPoExtendedService;
    }

    @Transactional
    public CommercialPaymentInfoDTO save(CommercialPaymentInfoDTO commercialPaymentInfoDTO) {
        log.debug("Request to save CommercialPaymentInfo : {}", commercialPaymentInfoDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : "";
        Instant currentDateTime = Instant.now();

        if(commercialPaymentInfoDTO.getTotalAmountToPay().compareTo(BigDecimal.ZERO) >= 0) {
            if (commercialPaymentInfoDTO.getTotalAmountPaid() == null && commercialPaymentInfoDTO.getRemainingAmountToPay() == null) {

            }
            else {
                if(commercialPaymentInfoDTO.getTotalAmountPaid().compareTo(BigDecimal.ZERO) == 0 && commercialPaymentInfoDTO.getRemainingAmountToPay().compareTo(BigDecimal.ZERO) == 0) {}
                else {
                    BigDecimal val1 = commercialPaymentInfoDTO.getTotalAmountPaid();
                    BigDecimal val2 = commercialPaymentInfoDTO.getRemainingAmountToPay();
                    BigDecimal sum = val1.add(val2);

                    if (commercialPaymentInfoDTO.getTotalAmountToPay().compareTo(sum) != 0) {
                        throw new BadRequestAlertException("Paid amount and remaining amount needs to be equal with total Payment", "CommercialPaymentInfo", "idnull");
                    }
                }
            }
        }
        else {
            throw new BadRequestAlertException("Total Amount Can not be Zero", "CommercialPaymentInfo", "idnull");
        }

        if (commercialPaymentInfoDTO.getId() == null) {
            commercialPaymentInfoDTO.setPaymentStatus(CommercialPaymentStatus.WAITING_FOR_PAYMENT_CONFIRMATION);
            commercialPaymentInfoDTO.setCreatedBy(currentUser);
            commercialPaymentInfoDTO.setCreatedOn(currentDateTime);
            CommercialPaymentInfo commercialPaymentInfo = commercialPaymentInfoMapper.toEntity(commercialPaymentInfoDTO);
            commercialPaymentInfo = commercialPaymentInfoExtendedRepository.save(commercialPaymentInfo);
            CommercialPaymentInfoDTO result = commercialPaymentInfoMapper.toDto(commercialPaymentInfo);
            commercialPaymentInfoSearchRepository.save(commercialPaymentInfo);
            return result;
        }
        else{
            Optional<CommercialPaymentInfoDTO> currentCommercialPaymentInfoDTO = this.findOne(commercialPaymentInfoDTO.getId());
            if (currentCommercialPaymentInfoDTO.isPresent()) {
                if (currentCommercialPaymentInfoDTO.get().getPaymentStatus().equals(CommercialPaymentStatus.WAITING_FOR_PAYMENT_CONFIRMATION)) {
                    if (commercialPaymentInfoDTO.getPaymentStatus().equals(CommercialPaymentStatus.PAYMENT_CONFIRMED)) {
                        CommercialPoDTO commercialPoDTO = new CommercialPoDTO();
                        commercialPoDTO.setPurchaseOrderNo(currentCommercialPaymentInfoDTO.get().getCommercialPiProformaNo());
                        commercialPoDTO.setCommercialPiId(currentCommercialPaymentInfoDTO.get().getCommercialPiId());
                        commercialPoDTO.setCommercialPiProformaNo(currentCommercialPaymentInfoDTO.get().getCommercialPiProformaNo());
                        commercialPoDTO.setPoStatus(CommercialPoStatus.PO_CREATED);
                        commercialPoExtendedService.save(commercialPoDTO);
                    }
                    commercialPaymentInfoDTO.setUpdatedBy(currentUser);
                    commercialPaymentInfoDTO.setUpdatedOn(currentDateTime);
                    CommercialPaymentInfo commercialPaymentInfo = commercialPaymentInfoMapper.toEntity(commercialPaymentInfoDTO);
                    commercialPaymentInfo = commercialPaymentInfoExtendedRepository.save(commercialPaymentInfo);
                    CommercialPaymentInfoDTO result = commercialPaymentInfoMapper.toDto(commercialPaymentInfo);
                    commercialPaymentInfoSearchRepository.save(commercialPaymentInfo);
                    return result;
                } else {
                    throw new BadRequestAlertException("Proforma invoice was not accepted by the customer", "CommercialPaymentInfo", "idnull");
                }
            } else {
                throw new BadRequestAlertException("Proforma invoice not found", "CommercialPaymentInfo", "idnull");
            }
        }
    }
}

