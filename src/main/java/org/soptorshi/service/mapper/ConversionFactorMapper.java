package org.soptorshi.service.mapper;

import org.soptorshi.domain.*;
import org.soptorshi.service.dto.ConversionFactorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ConversionFactor and its DTO ConversionFactorDTO.
 */
@Mapper(componentModel = "spring", uses = {CurrencyMapper.class})
public interface ConversionFactorMapper extends EntityMapper<ConversionFactorDTO, ConversionFactor> {

    @Mapping(source = "currency.id", target = "currencyId")
    @Mapping(source = "currency.notation", target = "currencyNotation")
    ConversionFactorDTO toDto(ConversionFactor conversionFactor);

    @Mapping(source = "currencyId", target = "currency")
    ConversionFactor toEntity(ConversionFactorDTO conversionFactorDTO);

    default ConversionFactor fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConversionFactor conversionFactor = new ConversionFactor();
        conversionFactor.setId(id);
        return conversionFactor;
    }
}
