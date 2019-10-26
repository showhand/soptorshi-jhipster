import { Moment } from 'moment';

export interface IPaymentVoucher {
    id?: number;
    voucherNo?: string;
    voucherDate?: Moment;
    postDate?: Moment;
    modifiedBy?: string;
    modifiedOn?: Moment;
}

export class PaymentVoucher implements IPaymentVoucher {
    constructor(
        public id?: number,
        public voucherNo?: string,
        public voucherDate?: Moment,
        public postDate?: Moment,
        public modifiedBy?: string,
        public modifiedOn?: Moment
    ) {}
}
