import { Moment } from 'moment';

export const enum BalanceType {
    DEBIT = 'DEBIT',
    CREDIT = 'CREDIT'
}

export interface IAccountBalance {
    id?: number;
    yearOpenBalance?: number;
    yearOpenBalanceType?: BalanceType;
    totDebitTrans?: number;
    totCreditTrans?: number;
    modifiedOn?: Moment;
    modifiedBy?: string;
    financialAccountYearDurationStr?: string;
    financialAccountYearId?: number;
    accountName?: string;
    accountId?: number;
}

export class AccountBalance implements IAccountBalance {
    constructor(
        public id?: number,
        public yearOpenBalance?: number,
        public yearOpenBalanceType?: BalanceType,
        public totDebitTrans?: number,
        public totCreditTrans?: number,
        public modifiedOn?: Moment,
        public modifiedBy?: string,
        public financialAccountYearDurationStr?: string,
        public financialAccountYearId?: number,
        public accountName?: string,
        public accountId?: number
    ) {}
}
