import { Moment } from 'moment';

export interface IReceiptVoucher {
    id?: number;
    voucherNo?: string;
    voucherDate?: Moment;
    postDate?: Moment;
    modifiedBy?: string;
    modifiedOn?: Moment;
}

export class ReceiptVoucher implements IReceiptVoucher {
    constructor(
        public id?: number,
        public voucherNo?: string,
        public voucherDate?: Moment,
        public postDate?: Moment,
        public modifiedBy?: string,
        public modifiedOn?: Moment
    ) {}
}
