import { Moment } from 'moment';

export interface ICommercialInvoice {
    id?: number;
    invoiceNo?: string;
    invoiceDate?: string;
    termsOfPayment?: string;
    consignedTo?: string;
    portOfLoading?: string;
    portOfDischarge?: string;
    exportRegistrationCertificateNo?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    commercialPurchaseOrderPurchaseOrderNo?: string;
    commercialPurchaseOrderId?: number;
    commercialProformaInvoiceProformaNo?: string;
    commercialProformaInvoiceId?: number;
    commercialPackagingConsignmentNo?: string;
    commercialPackagingId?: number;
}

export class CommercialInvoice implements ICommercialInvoice {
    constructor(
        public id?: number,
        public invoiceNo?: string,
        public invoiceDate?: string,
        public termsOfPayment?: string,
        public consignedTo?: string,
        public portOfLoading?: string,
        public portOfDischarge?: string,
        public exportRegistrationCertificateNo?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public commercialPurchaseOrderPurchaseOrderNo?: string,
        public commercialPurchaseOrderId?: number,
        public commercialProformaInvoiceProformaNo?: string,
        public commercialProformaInvoiceId?: number,
        public commercialPackagingConsignmentNo?: string,
        public commercialPackagingId?: number
    ) {}
}
