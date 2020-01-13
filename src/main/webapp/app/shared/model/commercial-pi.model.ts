import { Moment } from 'moment';

export const enum PaymentType {
    LC = 'LC',
    TT = 'TT',
    CASH = 'CASH',
    CHEQUE = 'CHEQUE',
    OTHERS = 'OTHERS'
}

export const enum CommercialPiStatus {
    WAITING_FOR_PI_APPROVAL_BY_THE_CUSTOMER = 'WAITING_FOR_PI_APPROVAL_BY_THE_CUSTOMER',
    PI_APPROVED_BY_THE_CUSTOMER = 'PI_APPROVED_BY_THE_CUSTOMER',
    PI_REJECTED_BY_THE_CUSTOMER = 'PI_REJECTED_BY_THE_CUSTOMER'
}

export interface ICommercialPi {
    id?: number;
    companyName?: string;
    companyDescription?: string;
    proformaNo?: string;
    proformaDate?: Moment;
    harmonicCode?: string;
    paymentType?: PaymentType;
    paymentTerm?: string;
    termsOfDelivery?: string;
    shipmentDate?: string;
    portOfLoading?: string;
    portOfDestination?: string;
    purchaseOrderNo?: string;
    piStatus?: CommercialPiStatus;
    createdBy?: string;
    createdOn?: Moment;
    updatedBy?: string;
    updatedOn?: Moment;
    commercialBudgetBudgetNo?: string;
    commercialBudgetId?: number;
}

export class CommercialPi implements ICommercialPi {
    constructor(
        public id?: number,
        public companyName?: string,
        public companyDescription?: string,
        public proformaNo?: string,
        public proformaDate?: Moment,
        public harmonicCode?: string,
        public paymentType?: PaymentType,
        public paymentTerm?: string,
        public termsOfDelivery?: string,
        public shipmentDate?: string,
        public portOfLoading?: string,
        public portOfDestination?: string,
        public purchaseOrderNo?: string,
        public piStatus?: CommercialPiStatus,
        public createdBy?: string,
        public createdOn?: Moment,
        public updatedBy?: string,
        public updatedOn?: Moment,
        public commercialBudgetBudgetNo?: string,
        public commercialBudgetId?: number
    ) {}
}
