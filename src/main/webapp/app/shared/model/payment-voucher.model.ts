import { Moment } from 'moment';

export interface IPaymentVoucher {
    id?: number;
    voucherNo?: string;
    voucherDate?: Moment;
    postDate?: Moment;
    modifiedBy?: string;
    modifiedOn?: Moment;
    accountName?: string;
    accountId?: number;
}

export class PaymentVoucher implements IPaymentVoucher {
    constructor(
        public id?: number,
        public voucherNo?: string,
        public voucherDate?: Moment,
        public postDate?: Moment,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public accountName?: string,
        public accountId?: number
    ) {}
}
