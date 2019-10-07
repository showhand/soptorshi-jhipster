package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.domain.PeriodClose;
import org.soptorshi.repository.PeriodCloseRepository;
import org.soptorshi.repository.search.PeriodCloseSearchRepository;
import org.soptorshi.service.PeriodCloseQueryService;
import org.soptorshi.service.dto.PeriodCloseCriteria;
import org.soptorshi.service.dto.PeriodCloseDTO;
import org.soptorshi.service.mapper.PeriodCloseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PeriodCloseExtendedQueryService extends PeriodCloseQueryService {

    private final Logger log = LoggerFactory.getLogger(PeriodCloseExtendedQueryService.class);


    public PeriodCloseExtendedQueryService(PeriodCloseRepository periodCloseRepository, PeriodCloseMapper periodCloseMapper, PeriodCloseSearchRepository periodCloseSearchRepository) {
        super(periodCloseRepository, periodCloseMapper, periodCloseSearchRepository);
    }




}
