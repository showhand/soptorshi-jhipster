package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.LoanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Loan and its DTO LoanDTO.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface LoanMapper extends EntityMapper<LoanDTO, Loan> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullName", target = "employeeFullName")
    LoanDTO toDto(Loan loan);

    @Mapping(source = "employeeId", target = "employee")
    Loan toEntity(LoanDTO loanDTO);

    default Loan fromId(Long id) {
        if (id == null) {
            return null;
        }
        Loan loan = new Loan();
        loan.setId(id);
        return loan;
    }
}
