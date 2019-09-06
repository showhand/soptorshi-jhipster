package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.VoucherNumberControlDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity VoucherNumberControl and its DTO VoucherNumberControlDTO.
 */
@Mapper(componentModel = "spring", uses = {FinancialAccountYearMapper.class, VoucherMapper.class})
public interface VoucherNumberControlMapper extends EntityMapper<VoucherNumberControlDTO, VoucherNumberControl> {

    @Mapping(source = "financialAccountYear.id", target = "financialAccountYearId")
    @Mapping(source = "financialAccountYear.durationStr", target = "financialAccountYearDurationStr")
    @Mapping(source = "voucher.id", target = "voucherId")
    @Mapping(source = "voucher.name", target = "voucherName")
    VoucherNumberControlDTO toDto(VoucherNumberControl voucherNumberControl);

    @Mapping(source = "financialAccountYearId", target = "financialAccountYear")
    @Mapping(source = "voucherId", target = "voucher")
    VoucherNumberControl toEntity(VoucherNumberControlDTO voucherNumberControlDTO);

    default VoucherNumberControl fromId(Long id) {
        if (id == null) {
            return null;
        }
        VoucherNumberControl voucherNumberControl = new VoucherNumberControl();
        voucherNumberControl.setId(id);
        return voucherNumberControl;
    }
}
