import { Moment } from 'moment';

export const enum VoucherType {
    SELLING = 'SELLING',
    BUYING = 'BUYING'
}

export const enum VoucherReferenceType {
    PAYROLL = 'PAYROLL',
    PURCHASE_ORDER = 'PURCHASE_ORDER',
    PROCUREMENT = 'PROCUREMENT'
}

export const enum ApplicationType {
    REQUISITION = 'REQUISITION',
    PURCHASE_ORDER = 'PURCHASE_ORDER',
    PAY_ROLL = 'PAY_ROLL'
}

export interface IJournalVoucher {
    id?: number;
    voucherNo?: string;
    voucherDate?: Moment;
    postDate?: Moment;
    type?: VoucherType;
    conversionFactor?: number;
    reference?: VoucherReferenceType;
    applicationType?: ApplicationType;
    applicationId?: number;
    referenceId?: number;
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
        public reference?: VoucherReferenceType,
        public applicationType?: ApplicationType,
        public applicationId?: number,
        public referenceId?: number,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public currencyNotation?: string,
        public currencyId?: number
    ) {}
}
