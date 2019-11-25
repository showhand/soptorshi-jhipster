package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.SalaryMessagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SalaryMessages and its DTO SalaryMessagesDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, MonthlySalaryMapper.class})
public interface SalaryMessagesMapper extends EntityMapper<SalaryMessagesDTO, SalaryMessages> {

    @Mapping(source = "commenter.id", target = "commenterId")
    @Mapping(source = "commenter.fullName", target = "commenterFullName")
    @Mapping(source = "monthlySalary.id", target = "monthlySalaryId")
    SalaryMessagesDTO toDto(SalaryMessages salaryMessages);

    @Mapping(source = "commenterId", target = "commenter")
    @Mapping(source = "monthlySalaryId", target = "monthlySalary")
    SalaryMessages toEntity(SalaryMessagesDTO salaryMessagesDTO);

    default SalaryMessages fromId(Long id) {
        if (id == null) {
            return null;
        }
        SalaryMessages salaryMessages = new SalaryMessages();
        salaryMessages.setId(id);
        return salaryMessages;
    }
}
