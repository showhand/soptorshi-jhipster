import { Moment } from 'moment';

export interface ICommercialProformaInvoice {
    id?: number;
    companyName?: string;
    companyDescriptionOrAddress?: string;
    proformaNo?: string;
    proformaDate?: Moment;
    harmonicCode?: string;
    paymentTerm?: string;
    termsOfDelivery?: string;
    shipmentDate?: string;
    portOfLanding?: string;
    portOfDestination?: string;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    commercialPurchaseOrderPurchaseOrderNo?: string;
    commercialPurchaseOrderId?: number;
}

export class CommercialProformaInvoice implements ICommercialProformaInvoice {
    constructor(
        public id?: number,
        public companyName?: string,
        public companyDescriptionOrAddress?: string,
        public proformaNo?: string,
        public proformaDate?: Moment,
        public harmonicCode?: string,
        public paymentTerm?: string,
        public termsOfDelivery?: string,
        public shipmentDate?: string,
        public portOfLanding?: string,
        public portOfDestination?: string,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public commercialPurchaseOrderPurchaseOrderNo?: string,
        public commercialPurchaseOrderId?: number
    ) {}
}
