package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.repository.CommercialAttachmentRepository;
import org.soptorshi.repository.search.CommercialAttachmentSearchRepository;
import org.soptorshi.service.CommercialAttachmentService;
import org.soptorshi.service.mapper.CommercialAttachmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing CommercialAttachment.
 */
@Service
@Transactional
public class CommercialAttachmentExtendedService extends CommercialAttachmentService {

    private final Logger log = LoggerFactory.getLogger(CommercialAttachmentExtendedService.class);

    public CommercialAttachmentExtendedService(CommercialAttachmentRepository commercialAttachmentRepository, CommercialAttachmentMapper commercialAttachmentMapper, CommercialAttachmentSearchRepository commercialAttachmentSearchRepository) {
        super(commercialAttachmentRepository, commercialAttachmentMapper, commercialAttachmentSearchRepository);
    }
}
