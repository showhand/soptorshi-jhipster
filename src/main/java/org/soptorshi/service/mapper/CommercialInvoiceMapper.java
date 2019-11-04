package org.soptorshi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soptorshi.domain.CommercialInvoice;
import org.soptorshi.service.dto.CommercialInvoiceDTO;

/**
 * Mapper for the entity CommercialInvoice and its DTO CommercialInvoiceDTO.
 */
@Mapper(componentModel = "spring", uses = {CommercialPurchaseOrderMapper.class, CommercialProformaInvoiceMapper.class, CommercialPackagingMapper.class})
public interface CommercialInvoiceMapper extends EntityMapper<CommercialInvoiceDTO, CommercialInvoice> {

    @Mapping(source = "commercialPurchaseOrder.id", target = "commercialPurchaseOrderId")
    @Mapping(source = "commercialPurchaseOrder.purchaseOrderNo", target = "commercialPurchaseOrderPurchaseOrderNo")
    @Mapping(source = "commercialProformaInvoice.id", target = "commercialProformaInvoiceId")
    @Mapping(source = "commercialProformaInvoice.proformaNo", target = "commercialProformaInvoiceProformaNo")
    @Mapping(source = "commercialPackaging.id", target = "commercialPackagingId")
    @Mapping(source = "commercialPackaging.consignmentNo", target = "commercialPackagingConsignmentNo")
    CommercialInvoiceDTO toDto(CommercialInvoice commercialInvoice);

    @Mapping(source = "commercialPurchaseOrderId", target = "commercialPurchaseOrder")
    @Mapping(source = "commercialProformaInvoiceId", target = "commercialProformaInvoice")
    @Mapping(source = "commercialPackagingId", target = "commercialPackaging")
    CommercialInvoice toEntity(CommercialInvoiceDTO commercialInvoiceDTO);

    default CommercialInvoice fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommercialInvoice commercialInvoice = new CommercialInvoice();
        commercialInvoice.setId(id);
        return commercialInvoice;
    }
}
