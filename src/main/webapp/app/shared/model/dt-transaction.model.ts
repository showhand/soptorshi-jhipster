import { Moment } from 'moment';

export const enum BalanceType {
    DEBIT = 'DEBIT',
    CREDIT = 'CREDIT'
}

export const enum VoucherType {
    SELLING = 'SELLING',
    BUYING = 'BUYING'
}

export const enum InstrumentType {
    CHEQUE = 'CHEQUE',
    PAY_ORDER = 'PAY_ORDER',
    DEMAND_DRAFT = 'DEMAND_DRAFT'
}

export interface IDtTransaction {
    id?: number;
    voucherNo?: string;
    voucherDate?: Moment;
    serialNo?: number;
    amount?: number;
    balanceType?: BalanceType;
    type?: VoucherType;
    invoiceNo?: string;
    invoiceDate?: Moment;
    instrumentType?: InstrumentType;
    instrumentNo?: string;
    instrumentDate?: Moment;
    fCurrency?: number;
    convFactor?: number;
    postDate?: Moment;
    narration?: string;
    modifiedBy?: string;
    modifiedOn?: Moment;
    reference?: string;
    accountName?: string;
    accountId?: number;
    voucherName?: string;
    voucherId?: number;
    currencyNotation?: string;
    currencyId?: number;
}

export class DtTransaction implements IDtTransaction {
    constructor(
        public id?: number,
        public voucherNo?: string,
        public voucherDate?: Moment,
        public serialNo?: number,
        public amount?: number,
        public balanceType?: BalanceType,
        public type?: VoucherType,
        public invoiceNo?: string,
        public invoiceDate?: Moment,
        public instrumentType?: InstrumentType,
        public instrumentNo?: string,
        public instrumentDate?: Moment,
        public fCurrency?: number,
        public convFactor?: number,
        public postDate?: Moment,
        public narration?: string,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public reference?: string,
        public accountName?: string,
        public accountId?: number,
        public voucherName?: string,
        public voucherId?: number,
        public currencyNotation?: string,
        public currencyId?: number
    ) {}
}
