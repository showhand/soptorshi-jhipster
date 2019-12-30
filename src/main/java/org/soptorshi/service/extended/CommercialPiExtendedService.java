package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.CommercialPi;
import org.soptorshi.domain.enumeration.CommercialPiStatus;
import org.soptorshi.domain.enumeration.CommercialPoStatus;
import org.soptorshi.repository.CommercialPiRepository;
import org.soptorshi.repository.search.CommercialPiSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.CommercialPiService;
import org.soptorshi.service.dto.CommercialPiDTO;
import org.soptorshi.service.dto.CommercialPoDTO;
import org.soptorshi.service.mapper.CommercialPiMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing CommercialPi.
 */
@Service
@Transactional
public class CommercialPiExtendedService extends CommercialPiService {

    private final Logger log = LoggerFactory.getLogger(CommercialPiExtendedService.class);

    private final CommercialPiRepository commercialPiRepository;

    private final CommercialPiMapper commercialPiMapper;

    private final CommercialPiSearchRepository commercialPiSearchRepository;

    private final CommercialPoExtendedService commercialPoExtendedService;

    public CommercialPiExtendedService(CommercialPiRepository commercialPiRepository, CommercialPiMapper commercialPiMapper, CommercialPiSearchRepository commercialPiSearchRepository,
                                       CommercialPoExtendedService commercialPoExtendedService) {
        super(commercialPiRepository, commercialPiMapper, commercialPiSearchRepository);
        this.commercialPiRepository = commercialPiRepository;
        this.commercialPiMapper = commercialPiMapper;
        this.commercialPiSearchRepository = commercialPiSearchRepository;
        this.commercialPoExtendedService = commercialPoExtendedService;
    }

    /**
     * Save a commercialPi.
     *
     * @param commercialPiDTO the entity to save
     * @return the persisted entity
     */
    public CommercialPiDTO save(CommercialPiDTO commercialPiDTO) {
        log.debug("Request to save CommercialPi : {}", commercialPiDTO);

        String currentUser = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().toString() : "";
        Instant currentDateTime = Instant.now();

        if(commercialPiDTO.getId() == null) {
            commercialPiDTO.setPiStatus(CommercialPiStatus.WAITING_FOR_PI_APPROVAL_BY_THE_CUSTOMER);
            commercialPiDTO.setCreatedBy(currentUser);
            commercialPiDTO.setCreatedOn(currentDateTime);
            CommercialPi commercialPi = commercialPiMapper.toEntity(commercialPiDTO);
            commercialPi = commercialPiRepository.save(commercialPi);
            CommercialPiDTO result = commercialPiMapper.toDto(commercialPi);
            commercialPiSearchRepository.save(commercialPi);
            return result;
        }
        else {
            Optional<CommercialPi> currentCommercialPi = commercialPiRepository.findById(commercialPiDTO.getId());
            if(currentCommercialPi.isPresent()) {
                if(currentCommercialPi.get().getPiStatus().equals(CommercialPiStatus.WAITING_FOR_PI_APPROVAL_BY_THE_CUSTOMER)) {
                    if (commercialPiDTO.getPiStatus().equals(CommercialPiStatus.PI_APPROVED_BY_THE_CUSTOMER)) {
                        CommercialPoDTO commercialPoDTO = new CommercialPoDTO();
                        commercialPoDTO.setCommercialPiId(commercialPiDTO.getId());
                        commercialPoDTO.setCommercialPiProformaNo(commercialPiDTO.getProformaNo());
                        commercialPoDTO.setPoStatus(CommercialPoStatus.PO_CREATED);
                        commercialPoDTO.setPurchaseOrderNo(commercialPiDTO.getPurchaseOrderNo());
                        commercialPoExtendedService.save(commercialPoDTO);
                    }
                    commercialPiDTO.setUpdatedBy(currentUser);
                    commercialPiDTO.setUpdatedOn(currentDateTime);
                    CommercialPi commercialPi = commercialPiMapper.toEntity(commercialPiDTO);
                    commercialPi = commercialPiRepository.save(commercialPi);
                    CommercialPiDTO result = commercialPiMapper.toDto(commercialPi);
                    commercialPiSearchRepository.save(commercialPi);
                    return result;
                }
            }
        }
        return null;
    }
}
