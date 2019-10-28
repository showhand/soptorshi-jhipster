import { Moment } from 'moment';

export const enum VoucherType {
    SELLING = 'SELLING',
    BUYING = 'BUYING'
}

export interface IJournalVoucher {
    id?: number;
    voucherNo?: string;
    voucherDate?: Moment;
    postDate?: Moment;
    type?: VoucherType;
    conversionFactor?: number;
    modifiedBy?: string;
    modifiedOn?: Moment;
    currencyNotation?: string;
    currencyId?: number;
}

export class JournalVoucher implements IJournalVoucher {
    constructor(
        public id?: number,
        public voucherNo?: string,
        public voucherDate?: Moment,
        public postDate?: Moment,
        public type?: VoucherType,
        public conversionFactor?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public currencyNotation?: string,
        public currencyId?: number
    ) {}
}
