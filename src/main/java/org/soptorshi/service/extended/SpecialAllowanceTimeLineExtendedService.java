package org.soptorshi.service.extended;

import org.soptorshi.domain.SpecialAllowanceTimeLine;
import org.soptorshi.domain.enumeration.MonthType;
import org.soptorshi.repository.SpecialAllowanceTimeLineRepository;
import org.soptorshi.repository.extended.SpecialAllowanceTimeLineExtendedRepository;
import org.soptorshi.repository.search.SpecialAllowanceTimeLineSearchRepository;
import org.soptorshi.service.SpecialAllowanceTimeLineService;
import org.soptorshi.service.mapper.SpecialAllowanceTimeLineMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SpecialAllowanceTimeLineExtendedService extends SpecialAllowanceTimeLineService {
    private SpecialAllowanceTimeLineExtendedRepository specialAllowanceTimeLineExtendedRepository;

    public SpecialAllowanceTimeLineExtendedService(SpecialAllowanceTimeLineRepository specialAllowanceTimeLineRepository, SpecialAllowanceTimeLineMapper specialAllowanceTimeLineMapper, SpecialAllowanceTimeLineSearchRepository specialAllowanceTimeLineSearchRepository, SpecialAllowanceTimeLineExtendedRepository specialAllowanceTimeLineExtendedRepository) {
        super(specialAllowanceTimeLineRepository, specialAllowanceTimeLineMapper, specialAllowanceTimeLineSearchRepository);
        this.specialAllowanceTimeLineExtendedRepository = specialAllowanceTimeLineExtendedRepository;
    }


    @Transactional(readOnly = true)
    public List<SpecialAllowanceTimeLine> get(Integer year, MonthType monthType){
        return specialAllowanceTimeLineExtendedRepository.getByYearAndMonth(year, monthType);
    }
}
