package org.soptorshi.service.extended;

import org.soptorshi.repository.PeriodCloseRepository;
import org.soptorshi.repository.search.PeriodCloseSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.PeriodCloseService;
import org.soptorshi.service.dto.PeriodCloseDTO;
import org.soptorshi.service.mapper.PeriodCloseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class PeriodCloseExtendedService extends PeriodCloseService {
    public PeriodCloseExtendedService(PeriodCloseRepository periodCloseRepository, PeriodCloseMapper periodCloseMapper, PeriodCloseSearchRepository periodCloseSearchRepository) {
        super(periodCloseRepository, periodCloseMapper, periodCloseSearchRepository);
    }

    @Override
    public PeriodCloseDTO save(PeriodCloseDTO periodCloseDTO) {
        periodCloseDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        periodCloseDTO.setModifiedOn(LocalDate.now());
        return super.save(periodCloseDTO);
    }

}
