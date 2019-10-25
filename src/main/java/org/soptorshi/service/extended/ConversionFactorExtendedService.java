package org.soptorshi.service.extended;

import org.soptorshi.repository.ConversionFactorRepository;
import org.soptorshi.repository.search.ConversionFactorSearchRepository;
import org.soptorshi.security.SecurityUtils;
import org.soptorshi.service.ConversionFactorService;
import org.soptorshi.service.dto.ConversionFactorDTO;
import org.soptorshi.service.mapper.ConversionFactorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class ConversionFactorExtendedService extends ConversionFactorService {
    public ConversionFactorExtendedService(ConversionFactorRepository conversionFactorRepository, ConversionFactorMapper conversionFactorMapper, ConversionFactorSearchRepository conversionFactorSearchRepository) {
        super(conversionFactorRepository, conversionFactorMapper, conversionFactorSearchRepository);
    }

    @Override
    public ConversionFactorDTO save(ConversionFactorDTO conversionFactorDTO) {
        conversionFactorDTO.setModifiedBy(SecurityUtils.getCurrentUserLogin().get().toString());
        conversionFactorDTO.setModifiedOn(LocalDate.now());
        return super.save(conversionFactorDTO);
    }
}
