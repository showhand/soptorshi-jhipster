package org.soptorshi.service.extended;

import org.soptorshi.repository.VoucherNumberControlRepository;
import org.soptorshi.repository.search.VoucherNumberControlSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.VoucherNumberControlService;
import org.soptorshi.service.dto.VoucherNumberControlDTO;
import org.soptorshi.service.mapper.VoucherNumberControlMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class VoucherNumberControlExtendedService extends VoucherNumberControlService {
    public VoucherNumberControlExtendedService(VoucherNumberControlRepository voucherNumberControlRepository, VoucherNumberControlMapper voucherNumberControlMapper, VoucherNumberControlSearchRepository voucherNumberControlSearchRepository) {
        super(voucherNumberControlRepository, voucherNumberControlMapper, voucherNumberControlSearchRepository);
    }

    @Override
    public VoucherNumberControlDTO save(VoucherNumberControlDTO voucherNumberControlDTO) {
        voucherNumberControlDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        voucherNumberControlDTO.setModifiedOn(LocalDate.now());
        return super.save(voucherNumberControlDTO);
    }
}
