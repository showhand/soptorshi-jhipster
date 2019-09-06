import { Moment } from 'moment';

export const enum BillClosingFlag {
    OPEN = 'OPEN',
    CLOSED = 'CLOSED',
    WITHHELD = 'WITHHELD'
}

export interface IDebtorLedger {
    id?: number;
    serialNo?: string;
    billNo?: string;
    billDate?: Moment;
    amount?: number;
    paidAmount?: number;
    billClosingFlag?: BillClosingFlag;
    dueDate?: Moment;
    vatNo?: string;
    contCode?: string;
    orderNo?: string;
    modifiedBy?: string;
    modifiedOn?: Moment;
    customerName?: string;
    customerId?: number;
}

export class DebtorLedger implements IDebtorLedger {
    constructor(
        public id?: number,
        public serialNo?: string,
        public billNo?: string,
        public billDate?: Moment,
        public amount?: number,
        public paidAmount?: number,
        public billClosingFlag?: BillClosingFlag,
        public dueDate?: Moment,
        public vatNo?: string,
        public contCode?: string,
        public orderNo?: string,
        public modifiedBy?: string,
        public modifiedOn?: Moment,
        public customerName?: string,
        public customerId?: number
    ) {}
}
