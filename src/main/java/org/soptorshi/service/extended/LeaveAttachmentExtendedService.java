package org.soptorshi.service.extended;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soptorshi.repository.LeaveAttachmentRepository;
import org.soptorshi.repository.search.LeaveAttachmentSearchRepository;
import org.soptorshi.service.LeaveAttachmentService;
import org.soptorshi.service.mapper.LeaveAttachmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing LeaveAttachment.
 */
@Service
@Transactional
public class LeaveAttachmentExtendedService extends LeaveAttachmentService {

    private final Logger log = LoggerFactory.getLogger(LeaveAttachmentExtendedService.class);

    private final LeaveAttachmentRepository leaveAttachmentRepository;

    private final LeaveAttachmentMapper leaveAttachmentMapper;

    private final LeaveAttachmentSearchRepository leaveAttachmentSearchRepository;

    public LeaveAttachmentExtendedService(LeaveAttachmentRepository leaveAttachmentRepository, LeaveAttachmentMapper leaveAttachmentMapper, LeaveAttachmentSearchRepository leaveAttachmentSearchRepository) {
        super(leaveAttachmentRepository, leaveAttachmentMapper, leaveAttachmentSearchRepository);
        this.leaveAttachmentRepository = leaveAttachmentRepository;
        this.leaveAttachmentMapper = leaveAttachmentMapper;
        this.leaveAttachmentSearchRepository = leaveAttachmentSearchRepository;
    }
}
