import { Moment } from 'moment';

export const enum ApplicationType {
    REQUISITION = 'REQUISITION',
    PURCHASE_ORDER = 'PURCHASE_ORDER',
    PAY_ROLL = 'PAY_ROLL'
}

export interface IReceiptVoucher {
    id?: number;
    voucherNo?: string;
    voucherDate?: Moment;
    postDate?: Moment;
    applicationType?: ApplicationType;
    applicationId?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    accountName?: string;
    accountId?: number;
}

export class ReceiptVoucher implements IReceiptVoucher {
    constructor(
        public id?: number,
        public voucherNo?: string,
        public voucherDate?: Moment,
        public postDate?: Moment,
        public applicationType?: ApplicationType,
        public applicationId?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public accountName?: string,
        public accountId?: number
    ) {}
}
