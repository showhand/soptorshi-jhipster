package org.soptorshi.service.extended;

import org.soptorshi.repository.PredefinedNarrationRepository;
import org.soptorshi.repository.search.PredefinedNarrationSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.PredefinedNarrationService;
import org.soptorshi.service.dto.PredefinedNarrationDTO;
import org.soptorshi.service.mapper.PredefinedNarrationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class PredefinedNarrationExtendedService extends PredefinedNarrationService {

    public PredefinedNarrationExtendedService(PredefinedNarrationRepository predefinedNarrationRepository, PredefinedNarrationMapper predefinedNarrationMapper, PredefinedNarrationSearchRepository predefinedNarrationSearchRepository) {
        super(predefinedNarrationRepository, predefinedNarrationMapper, predefinedNarrationSearchRepository);
    }

    @Override
    public PredefinedNarrationDTO save(PredefinedNarrationDTO predefinedNarrationDTO) {
        predefinedNarrationDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        predefinedNarrationDTO.setModifiedOn(LocalDate.now());
        return super.save(predefinedNarrationDTO);
    }
}
