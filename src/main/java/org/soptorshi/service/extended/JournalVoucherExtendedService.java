package org.soptorshi.service.extended;

import org.soptorshi.domain.JournalVoucherGenerator;
import org.soptorshi.repository.JournalVoucherGeneratorRepository;
import org.soptorshi.repository.JournalVoucherRepository;
import org.soptorshi.repository.search.JournalVoucherSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.JournalVoucherService;
import org.soptorshi.service.dto.JournalVoucherDTO;
import org.soptorshi.service.mapper.JournalVoucherMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class JournalVoucherExtendedService extends JournalVoucherService {
    private JournalVoucherGeneratorRepository journalVoucherGeneratorRepository;

    public JournalVoucherExtendedService(JournalVoucherRepository journalVoucherRepository,
                                         JournalVoucherMapper journalVoucherMapper,
                                         JournalVoucherSearchRepository journalVoucherSearchRepository,
                                         JournalVoucherGeneratorRepository journalVoucherGeneratorRepository) {
        super(journalVoucherRepository, journalVoucherMapper, journalVoucherSearchRepository);
        this.journalVoucherGeneratorRepository = journalVoucherGeneratorRepository;
    }

    @Override
    public JournalVoucherDTO save(JournalVoucherDTO journalVoucherDTO) {
        JournalVoucherGenerator journalVoucherGenerator = new JournalVoucherGenerator();
        if(journalVoucherDTO.getId()==null){
            journalVoucherGeneratorRepository.save(journalVoucherGenerator);
            String voucherNo = String.format("%06d", journalVoucherGenerator.getId());
            journalVoucherDTO.setVoucherNo("JN"+voucherNo);
        }

        journalVoucherDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        journalVoucherDTO.setModifiedOn(LocalDate.now());
        return super.save(journalVoucherDTO);
    }
}
