import { Moment } from 'moment';

export const enum BalanceType {
    DEBIT = 'DEBIT',
    CREDIT = 'CREDIT'
}

export const enum BillClosingFlag {
    OPEN = 'OPEN',
    CLOSED = 'CLOSED',
    WITHHELD = 'WITHHELD'
}

export interface ICreditorLedger {
    id?: number;
    serialNo?: number;
    billNo?: string;
    billDate?: Moment;
    amount?: number;
    paidAmount?: number;
    balanceType?: BalanceType;
    billClosingFlag?: BillClosingFlag;
    dueDate?: Moment;
    vatNo?: string;
    contCode?: string;
    orderNo?: string;
    modifiedBy?: string;
    modifiedOn?: Moment;
    vendorCompanyName?: string;
    vendorId?: number;
}

export class CreditorLedger implements ICreditorLedger {
    constructor(
        public id?: number,
        public serialNo?: number,
        public billNo?: string,
        public billDate?: Moment,
        public amount?: number,
        public paidAmount?: number,
        public balanceType?: BalanceType,
        public billClosingFlag?: BillClosingFlag,
        public dueDate?: Moment,
        public vatNo?: string,
        public contCode?: string,
        public orderNo?: string,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public vendorCompanyName?: string,
        public vendorId?: number
    ) {}
}
