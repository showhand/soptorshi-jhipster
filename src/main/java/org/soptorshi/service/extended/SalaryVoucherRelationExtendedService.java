package org.soptorshi.service.extended;

import org.soptorshi.repository.SalaryVoucherRelationRepository;
import org.soptorshi.repository.search.SalaryVoucherRelationSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.SalaryVoucherRelationService;
import org.soptorshi.service.dto.SalaryVoucherRelationDTO;
import org.soptorshi.service.mapper.SalaryVoucherRelationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class SalaryVoucherRelationExtendedService extends SalaryVoucherRelationService {
    public SalaryVoucherRelationExtendedService(SalaryVoucherRelationRepository salaryVoucherRelationRepository, SalaryVoucherRelationMapper salaryVoucherRelationMapper, SalaryVoucherRelationSearchRepository salaryVoucherRelationSearchRepository) {
        super(salaryVoucherRelationRepository, salaryVoucherRelationMapper, salaryVoucherRelationSearchRepository);
    }

    @Override
    public SalaryVoucherRelationDTO save(SalaryVoucherRelationDTO salaryVoucherRelationDTO) {
        salaryVoucherRelationDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get());
        salaryVoucherRelationDTO.setModifiedOn(LocalDate.now());
        return super.save(salaryVoucherRelationDTO);
    }
}
