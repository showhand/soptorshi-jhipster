import { Moment } from 'moment';

export const enum ApplicationType {
    REQUISITION = 'REQUISITION',
    PURCHASE_ORDER = 'PURCHASE_ORDER',
    PAY_ROLL = 'PAY_ROLL'
}

export interface IContraVoucher {
    id?: number;
    voucherNo?: string;
    voucherDate?: Moment;
    postDate?: Moment;
    applicationType?: ApplicationType;
    applicationId?: number;
    conversionFactor?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    currencyNotation?: string;
    currencyId?: number;
}

export class ContraVoucher implements IContraVoucher {
    constructor(
        public id?: number,
        public voucherNo?: string,
        public voucherDate?: Moment,
        public postDate?: Moment,
        public applicationType?: ApplicationType,
        public applicationId?: number,
        public conversionFactor?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public currencyNotation?: string,
        public currencyId?: number
    ) {}
}
